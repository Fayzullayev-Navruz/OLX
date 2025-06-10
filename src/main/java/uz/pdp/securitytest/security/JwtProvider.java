package uz.pdp.securitytest.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {


    @Value("${jwt.secretKey}")
    private String secretKey;


    public String generateToken(String username, Date expiration) {

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        return Jwts.builder()
                .signWith(key)
                .setSubject(username)
                .claim("role", "Admin")
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .compact();

    }

    public String validateToken(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes());

        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
        String subject = claims.getBody().getSubject();

        return subject;

    }

}



