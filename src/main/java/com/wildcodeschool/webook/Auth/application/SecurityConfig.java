package com.wildcodeschool.webook.Auth.application;

import com.wildcodeschool.webook.Auth.domain.service.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/login", "/register", "/password-forgotten/**", "/new-password/**").permitAll()
                        .requestMatchers("/users/**", "/uploads/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/books/**", "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/books/**", "/categories/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/books/**", "/categories/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/books/**", "/categories/**").authenticated()

                        .requestMatchers(HttpMethod.GET, "/categories/**", "/categories/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/categories/**", "/categories/**").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/categories/**", "/categories/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/categories/**", "/categories/**").authenticated()
                )
                .csrf((csrf) -> csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // csrf protection
                        .ignoringRequestMatchers("/register", "/login")
                        .disable()  // comment to enable CSRF protection
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // we use JWT , no need for session
                );

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}