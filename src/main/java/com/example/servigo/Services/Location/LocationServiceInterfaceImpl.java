package com.example.servigo.Services.Location;

import com.example.servigo.Entites.Localisation;
import com.example.servigo.Entites.Utilisateur;
import com.example.servigo.Repositories.LocalisationRepository;
import com.example.servigo.Repositories.PrestateurRepository;
import com.example.servigo.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceInterfaceImpl implements LocationServiceInterface {
    @Autowired
     LocalisationRepository localisationRepository;

    @Autowired
     UtilisateurRepository utilisateurRepository;

    @Autowired
    PrestateurRepository prestateurRepository;


    @Override
    public void saveOrUpdateLocation(Long userId, double latitude, double longitude) {
        Utilisateur utilisateur = utilisateurRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Optional<Localisation> existingLocation = localisationRepository.findByUtilisateur(utilisateur);

        Localisation localisation = existingLocation.orElse(
                Localisation.builder().utilisateur(utilisateur).build()
        );

        localisation.setLatitude(latitude);
        localisation.setLongitude(longitude);
        localisation.setUpdatedAt(LocalDateTime.now());

        localisationRepository.save(localisation);
    }
    @Override
    public List<Localisation> getAllLocations() {
        return localisationRepository.findAllPrestateurs();
    }

    @Override
    public Optional<Localisation> getLocationByUserId(Long userId) {
        return utilisateurRepository.findById(userId)
                .flatMap(localisationRepository::findByUtilisateur);
    }
}
