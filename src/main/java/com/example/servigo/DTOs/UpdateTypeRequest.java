package com.example.servigo.DTOs;

import com.example.servigo.Enums.TypeUtilisateur;
import lombok.Data;

@Data
public class UpdateTypeRequest {
    private Long idUtilisateur;
    private TypeUtilisateur typeUtilisateur;
}
