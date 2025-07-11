package com.example.servigo.Services.Service;

import com.example.servigo.Entites.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ServicesServiceInterface {
    public void addService(String serviceJson, MultipartFile file) throws IOException;
    List<Service> getAllServices();
}
