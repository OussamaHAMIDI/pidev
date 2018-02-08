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
    private float prix;
    private String taille;
    private String couleur;
    private String texture;
    private float poids;
    

    public Produit() {
    }

    public Produit(int id, String reference, String libelle, String description, float prix, String taille, String couleur, String texture, float poids) {
        this.id = id;
        this.reference = reference;
        this.libelle = libelle;
        this.description = description;
        this.prix = prix;
        this.taille = taille;
        this.couleur = couleur;
        this.texture = texture;
        this.poids = poids;
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

    
    
    @Override
    public String toString() {
        return "Les informations du produit sont :" + " l'id est " + id + ", la reference est " + reference + ", le libelle produit est " + libelle + ", la description est " + description;
    }

}
