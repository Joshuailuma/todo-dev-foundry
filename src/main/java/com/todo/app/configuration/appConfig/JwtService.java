package com.todo.app.configuration.appConfig;

import com.todo.app.model.entity.Token;
import com.todo.app.exception.ApiError;
import com.todo.app.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final TokenRepository tokenRepository;
    private final Long expiration = 400000000L;

    private String SECRET_KEY = "4f3a6d2f86d6342f7a5bdf78c0917c4934e5d02f40b713e5e9b9bfa1fa8692cb";

    public String extractUsername(String jwtToken) {
        return extractClaims(jwtToken, Claims::getSubject);
    }

    private <T> T extractClaims(String jwtToken, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(jwtToken);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String jwtToken) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(jwtToken).getBody();
    }

    private Key getSignInKey() {
        byte[] keyByte = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);
    }

    public boolean isTokenValidated(String jwtToken, UserDetails userDetails) {
        String username = extractUsername(jwtToken);
        Optional<Token> optionalToken = tokenRepository.findByToken(jwtToken);
        Token token;
        if (optionalToken.isPresent()) {
            token = optionalToken.get();
            if (token.isRevoked()) {
                throw new ApiError("Token is revoked", HttpStatus.BAD_REQUEST);
            }
        } else {
            throw new ApiError("Token not found", HttpStatus.NOT_FOUND);
        }

        return (userDetails.getUsername().equals(username) && !isExpired(jwtToken));
    }

    private boolean isExpired(String jwtToken) {
        return extractExpiration(jwtToken).before(new Date());
    }

    private Date extractExpiration(String jwtToken) {
        return extractClaims(jwtToken, Claims::getExpiration);
    }

    public String generateToken(UserDetails userDetails) {
        Collection<? extends SimpleGrantedAuthority> authorities = (Collection<? extends SimpleGrantedAuthority>) userDetails.getAuthorities();

        List<String> roleNames = new ArrayList<>();

        for (SimpleGrantedAuthority authority : authorities) {
            roleNames.add(authority.getAuthority());
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", roleNames);
        return buildToken(claims, userDetails, expiration);
    }

    private String buildToken(Map<String, Object> claims, UserDetails userDetails, Long expiration) {
        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis())).setExpiration(new Date(System.currentTimeMillis() + expiration)).setSubject(userDetails.getUsername()).signWith(SignatureAlgorithm.HS256, getSignInKey()).compact();
    }

    public Long getJwtExpiration() {
        return expiration;
    }
    }
