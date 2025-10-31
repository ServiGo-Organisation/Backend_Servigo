package com.example.servigo.Services.Utilisateur;


import com.example.servigo.Entites.Client;
import com.example.servigo.Entites.Prestateur;
import com.example.servigo.Entites.PrestateurClient;
import com.example.servigo.Entites.Utilisateur;
import com.example.servigo.Enums.TypeUtilisateur;
import com.example.servigo.Repositories.ClientRepository;
import com.example.servigo.Repositories.PrestateurRepository;
import com.example.servigo.Repositories.UtilisateurRepository;
import jdk.jshell.execution.Util;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor

public class UtilisateurServiceInterfaceImpl implements UtilisateurServiceInterface {
    UtilisateurRepository utilisateurRepository;
    ClientRepository clientRepository;
    PrestateurRepository prestateurRepository;
    @Override
    public Utilisateur loadByEmail(String email) {
        return utilisateurRepository.findByEmail(email);
    }

    @Override
    public Utilisateur creationCompte(Utilisateur utilisateur) {
        // Vérifications basiques
        if (utilisateur.getNom() == null || utilisateur.getNom().isEmpty()) {
            throw new IllegalArgumentException("Le nom est requis.");
        }

        if (utilisateur.getEmail() == null || utilisateur.getEmail().isEmpty()) {
            throw new IllegalArgumentException("L'email est requis.");
        }

        if (utilisateur.getMotDePasse() == null || utilisateur.getMotDePasse().isEmpty()) {
            throw new IllegalArgumentException("Le mot de passe est requis.");
        }

        // Vérifier si l'utilisateur existe déjà
//        if (utilisateurRepository.findByEmail(utilisateur.getEmail()) != null) {
//            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà.");
//        }

        // Hachage du mot de passe
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        utilisateur.setMotDePasse(encoder.encode(utilisateur.getMotDePasse()));

        // Gestion par type
        Utilisateur utilisateurCree = null;


        switch (utilisateur.getTypeUtilisateur()) {
            case CLIENT:
                Client client = new Client();
                copyCommonFields(utilisateur, client);
                utilisateurCree = clientRepository.save(client); // insert dans utilisateur + client
                break;

            case PRESTATAIRE:
                Prestateur prestateur = new Prestateur();
                copyCommonFields(utilisateur, prestateur);
                utilisateurCree = prestateurRepository.save(prestateur); // insert dans utilisateur + prestateur
                break;

            case PRESTATEUR_CLIENT:
                PrestateurClient both = new PrestateurClient();
                copyCommonFields(utilisateur, both);
                utilisateurCree = utilisateurRepository.save(both); // insert dans utilisateur + prestateur_client
                break;
            default:
                // Si pas de type, on enregistre comme simple utilisateur
                utilisateurCree = utilisateurRepository.save(utilisateur);
        }

        return utilisateurCree;
    }


    private void copyCommonFields(Utilisateur source, Utilisateur target) {
        target.setNom(source.getNom());
        target.setPrenom(source.getPrenom());
        target.setEmail(source.getEmail());
        target.setMotDePasse(source.getMotDePasse());
        target.setTelephone(source.getTelephone());
        target.setGenre(source.getGenre());
        target.setDateNaissance(source.getDateNaissance());
        target.setTypeUtilisateur(source.getTypeUtilisateur());
        target.setUserImage(source.getUserImage());
    }

    @Override
    public Utilisateur userInfo(Long id) {
        Utilisateur userInfo = utilisateurRepository.findByIdUtilisateur(id);
        return userInfo;
    }

    @Override
    public Utilisateur getUserInformations(Long idUtilisateur) {
        return utilisateurRepository.findByIdUtilisateur(idUtilisateur);
    }

    @Override
    public void status(Long idPrestateur){
        Utilisateur utilisateur = utilisateurRepository.findByIdUtilisateur(idPrestateur);
        utilisateur.set_online(!utilisateur.is_online());
        utilisateurRepository.save(utilisateur);
    }

    @Override
    public List<Prestateur> getPrestateursByVille(String ville) {
        return prestateurRepository.findByVille(ville);
    }

    @Override
    public List<Prestateur> getPrestateursByVilleAndService(String ville, String service) {
        return prestateurRepository.findOnlinePrestateursByVilleAndService(ville, service);
    }


}
