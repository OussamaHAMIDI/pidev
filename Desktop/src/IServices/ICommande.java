/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Commande;
import Entities.User;
import java.util.List;

/**
 *
 * @author benab
 */
public interface ICommande {
    
    public void ajouterCommande(Commande c);

    public void supprimerCommande(Commande c);
    
    public void modifierCommande(Commande c);

    public Commande chercherCommandeParID(String id);
    
    public List<Commande> getCommandes(User user);
}
