package com.example.servigo.Controllers;

import com.example.servigo.DTOs.UpdateTypeRequest;
import com.example.servigo.Entites.Utilisateur;
import com.example.servigo.Enums.TypeUtilisateur;
import com.example.servigo.Services.Utilisateur.UtilisateurServiceInterfaceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/utilisateur")
@AllArgsConstructor
public class UtilisateurController {
    UtilisateurServiceInterfaceImpl utilisateurServiceInterface;
    ObjectMapper objectMapper;
    @PostMapping("/addUtilisateur")
    public ResponseEntity<String> addUtilisateur(
            @RequestPart("utilisateur") String utilisateurJson,
            @RequestPart(value = "userImage", required = false) MultipartFile file
    ) {
        try {
            // Convertir JSON string en objet
            Utilisateur utilisateur = objectMapper.readValue(utilisateurJson, Utilisateur.class);

            // Gérer l'image si elle est présente
            if (file != null && !file.isEmpty()) {
                String uploadDir = System.getProperty("user.dir") + "/assets/userProfile/";
                String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
                Path filePath = Paths.get(uploadDir, fileName);

                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getBytes());

                utilisateur.setUserImage(fileName);
            }

            utilisateurServiceInterface.creationCompte(utilisateur);
            return ResponseEntity.status(HttpStatus.CREATED).body("Utilisateur créé avec succès");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la création de l'utilisateur : " + e.getMessage());
        }
    }




//    @PostMapping("/updateTypeOfuser")
//    public ResponseEntity<String> updateTypeUtilisateur(@RequestBody UpdateTypeRequest request) {
//        try {
//            String message = utilisateurServiceInterface.updateTypeUtilisateur(
//                    request.getIdUtilisateur(),
//                    request.getTypeUtilisateur()
//            );
//            return ResponseEntity.ok(message);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
//        }
//    }



    @GetMapping("/user-info")
    public ResponseEntity<?> userInformations(@RequestParam Long idUtilisateur) {
        try {
            Utilisateur utilisateur = utilisateurServiceInterface.getUserInformations(idUtilisateur);
            if (utilisateur != null) {
                String photo = utilisateur.getUserImage();
                if (photo != null && !photo.isEmpty()) {
                    String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
                    String imageUrl = baseUrl + "/assets/userProfile/" + photo;
                    utilisateur.setUserImage(imageUrl);
                }
                return ResponseEntity.ok(utilisateur);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilisateur introuvable");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la récupération des informations utilisateur");
        }
    }



    @PostMapping("/uploadPhoto")
    public ResponseEntity<String> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            String uploadDir = "assets/userProfile/";
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir + fileName);

            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            return ResponseEntity.ok(fileName); // <== le front reçoit ce nom
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de l'upload de l'image.");
        }
    }




}
