package com.example.servigo.Controllers;

import com.example.servigo.Entites.Localisation;
import com.example.servigo.Entites.Prestateur;
import com.example.servigo.Services.Location.LocationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/localisation")
public class LocationConrtoller {
    @Autowired
    LocationServiceInterface localisationService;

    @PostMapping("/update")
    public ResponseEntity<?> updateLocalisation(@RequestBody Localisation localisation) {
        localisationService.saveOrUpdateLocation(localisation.getUtilisateur().getIdUtilisateur(), localisation.getLatitude(),localisation.getLongitude());
        return ResponseEntity.ok("Localisation mise à jour avec succès");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getLocationByUser(@PathVariable Long id) {
        return localisationService.getLocationByUserId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/all")
    public List<Localisation> getAllLocalisations() {
        return localisationService.getAllLocations();

    }
//    @GetMapping("/prestataires-proches/{clientId}")
//    public ResponseEntity<List<Localisation>> getPrestatairesProches(
//            @PathVariable Long clientId,
//            @RequestParam(defaultValue = "5") double rayonKm) { // rayon en km
//        return ResponseEntity.ok(localisationService.getPrestatairesProches(clientId, rayonKm));
//    }
@GetMapping("/prestataires-proches")
public ResponseEntity<List<Localisation>> getPrestatairesProchesParRole(
        @RequestParam Long clientId,
        @RequestParam String role,
        @RequestParam(defaultValue = "5") double rayonKm) {

    List<Localisation> prestatairesProches = localisationService.getPrestatairesProches(clientId, rayonKm);

    // Filtrer par rôle
    List<Localisation> filtrerParRole = prestatairesProches.stream()
            .filter(loc -> loc.getUtilisateur() instanceof Prestateur)
            .filter(loc -> ((Prestateur) loc.getUtilisateur()).getRoles().equalsIgnoreCase(role))
            .toList();

    return ResponseEntity.ok(filtrerParRole);
}
    /*
     * what i will do now is when a client click on the button which makes him see more about the prestataire
     * then le prestateur gonna reseave a notification let them to aceed to the application so he is connected
     * is he is in for exeemple
     *
     *
     */

}
