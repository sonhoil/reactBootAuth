package com.cooking.side.todo.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtils {

    private final SecretKey jwtSecret;

    @Value("${app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public JwtUtils(@Value("${app.jwtSecret}") String secret) {
        byte[] decodedKey = Base64.getDecoder().decode(secret);
        this.jwtSecret = Keys.hmacShaKeyFor(decodedKey);
    }

    public String generateJwtToken(Authentication authentication) {
        Object principal = authentication.getPrincipal();
        String username;

        if (principal instanceof DefaultOAuth2User) {
            username = (String) ((DefaultOAuth2User) principal).getAttributes().get("name");
        } else if (principal instanceof UserDetailsImpl) {
            username = ((UserDetailsImpl) principal).getUsername();
        } else {
            throw new IllegalArgumentException("Unsupported authentication principal type");
        }

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }
    public String generateJwtToken(DefaultOAuth2User oAuth2User) {
        return Jwts.builder()
                .setSubject((String) oAuth2User.getAttributes().get("name"))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.err.println("Invalid JWT signature: " + e.getMessage());
        } catch (ExpiredJwtException e) {
            System.err.println("JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            System.err.println("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("JWT claims string is empty: " + e.getMessage());
        }
        return false;
    }
}