package com.example.servigo.DTOs;

import lombok.Data;

import java.time.LocalDateTime;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;
    private Long utilisateurId;
    private String message;
    private LocalDateTime dateEnvoi;
}