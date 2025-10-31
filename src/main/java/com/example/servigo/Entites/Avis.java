package com.example.servigo.Entites;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Avis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Prestateur prestateur;

    private int note;
    private String commentaire;
    private LocalDateTime dateAvis;
}