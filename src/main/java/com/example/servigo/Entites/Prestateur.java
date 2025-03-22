package com.example.servigo.Entites;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Prestateur extends Utilisateur {
    private double solde;

    @OneToMany(mappedBy = "serveur")
    private List<Service> services;
}