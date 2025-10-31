package com.example.servigo.Entites;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class Messagerie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Prestateur prestateur;

    private String message;
    private LocalDateTime dateEnvoi;
}