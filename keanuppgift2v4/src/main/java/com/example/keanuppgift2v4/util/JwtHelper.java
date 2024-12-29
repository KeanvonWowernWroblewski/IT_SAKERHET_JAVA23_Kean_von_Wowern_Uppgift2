package com.example.keanuppgift2v4.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

// generates and parses jwt tokens
@Component
public class JwtHelper {

    private final SecretKey secretKey =
            Keys.hmacShaKeyFor("my_jwt_secret_key_my_jwt_secret_key".getBytes());

    public String generateToken(String userEmail) {
        return Jwts.builder()
                .setSubject(userEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600_000)) // 1 hour
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }



    public String extractEmail(String headerVal) {
        if (headerVal.startsWith("bearer ")) {
            headerVal = headerVal.substring(7);
        }

        Claims c = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(headerVal)
                .getBody();

        return c.getSubject();
    }
}
