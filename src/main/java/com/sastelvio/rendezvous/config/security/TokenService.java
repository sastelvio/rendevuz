package com.sastelvio.rendezvous.config.security;

import com.sastelvio.rendezvous.domain.entity.security.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Slf4j
@Service
public class TokenService {
    private String secret = "secret"; //must be a secret word

    public String generateToken(User user){
        log.info("generating authentication token...");
        try{
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("auth")
                    .withSubject(user.getUsername())
                    .withClaim("firstname", user.getFirstName())
                    .withClaim("lastname", user.getLastName())
                    .withClaim("email", user.getEmail())
                    .withClaim("role", user.getRole().getRole())
                    .withExpiresAt(getExpirationDate())
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            log.error("error while generating token!");
            throw new RuntimeException("ERROR WHILE GENERATION TOKEN", exception);
        }
    }

    public String validateToken(String token) {
        log.info("validating token...");
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            // Remove the "Bearer " prefix from the token if it exists
            //token = token.replace("Bearer ", "");
            return JWT.require(algorithm)
                    .withIssuer("auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception) {
            log.error("error while validating token!");
            return exception.toString();
        }
    }

    private Instant getExpirationDate(){
        //TODO Look for best practices to this code, to confirm that this is the best way to implement it
        return LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("+01:00"));
    }
}
