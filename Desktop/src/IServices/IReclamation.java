/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Reclamation;
import Utils.Enumerations;

/**
 *
 * @author benab
 */
public interface IReclamation {
    
    public boolean ajouterReclamation(String userId, String produitOrBoutiqueId, String description, Enumerations.TypeReclamation type);
    
    public boolean supprimerReclamation(String reclamationId);
    
}
