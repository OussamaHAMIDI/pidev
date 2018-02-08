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
    
    private int id;
    private int userId;
    private int produitOrBoutiqueId;
    private LocalDateTime dateCreation;
    private Enumerations.TypeReclamation type;
    private int note; //de 1 à 10

    public Evaluation(int id, int userId, int produitOrBoutiqueId, String description, LocalDateTime dateCreation, Enumerations.TypeReclamation type, int note) {
        this.id = id;
        this.userId = userId;
        this.produitOrBoutiqueId = produitOrBoutiqueId;
        this.dateCreation = dateCreation;
        this.type = type;
        this.note = note;
    }

    public Evaluation() {
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getProduitOrBoutiqueId() {
        return produitOrBoutiqueId;
    }

    

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public Enumerations.TypeReclamation getType() {
        return type;
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


    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setType(Enumerations.TypeReclamation type) {
        this.type = type;
    }
    
    
    
}
