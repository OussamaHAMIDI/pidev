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
public class ProduitPanier extends Produit
{
    private float quantiteVendue;
    private float poidsVendu;
    private float prixVente;
    private boolean livree;
    
    public float getQuantiteVendue() {
        return quantiteVendue;
    }

    public void setQuantiteVendue(float quantiteVendue) {
        this.quantiteVendue = quantiteVendue;
    }

    public float getPoidsVendu() {
        return poidsVendu;
    }

    public void setPoidsVendu(float poidsVendu) {
        this.poidsVendu = poidsVendu;
    }

    public float getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(float prixVente) {
        this.prixVente = prixVente;
    }

    public ProduitPanier() {
    }

    public boolean isLivree() {
        return livree;
    }

    public void setLivree(boolean livree) {
        this.livree = livree;
    }

    public ProduitPanier(boolean livree ,float quantiteVendue, float poidsVendu, float prixVente, int id, String reference, String libelle, String description, float prix, String taille, String couleur, String texture, float poids, Boutique boutique, LocalDateTime dateAjout, InputStream photo) {
        super(id, reference, libelle, description, prix, taille, couleur, texture, poids, boutique, dateAjout, photo);
        this.quantiteVendue = quantiteVendue;
        this.poidsVendu = poidsVendu;
        this.prixVente = prixVente;
        this.livree = livree;
    }
    
    public ProduitPanier(Produit p)
    {
       this.id=p.id;
       this.boutique=p.boutique;
       this.idBoutique=p.idBoutique;
       this.couleur = p.couleur;
       this.description = p.description;
       this.dateAjout = p.dateAjout;
       this.libelle = p.libelle;
       this.reference = p.reference;
       this.prix = p.prix;
       this.taille = p.taille;
       this.texture =p.texture;
       this.prixVente=p.prix;
       this.quantiteVendue =1;
       this.poidsVendu=0;
    }
    

    
    

    
    
}
