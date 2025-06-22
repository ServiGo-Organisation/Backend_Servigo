package com.example.servigo.Services.Service;

import com.example.servigo.Entites.Service;
import com.example.servigo.Repositories.ServiceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServicesServiceInterfaceImpl implements ServicesServiceInterface {
    ServiceRepository serviceRepository;
    ObjectMapper objectMapper;
    @Override
    public void addService(String serviceJson, MultipartFile file) throws IOException {
        // Convertir JSON en objet
        Service service = objectMapper.readValue(serviceJson, Service.class);

        // Gestion de l'image
        if (file != null && !file.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + "/assets/servicesImages/";
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            service.setServiceImage(fileName);
        }

        serviceRepository.save(service);
    }

    @Override
    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

}
