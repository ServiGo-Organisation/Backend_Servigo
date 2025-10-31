package com.example.servigo.Repositories;

import com.example.servigo.Entites.ClientServiceChoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ClientServiceChoiceRepository extends JpaRepository<ClientServiceChoice, Long> {

    List<ClientServiceChoice> findByClientIdUtilisateur(Long clientId);

    @Query("SELECT csc FROM ClientServiceChoice csc WHERE csc.client.idUtilisateur = :clientId AND csc.service.id = :serviceId")
    ClientServiceChoice findByClientAndService(@Param("clientId") Long clientId, @Param("serviceId") Long serviceId);
}