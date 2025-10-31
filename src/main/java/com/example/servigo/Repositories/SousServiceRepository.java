package com.example.servigo.Repositories;

import com.example.servigo.Entites.SousService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface SousServiceRepository extends JpaRepository<SousService, Long> {

    // Trouver tous les sous-services d'un service
    List<SousService> findByServiceId(Long serviceId);

    // Trouver un sous-service par ID et service ID
    Optional<SousService> findByIdAndServiceId(Long id, Long serviceId);

    // Trouver plusieurs sous-services par leurs IDs
    List<SousService> findByIdIn(List<Long> ids);

    // Vérifier si un sous-service appartient à un service
    boolean existsByIdAndServiceId(Long id, Long serviceId);
}