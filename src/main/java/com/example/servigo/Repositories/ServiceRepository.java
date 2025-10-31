package com.example.servigo.Repositories;

import com.example.servigo.Entites.Service;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ServiceRepository extends JpaRepository<Service, Long> {
    Optional<Service> findByNom(String nom);
    boolean existsByNom(String nom);
}
