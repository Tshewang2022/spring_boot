package com.tshewang.employee.security;

// i will make something very valuable in the niche market and then
// develop into the bigger space
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    // pulling the information from the database
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(configurer ->
                configurer
                        // H2 Console - allow all access
                        .requestMatchers("/h2-console/**").permitAll()

                        // Swagger/API Documentation
                        .requestMatchers("/api/docs/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()

                        // Employee API endpoints with role-based access
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/employees/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")

                        // CRITICAL: Any other request requires authentication
                        .anyRequest().authenticated()
        );

        // FIXED: Only enable HTTP Basic authentication (removed the conflicting disable)
        http.httpBasic(Customizer.withDefaults());

        // Custom error handling for API responses
        http.exceptionHandling(exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(authenticationEntryPoint()));

        // Required for H2 Console to work properly
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));

        // CRITICAL: Disable CSRF for H2 Console and API endpoints
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(){
        return (request, response, authException) -> {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json");

            // Keep WWW-Authenticate header for proper basic auth
            response.setHeader("WWW-Authenticate", "Basic realm=\"Employee API\"");
            response.getWriter().write("{\"error\":\"Unauthorized access\", \"message\":\"Please provide valid credentials\"}");
        };
    }
}