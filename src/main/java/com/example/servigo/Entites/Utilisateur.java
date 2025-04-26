package com.example.servigo.Entites;

import com.example.servigo.Enums.TypeUtilisateur;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUtilisateur;
    private String nom;
    private String prenom;
    private String email;
    private String motDePasse;
    private String telephone;
    // a faire male femelle enum
    private String genre;
    private LocalDate dateNaissance;
    @Enumerated(EnumType.STRING)
    private TypeUtilisateur typeUtilisateur;
}
