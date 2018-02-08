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

    public void ajouterProduit(int idProduit, int idBoutique);

    public void supprimerBoutique(int idBoutique);

    public Boutique chercherBoutiqueParNom(String nom);

    public Boutique chercherBoutiqueParID(int idBoutique);

    public List<Produit> lireProduitsParBoutique(int idBoutique);

    public List<Produit> lireProduitsParBoutique(String nomBoutique);

    public List<Boutique> lireBoutiques();
    
     public int getNextId();

//     public Boutique chercherBoutiqueParProduit (Produit produit);
//     public void modifierBoutique(Boutique boutique);
}
