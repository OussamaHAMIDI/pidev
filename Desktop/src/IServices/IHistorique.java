/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.*;


/**
 *
 * @author benab
 */
public interface IHistorique {
    
    public int ajouterCommandeHistorique(Commande commande);
    public int supprimerCommandeHistorique(Commande commande);
    /**public int ajouterPanierHistorique(Panier panier, Historique historique);
    public int supprimerPanierHistorique(Panier panier, Historique historique);**/
    public Historique rechercherUserHistorique(String userId);
    public Historique rechercherHistorique(String id);
    
}
