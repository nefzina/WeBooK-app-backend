package com.wildcodeschool.webook.Auth.domain.service;

import com.wildcodeschool.webook.Auth.domain.dto.UserPrincipal;
import com.wildcodeschool.webook.Auth.domain.entity.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {
    public static final long JWT_TOKEN_VALIDITY = 60L * 60L; // 1 hour
    //private static String secretKey = "PXdrWTF3JmRvOEV0+OT1lQWU0CjBZbjRrVG9jZG9/LWkpzdiY4QQojV2ZWZEh2YzlDRnhyMUVGYmIKNkU9KlN0Y0FPViFYK0JWSm+1CCmd1b21PYjExTmYkIzUySCZobgpSWCROVUQjRCV/VYUE1Iyo2cCoKT3BiRnZaSDkmZDE3UkNQNnNl";

    public Token generateToken(UserPrincipal userPrincipal) {
        Date now = new Date();
        Token jwt = new Token();
        jwt.setToken(Jwts.builder()
                .subject(userPrincipal.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(now.getTime() + JWT_TOKEN_VALIDITY * 1000L))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact());
        return jwt;
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(${{ secrets.JWT_SECRET_KEY }});
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getEmailFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(${{ secrets.JWT_SECRET_KEY }})
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isTokenValid(String token, UserPrincipal userPrincipal) {
        final String email = getEmailFromToken(token);
        return (email.equals(userPrincipal.getEmail()) && !isTokenExpired(token));
    }

    public Date extractExpiration(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
