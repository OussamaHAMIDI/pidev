package IServices;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import Entities.Panier;
import Entities.Produit;
import Entities.ProduitPanier;
import java.util.List;

/**
 *
 * @author monta
 */
public interface IPanier {
    
    public int ajouterProduitPanier(ProduitPanier produit,int idPanier);
    public int modifierProduitPanier(ProduitPanier produit,int idPanier);
    public int supprimerProduitPanier(int produitId,int idPanier);
    public List<Panier> rechercherPaniersUtilisateur(int userId);
    public List<ProduitPanier> rechercherProduitsPanier(int panierId);
    public List<Panier> rechercherPaniersUtilisateur(int userId,String status);
    public Panier rechercherPanierById(int id);
    public int ajouterPanier(Panier produit);
    public int miseAJourPanier(Panier produit);
    public int supprimerPanier(Panier produit);
    public String rechercherArtisantProduitPanier(int idproduit);
    public List<ProduitPanier> rechercherCommandeArtisant(int userId);
    public String rechercheClientPanier(int idpanier);
    public void envoyerMailArtisant(Panier panier);
    public void envoyerMailClient(Panier panier);
 
}
