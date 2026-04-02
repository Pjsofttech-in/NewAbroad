//package com.abroad.JWT;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import javax.crypto.SecretKey;
//import java.nio.charset.StandardCharsets;
//import java.util.Date;
//
//@Component
//public class JwtUtil {
//    @Value("${jwt.secret}")
//    private String secretKey;
//
//    @Value("${jwt.expiration}")
//    private long expirationTime;
//
//    private SecretKey getSigningKey() {
//        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
//    }
//
//    public String generateToken(String email) {
//        return Jwts.builder()
//                .subject(email)
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + expirationTime))
//                .signWith(getSigningKey()) // No need for SignatureAlgorithm.HS256
//                .compact();
//    }
//
//    public boolean validateToken(String token) {
//        try {
//            Jwts.parser()
//                    .verifyWith(getSigningKey())
//                    .build()
//                    .parseSignedClaims(token);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public String extractEmail(String token) {
//        Claims claims = Jwts.parser()
//                .verifyWith(getSigningKey()) // Correct method
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//        return claims.getSubject();
//    }
//}


package com.abroad.JWT;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${jwt.expiration}")
    private long expirationTime;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    // ===============================
    // ✅ OLD LOGIN TOKEN (DO NOT CHANGE)
    // ===============================
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(getSigningKey())
                .compact();
    }

    // ===============================
    // ✅ NEW QR TOKEN METHOD
    // ===============================
    public String generateTokenWithClaims(String email,
                                          Map<String, Object> claims,
                                          Duration validity) {

        return Jwts.builder()
                .subject(email)
                .claims(claims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + validity.toMillis()))
                .signWith(getSigningKey())
                .compact();
    }

    // ===============================
    // ✅ VALIDATE TOKEN (UNCHANGED)
    // ===============================
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // ===============================
    // ✅ EXTRACT EMAIL (UNCHANGED)
    // ===============================
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    // ===============================
    // ✅ NEW: EXTRACT CUSTOM CLAIM
    // ===============================
    public String extractClaim(String token, String claimKey) {
        return extractAllClaims(token).get(claimKey, String.class);
    }

    // ===============================
    // INTERNAL METHOD
    // ===============================
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}