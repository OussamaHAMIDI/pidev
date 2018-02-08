/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

<<<<<<< HEAD
import Entities.Produit;
import Entities.*;
=======

import Entities.Produit;
import entities.*;

import Entities.*;

>>>>>>> 6deb11795fc702b49fc733d6fb1a218c6b0df91c
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
