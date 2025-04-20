package com.example.servigo.Services.Utilisateur;


import com.example.servigo.Entites.Utilisateur;

public interface UtilisateurServiceInterface {
    Utilisateur loadByEmail(String username);
    Utilisateur creationCompte(Utilisateur utilisateur);
    Utilisateur userInfo(Long id);


}
