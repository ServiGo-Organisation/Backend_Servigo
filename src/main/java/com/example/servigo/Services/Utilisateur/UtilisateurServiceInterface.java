package com.example.servigo.Services.Utilisateur;


import com.example.servigo.Entites.Prestateur;
import com.example.servigo.Entites.Utilisateur;
import com.example.servigo.Enums.TypeUtilisateur;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UtilisateurServiceInterface {
    Utilisateur loadByEmail(String username);
    Utilisateur creationCompte(Utilisateur utilisateur);
    Utilisateur userInfo(Long id);

    Utilisateur getUserInformations(Long idUtilisateur);
//    String updateTypeUtilisateur(Long idUtilisateur,TypeUtilisateur typeUtilisateur);

    // status onLine offLine
    void status(Long idPrestateur);
    List<Prestateur> getPrestateursByVille(String ville);
    List<Prestateur> getPrestateursByVilleAndService(String ville, String service);


}
