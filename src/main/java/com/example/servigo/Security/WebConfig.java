package com.example.servigo.Security;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    // Injection de la propriété upload.path définie dans application.properties ou via variable d'environnement
    @Value("${upload.path}")
    private String uploadPath;

    @Value("${upload.path2}")
    private String uploadPath2;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // autorise toutes les routes
                        .allowedOrigins("http://192.168.43.43:8087") // adresse front-end
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // méthodes autorisées
                        .allowedHeaders("*") // autorise tous les headers
                        .allowCredentials(true); // autoriser cookies et authentification
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                // Serve les fichiers images depuis le chemin configuré
                // N'oublie pas d'ajouter "/" à la fin de uploadPath si besoin
                String path = uploadPath.endsWith("/") ? uploadPath : uploadPath + "/";
                String path2 = uploadPath2.endsWith("/") ? uploadPath2 : uploadPath2 + "/";
                registry.addResourceHandler("/assets/userProfile/**")
                        .addResourceLocations("file:" + path);
                registry.addResourceHandler("/assets/servicesImages/**")
                        .addResourceLocations("file:" + path2);
            }
        };
    }
}
