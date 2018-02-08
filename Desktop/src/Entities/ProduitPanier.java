/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;

/**
 *
 * @author oussamahamidi
 */
public class ProduitPanier extends Produit
{
    private float quantiteVendue;
    private float poidsVendu;
    private float prixVente;

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

    public ProduitPanier(float quantiteVendue, float poidsVendu, float prixVente, int idProduit, String reference, String libelle, String description, float prix, String taille, String couleur, String texture, float poids) {
        super(idProduit, reference, libelle, description, prix, taille, couleur, texture, poids);
        this.quantiteVendue = quantiteVendue;
        this.poidsVendu = poidsVendu;
        this.prixVente = prixVente;
    }

    
    
}
