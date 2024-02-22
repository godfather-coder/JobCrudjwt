package com.example.jwtauth.service.Impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

  @Value("${token.secret.key}")
  String jwtSecretKey;

  @Value("${token.expirationms}")
  Long jwtExpirationMs;

  public String extractUserName(String token) {
      return extractClaim(token, Claims::getSubject);
  }

  public String generateToken(UserDetails userDetails) {
      return generateToken(new HashMap<>(), userDetails);
  }

  public boolean isTokenValid(String token, UserDetails userDetails) {
      final String userName = extractUserName(token);
      return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
      final Claims claims = extractAllClaims(token);
      return claimsResolvers.apply(claims);
  }

  private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
      return Jwts
        .builder()
              .setClaims(extraClaims)
              .subject(userDetails.getUsername())
              .issuedAt(new Date(System.currentTimeMillis()))
              .expiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
              .signWith(getKey(),SignatureAlgorithm.HS256)
              .compact();
//        .setClaims(extraClaims)
//        .setSubject(userDetails.getUsername())
//        .setIssuedAt(new Date(System.currentTimeMillis()))
//        .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationMs))
//        .signWith(getKey(), SignatureAlgorithm.HS256)
//        .compact();
  }

  private boolean isTokenExpired(String token) {
      return extractExpiration(token).before(new Date());
  }

  private Date extractExpiration(String token) {
      return extractClaim(token, Claims::getExpiration);
  }

  private Claims extractAllClaims(String token) {
      return Jwts
              .parser()
              .verifyWith(getKey())
              .build()
              .parseSignedClaims(token)
              .getPayload();
  }

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecretKey));
    }
  
}
