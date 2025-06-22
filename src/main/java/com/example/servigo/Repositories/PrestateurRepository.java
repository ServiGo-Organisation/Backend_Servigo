package com.example.servigo.Repositories;

import com.example.servigo.Entites.Client;
import com.example.servigo.Entites.Prestateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrestateurRepository extends JpaRepository<Prestateur,Long> {
    Prestateur findByIdUtilisateur(Long idUtilisateur);

}
