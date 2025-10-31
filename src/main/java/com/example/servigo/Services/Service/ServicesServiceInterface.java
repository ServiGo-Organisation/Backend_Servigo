package com.example.servigo.Services.Service;

import com.example.servigo.DTOs.ServiceDTO;
import com.example.servigo.Entites.Prestateur;
import com.example.servigo.Entites.Service;
import com.example.servigo.Entites.SousService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface ServicesServiceInterface {
//    public void addService(String serviceJson, MultipartFile file) throws IOException;
   // Service addService(String serviceJson, MultipartFile file) throws IOException;

    List<Prestateur> getPrestateursByServiceName(String serviceName);

    Service createService(Service service);
    List<Service> getAllServices();
        Optional<Service> getServiceByName(String nomService);
    Prestateur addServiceToPrestateur(Long prestateurId, Long serviceId);
    List<Prestateur> getPrestatairesByService(String nomService);
    List<SousService> getSousServicesByService(Long serviceId);
    Service calculerMontantTotalService(Long serviceId, List<Long> sousServiceIds);
}
