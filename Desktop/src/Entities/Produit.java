/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;


/**
 *
 * @author Hamdi
 */
public class Produit {

    private int id;
    private String reference;
    private String libelle;
    private String description;

    public Produit() {
    }

    public Produit(int id, String reference, String libelle, String description) {
        this.id = id;
        this.reference=reference;
        this.libelle = libelle;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    
    @Override
    public String toString() {
        return "Les informations du produit sont :" + " l'id est " + id + ", la reference est " + reference + ", le libelle produit est " + libelle + ", la description est " + description;
    }

}
