package com.example.servigo.Controllers;

import com.example.servigo.Entites.Utilisateur;
import com.example.servigo.Services.Utilisateur.UtilisateurServiceInterfaceImpl;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/utilisateur")
@AllArgsConstructor
public class UtilisateurController {
    UtilisateurServiceInterfaceImpl utilisateurServiceInterface;

    @PostMapping("/addUtilisateur")
    public ResponseEntity<String> addUtilisateur(@RequestBody Utilisateur utilisateur) {
        try {
            Utilisateur newUser = utilisateurServiceInterface.creationCompte(utilisateur);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Utilisateur cree avec succee");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }

    /*
    * AKRAM ANOU
    * SAFAE KARAMA
    * */


}
