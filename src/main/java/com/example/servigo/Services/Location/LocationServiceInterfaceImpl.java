package com.example.servigo.Services.Location;

import com.example.servigo.Entites.Localisation;
import com.example.servigo.Entites.Utilisateur;
import com.example.servigo.Enums.TypeUtilisateur;
import com.example.servigo.Repositories.LocalisationRepository;
import com.example.servigo.Repositories.PrestateurRepository;
import com.example.servigo.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<Localisation> getPrestatairesProches(Long clientId, double rayonKm) {
        Localisation clientLoc = localisationRepository.findByUtilisateurIdUtilisateur(clientId)
                .orElseThrow(() -> new RuntimeException("Localisation du client introuvable"));

        double clientLat = clientLoc.getLatitude();
        double clientLon = clientLoc.getLongitude();

        // Récupérer tous les prestataires avec localisation
        List<Localisation> all = localisationRepository.findAll().stream()
                .filter(loc -> loc.getUtilisateur().getTypeUtilisateur() == TypeUtilisateur.PRESTATAIRE)
                .collect(Collectors.toList());

        // Calculer la distance entre le client et chaque prestataire
        return all.stream()
                .filter(loc -> distanceKm(clientLat, clientLon, loc.getLatitude(), loc.getLongitude()) <= rayonKm)
                .sorted((a, b) -> Double.compare(
                        distanceKm(clientLat, clientLon, a.getLatitude(), a.getLongitude()),
                        distanceKm(clientLat, clientLon, b.getLatitude(), b.getLongitude())
                ))
                .collect(Collectors.toList());
    }
// hadi smitha formule de Haversine
    private double distanceKm(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // rayon Terre en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
