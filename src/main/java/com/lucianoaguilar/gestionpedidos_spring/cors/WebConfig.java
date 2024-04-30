package com.lucianoaguilar.gestionpedidos_spring.cors;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {



    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // Establece el mapeo para las rutas de tu API
                .allowedOrigins("http://127.0.0.1:4200"); // Cambia  por el origen del frontend
    }

}
