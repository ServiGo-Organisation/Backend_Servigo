package com.example.servigo.Services.Location;

import com.example.servigo.Entites.Localisation;

import java.util.List;
import java.util.Optional;

public interface LocationServiceInterface {
    void saveOrUpdateLocation(Long userId, double latitude, double longitude);
    List<Localisation> getAllLocations();
    Optional<Localisation> getLocationByUserId(Long userId);
}
