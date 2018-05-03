/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.entities;


/**
 *
 * @author monta
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

    public ProduitPanier(boolean livree ,float quantiteVendue, float poidsVendu, float prixVente, int id, String reference, String libelle, String description, int prix, String taille, String couleur, String texture, int poids, int boutique, String date_creation, String path,int quantite) {
        super( id,  boutique,  reference,  libelle,  description,  prix,  taille,  couleur,  texture,  poids,  date_creation,  quantite,  path);
        this.quantiteVendue = quantiteVendue;
        this.poidsVendu = poidsVendu;
        this.prixVente = prixVente;
        this.livree = livree;
    }
    
    public ProduitPanier(Produit p)
    {
       this.id=p.id;
       this.boutique=p.boutique;
       this.couleur = p.couleur;
       this.description = p.description;
       this.date_creation = p.date_creation;
       this.libelle = p.libelle;
       this.reference = p.reference;
       this.prix = p.prix;
       this.taille = p.taille;
       this.texture =p.texture;
       this.prixVente=p.prix;
       this.quantiteVendue =1;
       this.poidsVendu=0;
       this.path = p.path;
    }
    

    
    

    
    
}
