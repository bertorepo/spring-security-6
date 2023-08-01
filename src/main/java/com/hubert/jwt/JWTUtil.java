package com.hubert.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.time.Instant;
import java.util.Map;

import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class JWTUtil {

    private  static  final String SECRET_KEY =
            "hubert_3231_berja_hubert_3231_berja_hubert_3231_berja_hubert_3231_berja";

    public  String issueToken(String subject) {
        return  issueToken(subject, Map.of());
    }

    public  String issueToken(String subject, String ...scopes) {
        return  issueToken(subject, Map.of("scopes", scopes));
    }


    public String issueToken(String subject, Map<String, Object> claims) {
        String token = Jwts
                .builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuer("hubert")
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(15, DAYS)))
                .signWith(getSigninKey(), HS256)
                .compact();

        return token;

    }

    public String getSubject(String token) {
        return getClaims(token).getSubject();
    }

    public Claims getClaims(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims;
    }

    public boolean isTokenValid(String token, String username) {
      return getSubject(token).equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(Date.from(Instant.now()));
    }

    private Key getSigninKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
