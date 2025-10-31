    package com.example.servigo.Controllers;
    
    import com.example.servigo.DTOs.ServiceDTO;
    import com.example.servigo.Entites.Prestateur;

    import com.example.servigo.Entites.Service;
    import com.example.servigo.Entites.SousService;
    import com.example.servigo.Services.Service.ServicesServiceInterface;
    import com.example.servigo.Services.Service.ServicesServiceInterfaceImpl;
    import lombok.AllArgsConstructor;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.multipart.MultipartFile;
    
    import java.util.List;
    
    @RestController
    @RequestMapping("/api/v1/services")
    @AllArgsConstructor
    public class ServiceController {

        private final ServicesServiceInterfaceImpl servicesServiceInterfaceImpl;

        // Créer un nouveau rôle - CORRIGÉ
        @PostMapping(value = "/create", consumes = "application/json")
        public ResponseEntity<?> createService(@RequestBody Service service) {
            try {
                // Validation simple
                if (service.getNom() == null || service.getNom().trim().isEmpty()) {
                    return ResponseEntity.badRequest().body("Le nom du service est obligatoire");
                }

                Service savedService = servicesServiceInterfaceImpl.createService(service);
                return ResponseEntity.status(HttpStatus.CREATED).body(savedService);
            } catch (Exception e) {
                e.printStackTrace(); // Pour debug
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Erreur lors de la création du service: " + e.getMessage());
            }
        }

        // API pour récupérer les prestataires par rôle
        @GetMapping("/prestateurs")
        public ResponseEntity<?> getPrestatairesByService(@RequestParam String service) {
            try {
                List<Prestateur> prestateurs = servicesServiceInterfaceImpl.getPrestatairesByService(service);

                if (prestateurs.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Aucun prestataire trouvé pour le service: " + service);
                }

                return ResponseEntity.ok(prestateurs);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Aucun prestataire trouvé pour le service: " + service);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erreur serveur: " + e.getMessage());
            }
        }

        // Ajouter un service à un prestataire
        @PostMapping("/add-to-prestateur")
        public ResponseEntity<?> addServiceToPrestateur(
                @RequestParam Long prestateurId,
                @RequestParam Long serviceId) {
            try {
                Prestateur prestateur = servicesServiceInterfaceImpl.addServiceToPrestateur(prestateurId, serviceId);
                return ResponseEntity.ok(prestateur);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
            }
        }

        // Récupérer tous les services
        @GetMapping("/all")
        public ResponseEntity<List<Service>> getAllServices() {
            try {
                List<Service> services = servicesServiceInterfaceImpl.getAllServices();
                return ResponseEntity.ok(services);
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
        // API 1: Lister tous les sous-services d'un service
        @GetMapping("/{serviceId}/sous-services")
        public ResponseEntity<?> getSousServicesByService(@PathVariable Long serviceId) {
            try {
                List<SousService> sousServices = servicesServiceInterfaceImpl.getSousServicesByService(serviceId);

                if (sousServices.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body("Aucun sous-service trouvé pour le service ID: " + serviceId);
                }

                return ResponseEntity.ok(sousServices);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erreur lors de la récupération des sous-services: " + e.getMessage());
            }
        }

        // API 2: Calculer le montant total pour des sous-services sélectionnés
        @PostMapping("/{serviceId}/calculer-montant")
        public ResponseEntity<?> calculerMontantTotalService(
                @PathVariable Long serviceId,
                @RequestBody List<Long> sousServiceIds) {
            try {
                if (sousServiceIds == null || sousServiceIds.isEmpty()) {
                    return ResponseEntity.badRequest()
                            .body("La liste des sous-services sélectionnés ne peut pas être vide");
                }

                Service service = servicesServiceInterfaceImpl.calculerMontantTotalService(serviceId, sousServiceIds);

                return ResponseEntity.ok(service);
            } catch (RuntimeException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(e.getMessage());
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erreur lors du calcul du montant total: " + e.getMessage());
            }
        }

    }
