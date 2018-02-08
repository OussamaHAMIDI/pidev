/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

<<<<<<< HEAD
import Entities.Produit;
import entities.*;
=======
import Entities.*;
>>>>>>> b2db77aec68ea55f6430c7c8a0244d7c393c69f0
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
