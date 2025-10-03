package com.example.servigo.Entites;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Prestateur extends Utilisateur {
    private double solde;

    @ManyToMany
    @JoinTable(
            name = "prestateur_service",
            joinColumns = @JoinColumn(name = "prestateur_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private Set<Service> services = new HashSet<>();
}
