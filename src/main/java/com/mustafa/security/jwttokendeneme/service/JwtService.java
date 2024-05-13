package com.mustafa.security.jwttokendeneme.service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

    private final KeyPair keyPair;

    public JwtService() throws NoSuchAlgorithmException {
        this.keyPair = generateRsaKeyPair();
    }


    private KeyPair generateRsaKeyPair() throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        return keyPairGenerator.generateKeyPair();
    }


    private Key getRsaSigningKey() {
        return keyPair.getPrivate();
    }


    public String createJwt(String subject) {
        Key key = getRsaSigningKey();
        return Jwts.builder()
                .setSubject(subject)
                .signWith(key, SignatureAlgorithm.RS256)
                .compact();
    }


    public String extractUsername(String jwt) {
        Key key = getRsaSigningKey();
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwt)
                .getBody()
                .getSubject();
    }


    public boolean validateToken(String jwt) {
        try {
            Key key = getRsaSigningKey();
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}

