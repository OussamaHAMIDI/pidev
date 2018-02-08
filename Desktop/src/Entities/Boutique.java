/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Azza
 */
public class Boutique {
    
    private int id;
    private String nom;
    private List<Produit> listProduit;
    private LocalDateTime dateCreation;
    

    public Boutique() {
    }
    
    public Boutique(int id, String nom, List<Produit> listProduit, LocalDateTime dateCreation) {
        this.id = id;
        this.nom = nom;
        this.listProduit = listProduit;
        this.dateCreation = dateCreation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Produit> getListProduit() {
        return listProduit;
    }

    public void setListProduit(List<Produit> listProduit) {
        this.listProduit = listProduit;
    }

    public LocalDateTime getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDateTime dateCreation) {
        this.dateCreation = dateCreation;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Boutique other = (Boutique) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Les informations de la Boutique sont :" + " l'id est " + id + ", le nom est " + nom + ", le produit est " + listProduit + ", la date de Creation est " + dateCreation;
    }
    

    
    
    
}
