package com.ZestAssignment.EmployeeManagment.Services;

import com.ZestAssignment.EmployeeManagment.DTO.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtHelper {

    // instance field — Spring can inject this
    @Value("${spring.jwt_secret}")
    private String secretKeyValue;

    private static String SECRET_KEY = "";

    @PostConstruct
    private void init(){
        SECRET_KEY = secretKeyValue;
    }


    private final static long EXPIRATION_TIME = 86400000;

    public static String generateJWTToken(UserDTO currUserDTO){
        return Jwts.builder()
                .setSubject(currUserDTO.getEmail())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .claim("role", "ROLE_" + currUserDTO.getRole())
                .compact();
    }

    public static Claims extractClaims(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .setAllowedClockSkewSeconds(300)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token){
        return extractClaims(token).getSubject();
    }

    private boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private boolean validateToken(String token){
        return !isTokenExpired(token);
    }

    public static String extractRoleFromToken(String token){
        return extractClaims(token).get("role", String.class);
    }

}
