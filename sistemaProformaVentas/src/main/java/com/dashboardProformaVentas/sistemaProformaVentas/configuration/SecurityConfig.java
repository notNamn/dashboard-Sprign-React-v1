package com.dashboardProformaVentas.sistemaProformaVentas.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors() // Habilitar CORS
                .and()
                .csrf().disable() // Deshabilitar CSRF para pruebas
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/auth/**",
                                "/dashboard/**",
                                "/ventas/**").permitAll() // Permitir acceso a autenticación
                        // .requestMatchers("/dashboard/productos/**", "/dashboard/ventas/**", "/dashboard/proformas/**", "/dashboard/categorias/**").authenticated() // Requiere autenticación
                        // .anyRequest().authenticated() // El resto de las rutas también requieren autenticación
                        .anyRequest().permitAll()
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

