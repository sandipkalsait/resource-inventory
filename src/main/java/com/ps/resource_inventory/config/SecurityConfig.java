package com.ps.resource_inventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import com.ps.resource_inventory.filter.RateLimitingFilter;

@Configuration
public class SecurityConfig {

    private final RateLimitingFilter rateLimitingFilter;

    public SecurityConfig(RateLimitingFilter rateLimitingFilter) {
        this.rateLimitingFilter = rateLimitingFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Disable CSRF for simplicity
            .authorizeHttpRequests(auth -> 
                auth.requestMatchers("/inventory/**").permitAll() // Allow access to /inventory/**
                    .anyRequest().authenticated() // All other requests require authentication
            )
            .httpBasic() // Enable Basic Authentication
            .and()
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless sessions
            .addFilterBefore(rateLimitingFilter, BasicAuthenticationFilter.class); // Add the Rate Limiting Filter

        return http.build();
    }

   
}
