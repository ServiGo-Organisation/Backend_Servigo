package com.example.servigo.Services.Service;

import com.example.servigo.DTOs.ServiceDTO;
import com.example.servigo.Entites.Prestateur;
import com.example.servigo.Entites.Service;
import com.example.servigo.Entites.SousService;
import com.example.servigo.Repositories.PrestateurRepository;
import com.example.servigo.Repositories.ServiceRepository;
import com.example.servigo.Repositories.SousServiceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServicesServiceInterfaceImpl implements ServicesServiceInterface {

    private final ServiceRepository serviceRepository;
    private final PrestateurRepository prestateurRepository;
    private final SousServiceRepository sousServiceRepository;

    public Service createService(Service service) {
        // Vérifier si le rôle existe déjà
        if (serviceRepository.existsByNom(service.getNom())) {
            throw new RuntimeException("Un service  nom '" + service.getNom() + "' existe déjà");
        }
        return serviceRepository.save(service);
    }

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<Service>getServiceByName(String nom) {
        return serviceRepository.findByNom(nom);
    }
    public Prestateur addServiceToPrestateur(Long prestateurId, Long serviceId) {
        Prestateur prestateur = prestateurRepository.findById(prestateurId)
                .orElseThrow(() -> new RuntimeException("Prestateur non trouvé avec ID: " + prestateurId));
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service non trouvé avec ID: " + serviceId));

        prestateur.getServices().add(service);
        return prestateurRepository.save(prestateur);
    }
    // API 1: Lister tous les sous-services d'un service
    @Override
    public List<SousService> getSousServicesByService(Long serviceId) {
        // Vérifier que le service existe
        serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service non trouvé avec ID: " + serviceId));

        return sousServiceRepository.findByServiceId(serviceId);
    }

    // API 2: Calculer le montant total pour des sous-services sélectionnés
    @Override
    @Transactional
    public Service calculerMontantTotalService(Long serviceId, List<Long> sousServiceIds) {
        // Vérifier que le service existe
        Service service = serviceRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service non trouvé avec ID: " + serviceId));

        // Vérifier que la liste des sous-services n'est pas vide
        if (sousServiceIds == null || sousServiceIds.isEmpty()) {
            throw new RuntimeException("La liste des sous-services sélectionnés ne peut pas être vide");
        }

        // Récupérer les sous-services sélectionnés
        List<SousService> sousServicesSelectionnes = sousServiceRepository.findByIdIn(sousServiceIds);

        // Vérifier que tous les sous-services existent
        if (sousServicesSelectionnes.size() != sousServiceIds.size()) {
            throw new RuntimeException("Certains sous-services n'existent pas");
        }

        // Vérifier que tous les sous-services appartiennent au service spécifié
        for (SousService sousService : sousServicesSelectionnes) {
            if (!sousService.getService().getId().equals(serviceId)) {
                throw new RuntimeException("Le sous-service " + sousService.getId() + " n'appartient pas au service " + serviceId);
            }
        }

        // Calculer la somme des montants
        Double montantTotal = sousServicesSelectionnes.stream()
                .mapToDouble(SousService::getMontant)
                .sum();

        // Mettre à jour le budget total du service
        service.setBudgetTotal(montantTotal);

        // Sauvegarder le service avec le nouveau budget total
        Service serviceMisAJour = serviceRepository.save(service);

        // Optionnel: Stocker les IDs des sous-services sélectionnés (si besoin)
        // Vous pouvez créer un champ supplémentaire dans Service pour stocker cette information
        // service.setSousServicesSelectionnes(new HashSet<>(sousServiceIds));

        return serviceMisAJour;
    }
    // Méthode pour récupérer les prestataires par service
    public List<Prestateur> getPrestatairesByService(String nomService) {
        Service service = serviceRepository.findByNom(nomService)
                .orElseThrow(() -> new RuntimeException("Service non trouvé: " + nomService));
        return prestateurRepository.findByServicesContaining(service);
    }
}


