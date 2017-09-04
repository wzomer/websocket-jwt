package com.github.wzomer.websocketjwt.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.wildfly.swarm.spi.runtime.annotations.ConfigurationValue;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.Date;
import java.util.Objects;

@ApplicationScoped
public class TokenService {

    public static final String HEADER_NAME = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer";

    @Inject
    @ConfigurationValue("jwt.expirationTime")
    private Long expirationTime;

    @Inject
    @ConfigurationValue("jwt.secret")
    private String secret;

    public String getJWT(final String subject) {
        Objects.requireNonNull(subject);

        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getSubject(final String jwt) {
        Objects.requireNonNull(jwt);

        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(jwt.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
    }

}