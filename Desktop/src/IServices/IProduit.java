/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IServices;

import Entities.Boutique;
import Entities.Produit;
import java.io.InputStream;
import java.util.List;
import javafx.scene.image.Image;

/**
 *
 * @author oussamahamidi
 */
public interface IProduit {

    public boolean ajouterProduit(Produit p);

    public boolean supprimerProduit(int id);

    public boolean modifierProduit(Produit p);

    public Produit chercherProduitParID(int id);

    public Produit chercherProduitParID(Boutique boutique, int id);

    public List<Produit> listerProduitsBoutique(int idB);

    public List<Produit> listerProduits();

    public Image getPhoto(int idP);

    public InputStream getPhotoProduit(int idP);

    public String getNextId();

}
