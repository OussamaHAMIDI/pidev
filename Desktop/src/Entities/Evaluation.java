/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import Utils.Enumerations;
import java.time.LocalDateTime;

/**
 *
 * @author benab
 */
public class Evaluation {
    
    private String id;
    private String userId;
    private String produitOrBoutiqueId;
    private String description;
    private LocalDateTime dateCreation;
    private Enumerations.TypeReclamation type;

    public Evaluation(String id, String userId, String produitOrBoutiqueId, String description, LocalDateTime dateCreation, Enumerations.TypeReclamation type) {
        this.id = id;
        this.userId = userId;
        this.produitOrBoutiqueId = produitOrBoutiqueId;
        this.description = description;
        this.dateCreation = dateCreation;
        this.type = type;
    }

    public Evaluation() {
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getProduitOrBoutiqueId() {
        return produitOrBoutiqueId;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public Enumerations.TypeReclamation getType() {
        return type;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setProduitOrBoutiqueId(String produitOrBoutiqueId) {
        this.produitOrBoutiqueId = produitOrBoutiqueId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setType(Enumerations.TypeReclamation type) {
        this.type = type;
    }
    
    
    
}
