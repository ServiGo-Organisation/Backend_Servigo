package com.example.servigo.Repositories;

import com.example.servigo.Entites.Prestateur;
import com.example.servigo.Entites.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PrestateurRepository extends JpaRepository<Prestateur, Long> {
    Prestateur findByIdUtilisateur(Long idUtilisateur);
    List<Prestateur> findByServicesContaining(Service service);

    List<Prestateur> findByServicesId(Long serviceId);
}