//package com.example.servigo.Mapper;
//
//
//import com.example.servigo.DTOs.*;
//import com.example.servigo.Entites.*;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DtoMapper {
//
//    public UtilisateurDTO toUtilisateurDTO(Utilisateur utilisateur) {
//        if (utilisateur instanceof Client) {
//            return toClientDTO((Client) utilisateur);
//        } else if (utilisateur instanceof Prestateur) {
//            return toServeurDTO((Prestateur) utilisateur);
//        }
//        return null;
//    }
//
//    public ClientDTO toClientDTO(Client client) {
//        ClientDTO dto = new ClientDTO();
//        dto.setId(client.getId());
//        dto.setNom(client.getNom());
//        dto.setEmail(client.getEmail());
//        return dto;
//    }
//
//    public PrestateurDTO toServeurDTO(Prestateur serveur) {
//        PrestateurDTO dto = new PrestateurDTO();
//        dto.setId(serveur.getId());
//        dto.setNom(serveur.getNom());
//        dto.setEmail(serveur.getEmail());
//        dto.setSolde(serveur.getSolde());
//        return dto;
//    }
//
//    public ServiceDTO toServiceDTO(Service service) {
//        ServiceDTO dto = new ServiceDTO();
//        dto.setId(service.getId());
//        dto.setNom(service.getNom());
//        dto.setDescription(service.getDescription());
//        dto.setServeurId(service.getPrestateur().getId());
//        return dto;
//    }
//
//    public MessagerieDTO toMessagerieDTO(Messagerie messagerie) {
//        MessagerieDTO dto = new MessagerieDTO();
//        dto.setId(messagerie.getId());
//        dto.setClientId(messagerie.getClient().getId());
//        dto.setServeurId(messagerie.getPrestateur().getId());
//        dto.setMessage(messagerie.getMessage());
//        dto.setDateEnvoi(messagerie.getDateEnvoi());
//        return dto;
//    }
//
//    public NotificationDTO toNotificationDTO(Notification notification) {
//        NotificationDTO dto = new NotificationDTO();
//        dto.setId(notification.getId());
//        dto.setUtilisateurId(notification.getUtilisateur().getId());
//        dto.setMessage(notification.getMessage());
//        dto.setDateEnvoi(notification.getDateEnvoi());
//        return dto;
//    }
//
//    public AvisDTO toAvisDTO(Avis avis) {
//        AvisDTO dto = new AvisDTO();
//        dto.setId(avis.getId());
//        dto.setClientId(avis.getClient().getId());
//        dto.setServeurId(avis.getPrestateur().getId());
//        dto.setNote(avis.getNote());
//        dto.setCommentaire(avis.getCommentaire());
//        dto.setDateAvis(avis.getDateAvis());
//        return dto;
//    }
//}