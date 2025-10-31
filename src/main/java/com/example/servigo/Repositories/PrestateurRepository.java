package com.example.servigo.Repositories;

import com.example.servigo.Entites.Client;
import com.example.servigo.Entites.Prestateur;
import com.example.servigo.Entites.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.PreparedStatement;
import java.util.List;

public interface PrestateurRepository extends JpaRepository<Prestateur,Long> {
    Prestateur findByIdUtilisateur(Long idUtilisateur);
    List<Prestateur> findByRolesIgnoreCase(String role);
    List<Prestateur> findByVille(String ville);

    @Query("SELECT p FROM Prestateur p " +
            "WHERE LOWER(p.ville) = LOWER(:ville) " +
            "AND p.is_online = true " +
            "AND LOWER(p.roles) = LOWER(:service)")
    List<Prestateur> findOnlinePrestateursByVilleAndService(
            @Param("ville") String ville,
            @Param("service") String service
    );

    List<Prestateur> findByServicesContaining(Service service);

    List<Prestateur> findByServicesId(Long serviceId);
}
