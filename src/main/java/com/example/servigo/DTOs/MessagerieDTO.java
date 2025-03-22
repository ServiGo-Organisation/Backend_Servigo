package com.example.servigo.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessagerieDTO {
    private Long id;
    private Long clientId;
    private Long serveurId;
    private String message;
    private LocalDateTime dateEnvoi;
}