package com.example.servigo.DTOs;

import lombok.Data;

import java.util.Set;

@Data
public class PrestateurDTO {
    private Long id;
    private String prenom;
    private String nom;
    private String roles;
    private double solde;

    private Double latitude;
    private Double longitude;
}
