package com.dashboardProformaVentas.sistemaProformaVentas.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Permitir todas las rutas
                .allowedOrigins("http://localhost:5173") // Tu URL del frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS","PATCH") // Métodos permitidos
                .allowedHeaders("*") // Permitir todos los headers
                .allowCredentials(true); // Permitir cookies y autenticación
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/api/**")
//                .allowedOrigins("http://localhost:5173") // Frontend local
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//
//        // Agregar también el mapeo para /dashboard
//        registry.addMapping("/dashboard/**")
//                .allowedOrigins("http://localhost:5173") // Frontend local
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//
//        // Agregar también el mapeo para /api/ventas
//        registry.addMapping("/ventas/**")
//                .allowedOrigins("http://localhost:5173") // Frontend local
//                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
//                .allowedHeaders("*")
//                .allowCredentials(true);
//    }
}

