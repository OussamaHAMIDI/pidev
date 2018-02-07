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

    public void ajouterProduit(Produit p);

    public void supprimerProduit(Produit p);
    
    public void modifierProduit(Produit p);

    public Produit chercherProduitParID(int ID);

    public List<Produit> lireProduits();
    
    public String  getNextId();
    
}
