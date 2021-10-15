package com.Team4.FetchNoteServer.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:3000", "http://127.0.0.1:5500")
                .allowedMethods("GET", "POST", "DELETE", "PATCH", "PUT")
                .allowCredentials(true);
    }
}