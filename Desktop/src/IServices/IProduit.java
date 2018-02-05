/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import entities.Produit;
import java.util.List;
import javafx.scene.control.ListCellBuilder;

/**
 *
 * @author bhk
 */
public interface IProduit {

    public void ajouterProduit(Produit p);

    public void supprimerProduit(Produit p);

    public Produit chercherProduitParNom(String nom);

    public List<Produit> lireProduits();
    
}
