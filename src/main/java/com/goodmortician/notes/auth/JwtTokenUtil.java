package com.goodmortician.notes.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goodmortician.notes.web.LocalJwtKeyProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.jackson.io.JacksonDeserializer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    private final LocalJwtKeyProvider localJwtKeyProvider;
    private final ObjectMapper objectMapper;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
    public String getUsernameFromToken (String token) {
        return getClaimFromToken (token, Claims::getSubject);
    }
    public Date getExpirationDateFromToken (String token) {
        return getClaimFromToken (token, Claims::getExpiration);
    }
    public <T> T getClaimFromToken (String token, Function<Claims, T> claimsResolver){
        final Jws<Claims> claims = getAllClaimFromToken(token);
        return claimsResolver.apply(claims.getBody());
    }
    private Jws<Claims> getAllClaimFromToken(String token){
        return Jwts.parserBuilder()
                .deserializeJsonWith(new JacksonDeserializer<>(objectMapper))
                .setSigningKey(localJwtKeyProvider.getVerifyingKey())
                .build()
                .parseClaimsJws(token);
    }
    private Boolean isTokenExpired (String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    public String generateToken (UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        claims.put("login", userDetails.getUsername());
        return generateToken(claims, userDetails.getUsername());
    }
    public String generateToken (Map<String, Object> claims, String subject){
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
                .signWith(localJwtKeyProvider.getSigningKey(), SignatureAlgorithm.RS256)
                .compact();
    }
    public Boolean validateToken(String token, UserDetails userDetails){
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }



}
