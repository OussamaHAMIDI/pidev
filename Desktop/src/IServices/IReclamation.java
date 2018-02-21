
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Reclamation;
import Entities.Boutique;
import Entities.Produit;
import Entities.User;
import Utils.Enumerations;
import java.util.List;

/**
 *
 * @author benab
 */
public interface IReclamation {
    
    public boolean ajouterReclamation(Reclamation reclamation);
    
    public boolean supprimerReclamation(int reclamationId);
    
    public boolean supprimerReclamation(Reclamation reclamation);
    
    public Reclamation getReclamationById(int reclamationId);
    
    public List<Reclamation> rechercherReclamationBoutique(Boutique boutique);
    
    public List<Reclamation> rechercherReclamationProduit(Produit produit);
    
    public List<Reclamation> rechercherReclamationUser (User user);
    
    public List<Reclamation> rechercherReclamationUserBoutique(User user, Boutique boutique);
    
    public List<Reclamation> rechercherReclamationUserProduit(User user, Produit produit);
    
    public List<Reclamation> getAllReclamations();
    
    
}
