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
 * @author oussamahamidi
 */
public interface IProduit {

    public boolean ajouterProduit(Produit p);

    public boolean supprimerProduit(int id);
    
    public boolean modifierProduit(Produit p);

    public Produit chercherProduitParID(int id);

    public List<Produit> listerProduitsBoutique(int idB);
    
    public String  getNextId();
    
}
