package com.example.servigo.DTOs;

import lombok.Data;

@Data
public class ChooseServiceRequestDTO {
    private Long clientId;
    private Long serviceId;
}
