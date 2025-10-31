package com.example.servigo.DTOs;

import lombok.Data;

import java.time.LocalDateTime;


import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AvisDTO {
    private Long id;
    private Long clientId;
    private Long serveurId;
    private int note;
    private String commentaire;
    private LocalDateTime dateAvis;
}