
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
    private User user;
    private Boutique boutique;
    private Produit produit;
    private String description;
    private LocalDateTime dateCreation;
    private TypeReclamation type;

    public Reclamation() {
    }
    
    public Reclamation(int id, User user, Boutique boutique, String description, LocalDateTime dateCreation, TypeReclamation type) {
        this.id = id;
        this.user = user;
        this.boutique = boutique;
        this.produit = null;
        this.description = description;
        this.dateCreation = dateCreation;
        this.type = type;
    }

    public Reclamation(int id, User user, Produit produit, String description, LocalDateTime dateCreation, TypeReclamation type) {
        this.id = id;
        this.user = user;
        this.produit = produit;
        this.boutique = null;
        this.description = description;
        this.dateCreation = dateCreation;
        this.type = type;
    }

    public Reclamation(User user, Boutique boutique, String description, LocalDateTime dateCreation, TypeReclamation type) {
        this.user = user;
        this.boutique = boutique;
        this.produit = null;
        this.description = description;
        this.dateCreation = dateCreation;
        this.type = type;
    }

    public Reclamation(User user, Boutique boutique, String description, TypeReclamation type) {
        this.user = user;
        this.boutique = boutique;
        this.produit = null;
        this.description = description;
        this.type = type;
    }

    public Reclamation(User user, Produit produit, String description, LocalDateTime dateCreation, TypeReclamation type) {
        this.user = user;
        this.produit = produit;
        this.boutique = null;
        this.description = description;
        this.dateCreation = dateCreation;
        this.type = type;
    }

    public Reclamation(User user, Produit produit, String description, TypeReclamation type) {
        this.user = user;
        this.produit = produit;
        this.boutique = null;
        this.description = description;
        this.type = type;
    }

    public Reclamation(User user, Boutique boutique, String description) {
        this.user = user;
        this.boutique = boutique;
        this.type = TypeReclamation.Boutique;
        this.produit = null;
        this.description = description;
    }

    public Reclamation(User user, Produit produit, String description) {
        this.user = user;
        this.type = TypeReclamation.Produit;
        this.boutique = null;
        this.produit = produit;
        this.description = description;
    }
    
    

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Boutique getBoutique() {
        return boutique;
    }

    public Produit getProduit() {
        return produit;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public TypeReclamation getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setType(TypeReclamation type) {
        this.type = type;
    }
    
}
