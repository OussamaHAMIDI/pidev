/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Produit;
import Entities.*;
import java.util.List;

/**
 *
 * @author monta
 */
public interface IPanier {
    public int ajouterProduitPanier(Produit prod,Panier pan);
    public int supprimerProduitPanier(Produit prod,Panier pan);
    public List<Panier> rechercherPaniersUtilisateur(String userId);
    public List<Panier> rechercherPaniersUtilisateur(String userId,String status);
    public Panier rechercherPanierId(String id);
    public int ajouterPanier(Panier pan);
    public int miseAJourPanier(Panier pan);
    public int supprimerPanier(Panier pan);
    
    
}
