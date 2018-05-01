/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entities;

/**
 *
 * @author Worm-root
 */
public class Produit {

    protected int id ;
    protected int boutique;
    protected String reference;
    protected String libelle;
    protected String description;
    protected int prix;
    protected String taille;
    protected String couleur;
    protected String texture;
    protected int poids;
    protected String date_creation;
    protected int quantite;
    protected String path;

    public Produit() {
    }

    public Produit(int id, int boutique, String reference, String libelle, String description, int prix, String taille, String couleur, String texture, int poids, String date_creation, int quantite, String path) {
        this.id = id;
        this.boutique = boutique;
        this.reference = reference;
        this.libelle = libelle;
        this.description = description;
        this.prix = prix;
        this.taille = taille;
        this.couleur = couleur;
        this.texture = texture;
        this.poids = poids;
        this.date_creation = date_creation;
        this.quantite = quantite;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBoutique() {
        return boutique;
    }

    public void setBoutique(int boutique) {
        this.boutique = boutique;
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

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
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

    public int getPoids() {
        return poids;
    }

    public void setPoids(int poids) {
        this.poids = poids;
    }

    public String getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(String date_creation) {
        this.date_creation = date_creation;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    
    
    
    
}
