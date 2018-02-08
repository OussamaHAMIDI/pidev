package IServices;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Entities.Panier;
import Entities.Produit;
import java.util.List;

/**
 *
 * @author monta
 */
public interface IPanier {
    
    public int ajouterProduitPanier(Produit produit,Panier panier);
    public int ajouterProduitPanier(Produit produit,String idPanier);
    public int supprimerProduitPanier(Produit produit,Panier panier);
    public int modifierProduitPanier(Produit produit,String idPanier);
    public int supprimerProduitPanier(Produit produit,String idPanier);
    public List<Panier> rechercherPaniersUtilisateur(String userId);
    public List<Panier> rechercherPaniersUtilisateur(String userId,String status);
    public Panier rechercherPanierById(String id);
    public int ajouterPanier(Panier produit);
    public int miseAJourPanier(Panier produit);
    public int supprimerPanier(Panier produit);
    
    
}
