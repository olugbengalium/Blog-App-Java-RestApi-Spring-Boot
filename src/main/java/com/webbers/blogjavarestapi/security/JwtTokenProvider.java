package com.webbers.blogjavarestapi.security;

import com.webbers.blogjavarestapi.exceptions.BlogAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${app.jwt-secret}")
    private String jwtSecret;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    //generate JWT token(Utility)
    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date currentDate = new Date();
        Date expirationDate = new Date(currentDate.getTime()+jwtExpirationDate);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expirationDate)
                .signWith(keys())
                .compact();
        return  token;
    }

    private Key keys(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecret)
        );
    }

    public String getUsername(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(keys())
                .build()
                .parseClaimsJws(token)
                .getBody();
        String username = claims.getSubject();
        return username;
    }

    //validate JWT token

    public Boolean validateToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(keys())
                    .build()
                    .parse(token);
            return true;
        }
        catch (MalformedJwtException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST,"Invalid Jwt token");
        }
        catch (ExpiredJwtException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT toke used");
        }
        catch (IllegalArgumentException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }
        catch (UnsupportedJwtException ex){
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        }

    }
}
