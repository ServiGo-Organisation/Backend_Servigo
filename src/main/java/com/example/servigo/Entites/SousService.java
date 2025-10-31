package com.example.servigo.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SousService {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private Double montant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id", nullable = false)
    @JsonIgnore
    private Service service;

    // Constructeur par défaut
    public SousService() {}

    // Constructeur pratique
    public SousService(String nom, Double montant, Service service) {
        this.nom = nom;
        this.montant = montant;
        this.service = service;
    }
}