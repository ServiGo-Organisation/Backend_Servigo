package com.example.servigo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class ServiGoApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiGoApplication.class, args);
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoderCoach() {
        return new BCryptPasswordEncoder();
    }

}
