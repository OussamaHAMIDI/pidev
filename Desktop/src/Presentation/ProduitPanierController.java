/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.ProduitPanier;
import Services.ProduitService;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;


/**
 * FXML Controller class
 *
 * @author monta
 */


public class ProduitPanierController implements Initializable {

    ProduitPanier produit;
    ProduitPanier old;
    static public int index;
    static public List<ProduitPanier> contenu;
    
    static public PanierController pc;
    
    @FXML
    private AnchorPane contact;
    @FXML
    private Label reference;
    @FXML
    private Label description;
    @FXML
    private Label nom;
    @FXML
    private Label prix;
    @FXML
    private JFXButton supprimer;
    @FXML
    private JFXButton plus;
    @FXML
    private Label quantite;
    @FXML
    private JFXButton moins;
    @FXML
    private ImageView image;
    @FXML
    private Label prixTotal;
    ProduitService ps = new ProduitService();
    @FXML
    private Circle circle;
    /**
     * Initializes the controller class.
     */
      
    public AnchorPane getThis()
    {
        return contact;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        produit = contenu.get(index);
        old = produit;
        reference.setText(produit.getReference());
        nom.setText(produit.getLibelle());
        description.setText(produit.getDescription());
        prix.setText(((Float)produit.getPrixVente()).toString());
        prixTotal.setText(((Float)produit.getPrixVente()).toString());
        quantite.setText(((Float)produit.getQuantiteVendue()).toString());
       circle.setStroke(Color.SEAGREEN);
        circle.setFill(Color.SNOW);
        circle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        circle.setFill(new ImagePattern(new Image("Images/camera.png")));
        Image img = ps.getPhoto(produit.getId());
        if (img != null) {
            try {
                circle.setFill(new ImagePattern(img));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        
    }

    @FXML
    private void supprimerProduitPanier(MouseEvent event) {
        pc.panier.getContenu().remove(produit);
        contenu.remove(produit);
        pc.modifierTotaux(produit.getPrix()*produit.getQuantiteVendue()*-1);
        GridPane parent = (GridPane)contact.getParent();
        parent.getChildren().remove(contact);
    }

    @FXML
    private void ajouterQuantite(MouseEvent event) throws IOException {
        Float qte = produit.getQuantiteVendue()+1;
        produit.setQuantiteVendue(qte);
        
        Float prixT =qte*Float.parseFloat(prix.getText());
       
        quantite.setText(qte.toString());
        prixTotal.setText(prixT.toString());
       pc.modifierTotaux(produit.getPrix());
       pc.modifierContenu(produit,old);
       old = produit;
    }

    @FXML
    private void diminuerQuantite(MouseEvent event) throws IOException {
       
        if(produit.getQuantiteVendue()>1)
        {
        Float qte = produit.getQuantiteVendue()-1;
        produit.setQuantiteVendue(qte); 
        Float prixT =qte*Float.parseFloat(prix.getText());
        quantite.setText(qte.toString());
        prixTotal.setText(prixT.toString());
        pc.modifierTotaux(produit.getPrix()*-1);
        pc.modifierContenu(produit,old);
        old = produit;
        }
    }

    @FXML
    private void test(MouseEvent event) {
        System.out.println(produit.toString() + index);
    }
    
}
