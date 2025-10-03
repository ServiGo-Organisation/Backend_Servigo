package com.example.servigo.Services.Service;

import com.example.servigo.DTOs.ServiceDTO;
import com.example.servigo.Entites.Prestateur;
import com.example.servigo.Entites.Service;
import com.example.servigo.Repositories.PrestateurRepository;
import com.example.servigo.Repositories.ServiceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServicesServiceInterfaceImpl implements ServicesServiceInterface {
    ServiceRepository serviceRepository;
    PrestateurRepository prestateurRepository;
    ObjectMapper objectMapper;
//    public void addService(String serviceJson, MultipartFile file) throws IOException {
//        // Convertir JSON en objet
//        Service service = objectMapper.readValue(serviceJson, Service.class);
//
//        // Gestion de l'image
//        if (file != null && !file.isEmpty()) {
//            String uploadDir = System.getProperty("user.dir") + "/assets/servicesImages/";
//            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
//            Path filePath = Paths.get(uploadDir, fileName);
//
//            Files.createDirectories(filePath.getParent());
//            Files.write(filePath, file.getBytes());
//
//            service.setServiceImage(fileName);
//        }
//
//        serviceRepository.save(service);
//    }
@Override
public Service addService(String serviceJson, MultipartFile file) throws IOException {
    Service serviceData = objectMapper.readValue(serviceJson, Service.class);

    Service service = new Service();
    service.setNom(serviceData.getNom());
    service.setDescription(serviceData.getDescription());

    if (file != null && !file.isEmpty()) {
        String uploadDir = System.getProperty("user.dir") + "/assets/servicesImages/";
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        Files.createDirectories(filePath.getParent());
        Files.write(filePath, file.getBytes());

        service.setServiceImage(fileName);
    }

    // Sauvegarder le service d'abord pour générer l'ID
    service = serviceRepository.save(service);

    // Associer le service aux prestataires (côté propriétaire)
    if (serviceData.getPrestateurs() != null) {
        for (Prestateur p : serviceData.getPrestateurs()) {
            Prestateur prestateur = prestateurRepository.findByIdUtilisateur(p.getIdUtilisateur());
            if (prestateur != null) {
                prestateur.getServices().add(service); // ✅ côté propriétaire
                prestateurRepository.save(prestateur);
            }
        }
    }

    return service;
}



    @Override
    public List<ServiceDTO> getAllServices() {
        return serviceRepository.findAll().stream().map(service -> {
            ServiceDTO dto = new ServiceDTO();
            dto.setId(service.getId());
            dto.setNom(service.getNom());
            dto.setDescription(service.getDescription());
            dto.setServiceImage(service.getServiceImage());

            // ⚡ Récupérer seulement les IDs des prestataires pour éviter lazy-loading et cycles
            dto.setPrestateurIds(
                    service.getPrestateurs().stream()
                            .map(Prestateur::getIdUtilisateur)
                            .toList()
            );

            return dto;
        }).toList();
    }


    // Update service


}
