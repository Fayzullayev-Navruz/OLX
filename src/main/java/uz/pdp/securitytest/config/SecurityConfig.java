package uz.pdp.securitytest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private final DBUserDetailsService userDetailsService;

    public SecurityConfig(DBUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/api/auth/**",
                                "/api-docs/",
                                "/swagger-ui/",
                                "/swagger-ui.html/",
                                "/swagger-resources/",
                                "/webjars/",
                                "/index.html",
                                "/static/**",
                                "/admin.html").permitAll()
                        .requestMatchers(HttpMethod.POST,
                                "/users/register",
                                "/users/login",
                                "/users/forgot-password",
                                "/users/reset-password/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/users", "/users/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/users/**").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/users/**").permitAll()
                        .requestMatchers("/users/{userId}/assignRole").permitAll()
                        .anyRequest().permitAll()
                );
        http.csrf(AbstractHttpConfigurer::disable);

        http.userDetailsService(userDetailsService);
              //  .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // Sessiyasiz ishlash

        return http.build();
    }
}

