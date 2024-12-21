package com.wildcodeschool.webook.Auth.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class ApiConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Tous les chemins
                .allowedOrigins("https://webook-fr.vercel.app", "https://webook-dev.vercel.app", "http://localhost:4200") // URL(s) autorisée(s)
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE") // Méthodes HTTP autorisées
                .allowCredentials(true)
                .maxAge(3600);
    }
}
