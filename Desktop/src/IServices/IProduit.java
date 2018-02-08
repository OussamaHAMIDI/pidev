/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Produit;
import java.util.List;
import javafx.scene.control.ListCellBuilder;

/**
 *
 * @author Hamdi
 */
public interface IProduit {

    public boolean ajouterProduit(Produit p);

    public boolean supprimerProduit(Produit p);
    
    public boolean modifierProduit(Produit p);

    public Produit chercherProduitParID(String ID);

    public List<Produit> lireProduits();
    
    public String  getNextId();
    
}
