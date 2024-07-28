package com.wildcodeschool.webook.Auth.domain.service;

import com.wildcodeschool.webook.Auth.domain.entity.Token;
import com.wildcodeschool.webook.Auth.domain.entity.User;
import com.wildcodeschool.webook.Auth.infrastructure.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class CookieService {
    private final HttpServletRequest request;
    private final JwtService jwtService;

    private final UserRepository userRepository;
    public CookieService(HttpServletRequest request, JwtService jwtService, UserRepository userRepository) {
        this.request = request;

        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }


    public ResponseCookie createCookie(Token token) {
        return ResponseCookie.from("token", token.getToken())
                .httpOnly(true)   // Marquer le cookie comme HttpOnly pour la sécurité
                //.secure(true)     // Marquer le cookie comme sécurisé (transmis uniquement via HTTPS)
                .path("/")        // Le cookie est accessible pour l'ensemble du domaine
                .maxAge(60 * 60) // Définir la durée de vie du cookie (exemple : 24 heures)
                .sameSite("Strict") // Politique SameSite pour le cookie
                .build();

    }
    public User getUserByCookie() {
        String jwt = Arrays.stream(request.getCookies())
                .filter(cookie -> "token".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        return userRepository.findByEmail(jwtService.getEmailFromToken(jwt));

    }
}


