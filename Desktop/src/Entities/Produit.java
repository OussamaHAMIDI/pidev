
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

import java.time.LocalDateTime;


/**
 *
 * @author oussamahamidi
 */
public class Produit {

    protected int idProduit;
    protected String reference;
    protected String libelle;
    protected String description;
    protected float prix;
    protected String taille;
    protected String couleur;
    protected String texture;
    protected float poids;
    protected int idBoutique;
    protected LocalDateTime dateAjout;

    public Produit() {
    }

    public Produit(int idProduit, String reference, String libelle, String description, float prix, String taille, String couleur, String texture, float poids, int idBoutique) {
        this.idProduit = idProduit;
        this.reference = reference;
        this.libelle = libelle;
        this.description = description;
        this.prix = prix;
        this.taille = taille;
        this.couleur = couleur;
        this.texture = texture;
        this.poids = poids;
        this.idBoutique = idBoutique;
    }

    

    

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
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

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getTaille() {
        return taille;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getTexture() {
        return texture;
    }

    public void setTexture(String texture) {
        this.texture = texture;
    }

    public float getPoids() {
        return poids;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }
    public int getIdBoutique() {
        return idBoutique;
    }

    public void setIdBoutique(int idBoutique) {
        this.idBoutique = idBoutique;
    }
    
    
    @Override
    public String toString() {
        return "Les informations du produit sont :" + " l'id est " + idProduit + ", la reference est " + reference + ", le libelle produit est " + libelle + ", la description est " + description;
    }
}

