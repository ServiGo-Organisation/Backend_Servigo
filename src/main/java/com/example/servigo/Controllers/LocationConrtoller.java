package com.example.servigo.Controllers;

import com.example.servigo.Entites.Localisation;
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
}
