package com.example.servigo.Controllers;

import com.example.servigo.DTOs.ServiceDTO;
import com.example.servigo.Entites.Prestateur;
import com.example.servigo.Entites.Service;
import com.example.servigo.Services.Service.ServicesServiceInterface;
import com.example.servigo.Services.Utilisateur.UtilisateurServiceInterface;
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
    UtilisateurServiceInterface utilisateurServiceInterface;
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
    public ResponseEntity<List<ServiceDTO>> getAllServices() {
        try {
            List<ServiceDTO> services = servicesServiceInterface.getAllServices();
            return ResponseEntity.ok(services);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }


    // getting here the name of service and show the prestataire avec ce role just for tries
    @GetMapping("/serviceName/{nomService}")
    public ResponseEntity<List<Prestateur>> getPrestateursByRoleName(@PathVariable String nomService) {
        List<Prestateur> prestateurs=servicesServiceInterface.getPrestateursByServiceName(nomService);
        return ResponseEntity.ok(prestateurs);
    }

    // affecter a un prestateur un role (partie adim apres confirmation)


}
