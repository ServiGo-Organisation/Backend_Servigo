package com.example.servigo.Repositories;
import com.example.servigo.Enums.TypeUtilisateur;

import com.example.servigo.Entites.Utilisateur;
import com.example.servigo.Enums.TypeUtilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {
    Utilisateur findByEmail(String email);
    Utilisateur findByIdUtilisateur(Long id);
    Utilisateur findByPrenomAndNom(String nom , String prenom);
    List<Utilisateur> findAllByTypeUtilisateur(TypeUtilisateur typeUtilisateur);


}
