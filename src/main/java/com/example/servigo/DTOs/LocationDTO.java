package com.example.servigo.DTOs;

import lombok.Data;

public class LocationDTO {
    @Data
    class LocalisationRequest {
        private Long userId;
        private double latitude;
        private double longitude;
    }
}
