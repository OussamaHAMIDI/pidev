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
    private int quantiteVendue;
    private float poidsVendu;
    private float prixVente;

    public int getQuantiteVendue() {
        return quantiteVendue;
    }

    public void setQuantiteVendue(int quantiteVendue) {
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

    public ProduitPanier(int quantiteVendue, float poidsVendu, float prixVente, int id, String reference, String libelle, String description, float prix, String taille, String couleur, String texture, float poids) {
        super(id, reference, libelle, description, prix, taille, couleur, texture, poids);
        this.quantiteVendue = quantiteVendue;
        this.poidsVendu = poidsVendu;
        this.prixVente = prixVente;
    }

    
    
}
