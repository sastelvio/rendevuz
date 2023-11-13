package com.sastelvio.rendezvous.config.security;

import com.sastelvio.rendezvous.domain.entity.security.User;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {
    private String secret = "secret";

    public String generateToken(User user){
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth")
                    .withSubject(user.getUsername())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            throw new RuntimeException("ERROR WHILE GENERATION TOKEN", exception);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // Remove the "Bearer " prefix from the token if it exists
            token = token.replace("Bearer ", "");
            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            return exception.toString();
        }
    }

    private Instant getExpirationDate(){
        //TODO Look for best practices to this code, to confirm that this is the best way to implement it
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("+01:00"));
    }
}
