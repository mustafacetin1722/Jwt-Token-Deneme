package com.mustafa.security.jwttokendeneme.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {
   /* private static final String SECRET_KEY =generateEncryptionKey();
    public static String generateEncryptionKey() {
        byte[] key = new byte[32];
        new SecureRandom().nextBytes(key);
        return BaseEncoding.base64().encode(key);
    }*/

    @Value("${jwt.key}")
    private String SECRET;

    public String generateToken(String userName){
        Map<String,Object> claims = new HashMap<>();
        claims.put("can","wia");
        return createToken(claims,userName);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        String username = extractUser(token);
        Date exprationDate = extractExpiration(token);
        return userDetails.getUsername().equals(username) && !exprationDate.before(new Date());

    }
    private Date extractExpiration(String token){
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSinkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }
    public String extractUser(String token){
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(getSinkey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }
    public String createToken(Map<String,Object> claims, String userName){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 2))
                .signWith(getSinkey(), SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getSinkey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
