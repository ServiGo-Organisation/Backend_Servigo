package com.example.servigo.Entites;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String description;

    @ManyToOne
    private Prestateur prestateur;
}