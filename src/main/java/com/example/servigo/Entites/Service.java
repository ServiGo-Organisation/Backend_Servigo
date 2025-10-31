package com.example.servigo.Entites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nom;
    private String description;
    private String serviceImage;

    @ManyToMany(mappedBy = "services")
    @JsonIgnore
    private Set<Prestateur> prestateurs = new HashSet<>();

    @Column(nullable = false, columnDefinition = "double default 0.0")
    private Double budgetTotal = 0.0;

    // Relation avec les sous-services
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SousService> sousServices = new HashSet<>();
}
