package com.example.servigo.DTOs;

import lombok.Data;


import lombok.Data;

@Data
public class ServiceDTO {
    private Long id;
    private String nom;
    private String description;
    private Long serveurId;
}