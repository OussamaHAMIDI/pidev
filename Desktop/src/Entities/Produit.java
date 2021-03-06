
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;


import java.io.InputStream;

import java.time.LocalDateTime;


/**
 *
 * @author oussamahamidi
 */
public class Produit {

    protected int id;
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

    protected Boutique boutique;
    
    private String photo;

    public Produit(int id) {
        this.id = id;
    }


    


    public Produit() {
    }

    public Produit(int id, String reference, String libelle, String description, float prix, String taille, String couleur, String texture, float poids, Boutique boutique, LocalDateTime dateAjout, String photo) {
        this.id = id;
        this.reference = reference;
        this.libelle = libelle;
        this.description = description;
        this.prix = prix;
        this.taille = taille;
        this.couleur = couleur;
        this.texture = texture;
        this.poids = poids;
        this.boutique = boutique;
        this.dateAjout = dateAjout;
        this.photo = photo;
    }

    public Produit(String reference, String libelle, String description, float prix, String taille, String couleur, String texture, float poids, Boutique boutique, LocalDateTime dateAjout, String photo) {
        this.reference = reference;
        this.libelle = libelle;
        this.description = description;
        this.prix = prix;
        this.taille = taille;
        this.couleur = couleur;
        this.texture = texture;
        this.poids = poids;
        this.boutique = boutique;
        this.dateAjout = dateAjout;
        this.photo = photo;
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

    public Boutique getBoutique() {
        return boutique;
    }

    public void setBoutique(Boutique boutique) {
        this.boutique = boutique;
    }

    public LocalDateTime getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDateTime dateAjout) {
        this.dateAjout = dateAjout;
    }
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
    
    
    @Override
    public String toString() {
        return "Les informations du produit sont :" + " l'id est " + id + ", la reference est " + reference + ", le libelle produit est " + libelle + ", la description est " + description;
    }
}

