package com.example.servigo.Services.Utilisateur;


import com.example.servigo.Entites.Client;
import com.example.servigo.Entites.Prestateur;
import com.example.servigo.Entites.Utilisateur;
import com.example.servigo.Enums.TypeUtilisateur;
import com.example.servigo.Repositories.ClientRepository;
import com.example.servigo.Repositories.PrestateurRepository;
import com.example.servigo.Repositories.UtilisateurRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


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

        if (utilisateur.getTypeUtilisateur() == null) {
            throw new IllegalArgumentException("Le type d'utilisateur est requis.");
        }

        // Vérifier si l'utilisateur existe déjà
        if (utilisateurRepository.findByEmail(utilisateur.getEmail()) != null) {
            throw new IllegalArgumentException("Un utilisateur avec cet email existe déjà.");
        }

        // Hachage du mot de passe
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        utilisateur.setMotDePasse(encoder.encode(utilisateur.getMotDePasse()));

        // Créer l’utilisateur selon son type
        switch (utilisateur.getTypeUtilisateur()) {
            case CLIENT:
                Client client = new Client();
                copyCommonFields(utilisateur, client);
                return clientRepository.save(client); // insère dans utilisateur + client

            case PRESTATAIRE:
                Prestateur prestateur = new Prestateur();
                copyCommonFields(utilisateur, prestateur);
                return prestateurRepository.save(prestateur); // insère dans utilisateur + prestateur

            default:
                throw new IllegalArgumentException("Type d'utilisateur non reconnu.");
        }
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
    }

    @Override
    public Utilisateur userInfo(Long id) {
        Utilisateur userInfo = utilisateurRepository.findByIdUtilisateur(id);
        return userInfo;
    }



}
