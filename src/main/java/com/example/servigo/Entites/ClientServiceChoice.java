package com.example.servigo.Entites;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "client_service_choice")
public class ClientServiceChoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "service_id", nullable = false)
    private Service service;

    private LocalDateTime choiceDate;

    @PrePersist
    public void prePersist() {
        this.choiceDate = LocalDateTime.now();
    }
}