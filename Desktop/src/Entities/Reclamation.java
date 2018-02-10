
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Utils.Enumerations.TypeReclamation;
import java.time.LocalDateTime;

/**
 *
 * @author benab
 */
public class Reclamation {
    
    private int id;
    private int userId;
    private int produitOrBoutiqueId;
    private String description;
    private LocalDateTime dateCreation;
    private TypeReclamation type;

    
    /**
     * 
     * @param id
     * @param userId
     * @param produitOrBoutiqueId 
     * @param description
     * @param dateCreation 
     */
    
    public Reclamation(int id, int userId, int produitOrBoutiqueId, String description, LocalDateTime dateCreation, TypeReclamation type) {
        this.id = id;
        this.produitOrBoutiqueId = produitOrBoutiqueId;
        this.description = description;
        this.dateCreation = dateCreation;
        this.userId = userId;
        this.type=type;
    }
    
    public Reclamation() {
    }

    public int getId() {
        return id;
    }

    public void setType(TypeReclamation type) {
        this.type = type;
    }

    public TypeReclamation getType() {
        return type;
    }

    public int getUserId() {
        return userId;
    }

    public int getProduitOrBoutiqueId() {
        return produitOrBoutiqueId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setProduitOrBoutiqueId(int produitOrBoutiqueId) {
        this.produitOrBoutiqueId = produitOrBoutiqueId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }
    

}
