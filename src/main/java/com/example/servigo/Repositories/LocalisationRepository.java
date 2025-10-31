package com.example.servigo.Repositories;

import com.example.servigo.Entites.Localisation;
import com.example.servigo.Entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LocalisationRepository extends JpaRepository<Localisation,Long> {
    Optional<Localisation> findByUtilisateur(Utilisateur utilisateur);
    @Query("SELECT l FROM Localisation l WHERE l.utilisateur.typeUtilisateur = 'PRESTATAIRE'")
    List<Localisation> findAllPrestateurs();
}
