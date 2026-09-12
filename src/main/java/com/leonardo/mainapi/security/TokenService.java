package com.leonardo.mainapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@Service
public class TokenService {

    Algorithm algorithm;

    public TokenService(@Value("${jwt.secret}") String secret){
        algorithm = Algorithm.HMAC256(secret);
    }

    // Retorna o login
    public String validate(String token){
            return JWT
                    .require(algorithm)
                    .withIssuer("sso")
                    .build()
                    .verify(token)
                    .getSubject();
    }
}
