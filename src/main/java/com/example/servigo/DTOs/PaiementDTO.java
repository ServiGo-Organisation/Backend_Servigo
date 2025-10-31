package com.example.servigo.DTOs;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PaiementDTO {
    private Long id;
    private double montant;
    private LocalDateTime date;
    private Long serveurId;
}