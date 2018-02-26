/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Produit;
import Entities.ProduitPanier;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author oussamahamidi
 */
public class ProduitController implements Initializable {
    @FXML
    private ImageView photoProduit;
    @FXML
    private Text nomProduit;
    @FXML
    private Text descProduit;
    @FXML
    private Text prix;
    
    Produit produit;
    Produit old;
    static public int index;
    static public List<Produit> contenu;
    
    static public ListProduitsController pc;
    @FXML
    private Text reference;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produit = contenu.get(index);
        old=produit;
        nomProduit.setText(produit.getLibelle());
        reference.setText(produit.getReference());
        descProduit.setText(produit.getDescription());
        prix.setText(Float.toString(produit.getPrix()));
if(produit.getPhoto()!=null)
        photoProduit.setImage(new Image(produit.getPhoto()));
        
        
    }    
    
}
