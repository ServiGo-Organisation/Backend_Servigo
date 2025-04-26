package com.example.servigo.Repositories;

import com.example.servigo.Entites.Client;
import com.example.servigo.Entites.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client,Long> {
}
