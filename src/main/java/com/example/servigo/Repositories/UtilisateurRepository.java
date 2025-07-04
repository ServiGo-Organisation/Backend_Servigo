package com.example.servigo.Repositories;

import com.example.servigo.Entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
    Utilisateur findByEmail(String email);
    Utilisateur findByIdUtilisateur(Long id);
    Utilisateur findByPrenomAndNom(String nom , String prenom);
}
