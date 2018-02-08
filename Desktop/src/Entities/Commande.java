/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;

/**
 *
 * @author benab
 */
public class Commande {
    
    private String id;
    private User user;
    private Panier panier;
    private String statut;
    private Date dateLivraison;
    private Date dateCreation;
    private Date datePaiement;
    

    public Commande(String id, String statut, Date dateLivraison, Date dateCreation, Date datePaiement, Panier panier) {
        this.id = id;
        this.statut = statut;
        this.dateLivraison = dateLivraison;
        this.dateCreation = dateCreation;
        this.datePaiement = datePaiement;
        this.panier = panier;
    }

    public Panier getPanier() {
        return this.panier;
    }

    public void setPanier(Panier panier) {
        this.panier = panier;
    }

    public Commande() {
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getId() {
        return id;
    }

    public String getStatut() {
        return statut;
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", statut=" + statut + ", dateLivraison=" + dateLivraison + ", dateCreation=" + dateCreation + ", datePaiement=" + datePaiement + '}';
    }
    
}
