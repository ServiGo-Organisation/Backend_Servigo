package com.example.servigo.DTOs;

import lombok.Data;



import java.util.List;

@Data
public class ServiceDTO {
    private Long id;
    private String nom;
    private String description;
    private String serviceImage;
    private List<Long> prestateurIds;
}