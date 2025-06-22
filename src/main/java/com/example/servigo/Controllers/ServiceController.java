package com.example.servigo.Controllers;

import com.example.servigo.Entites.Service;
import com.example.servigo.Services.Service.ServicesServiceInterface;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@AllArgsConstructor
public class ServiceController {

    ServicesServiceInterface servicesServiceInterface;
    @PostMapping("/addService")
    public ResponseEntity<String> addService(
            @RequestPart("service") String serviceJson,
            @RequestPart(value = "serviceImage", required = false) MultipartFile file
    ) {
        try {
            servicesServiceInterface.addService(serviceJson, file);
            return ResponseEntity.status(HttpStatus.CREATED).body("Service créé avec succès");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création du service : " + e.getMessage());
        }
    }
    @GetMapping("/getAllServices")
    public ResponseEntity<List<Service>> getAllServices() {
        try {
            List<Service> services = servicesServiceInterface.getAllServices();
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }

}
