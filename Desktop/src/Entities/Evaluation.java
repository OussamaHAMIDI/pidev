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
public class Evaluation {
    
    private int id;
    private User user;
    private Produit produit;
    private Boutique boutique;
    private LocalDateTime dateCreation;
    private TypeReclamation type;
    private int note; //de 1 à 10

    public Evaluation(int id, User user, Produit produit, LocalDateTime dateCreation, TypeReclamation type, int note) {
        this.id = id;
        this.user = user;
        this.produit = produit;
        this.boutique = null;
        this.dateCreation = dateCreation;
        this.type = type;
        this.note = note;
    }
    
    public Evaluation(int id, User user, Boutique boutique, LocalDateTime dateCreation, TypeReclamation type, int note) {
        this.id = id;
        this.user = user;
        this.boutique = boutique;
        this.produit = null;
        this.dateCreation = dateCreation;
        this.type = type;
        this.note = note;
    }

    public Evaluation() {
    }

    public Evaluation(User user, Boutique boutique, LocalDateTime dateCreation, TypeReclamation type, int note) {
        this.user = user;
        this.produit = null;
        this.boutique = boutique;
        this.dateCreation = dateCreation;
        this.type = type;
        this.note = note;
    }

    public Evaluation(User user, Produit produit, LocalDateTime dateCreation, TypeReclamation type, int note) {
        this.user = user;
        this.produit = produit;
        this.dateCreation = dateCreation;
        this.type = type;
        this.note = note;
    }

    public Evaluation(User user, Boutique boutique, TypeReclamation type, int note) {
        this.user = user;
        this.boutique = boutique;
        this.type = type;
        this.note = note;
    }

    public Evaluation(User user, Produit produit, TypeReclamation type, int note) {
        this.user = user;
        this.produit = produit;
        this.type = type;
        this.note = note;
    }

    public Evaluation(User user, Produit produit, int note) {
        this.user = user;
        this.type = TypeReclamation.Produit;
        this.produit = produit;
        this.boutique = null;
        this.note = note;
    }

    public Evaluation(User user, Boutique boutique, int note) {
        this.user = user;
        this.type = TypeReclamation.Boutique;
        this.boutique = boutique;
        this.produit = null;
        this.note = note;
    }

    
    
    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Produit getProduit() {
        return produit;
    }
    
    public Boutique getBoutique(){
        return boutique;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public TypeReclamation getType() {
        return type;
    }

    public int getNote() {
        return note;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }
    
    public void setBoutique(Boutique boutique){
        this.boutique = boutique;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setType(TypeReclamation type) {
        this.type = type;
    }

    public void setNote(int note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "Evaluation{" + "id=" + id + ", user=" + user + ", produit=" + produit + ", boutique=" + boutique + ", dateCreation=" + dateCreation + ", type=" + type + ", note=" + note + "} \n";
    }

    
    
}
