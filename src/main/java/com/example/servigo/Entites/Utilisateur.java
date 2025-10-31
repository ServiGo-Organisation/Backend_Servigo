package com.example.servigo.Entites;

import com.example.servigo.Enums.TypeUtilisateur;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.Date;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateNaissance;
    private String userImage;
    private String ville;
    private boolean is_online;// default is false sure ?
    @Enumerated(EnumType.STRING)
    private TypeUtilisateur typeUtilisateur=null;
}
