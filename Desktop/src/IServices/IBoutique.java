/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Boutique;
import Entities.Produit;
import java.util.List;

/**
 *
 * @author Azza
 */
public interface IBoutique {
    
    
    public void ajouterBoutique(Boutique boutique);
    public void supprimerBoutique(Boutique boutique);   
    public void modifierBoutique(Boutique boutique);
    public void ajouterProduit(Produit produit,Boutique boutique);
    public Boutique chercherBoutiqueParNom (String nom);
    public Boutique chercherBoutiqueParID (int Id);
    public Boutique chercherBoutiqueParProduit (Produit produit);
}
