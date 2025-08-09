package com.example.servigo.Entites;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Localisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double latitude;
    private double longitude;

    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "utilisateur_id", unique = true) // Clé étrangère UNIQUE
    private Utilisateur utilisateur;

}
