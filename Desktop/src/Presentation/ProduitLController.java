/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Produit;
import Entities.ProduitPanier;
import Services.BoutiqueService;
import Services.ProduitService;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author oussamahamidi
 */
public class ProduitLController implements Initializable {
    
    private Produit prod;
    static public int index;
    static public List<Produit> contenu;
    static public MenuProduitsController mc;
     static public ListProduitsController lp;
    @FXML
    private AnchorPane produit;
    private Label adresseB;
    private Label nomB;
    private Label dateB;
    ProduitService ps= new ProduitService();
    @FXML
    private Circle circle;
    @FXML
    private Label prix;
    @FXML
    private Label libelle;
    @FXML
    private Label Description;
    @FXML
    private Label reference;
    @FXML
    private JFXButton produitB;
    @FXML
    private JFXButton show;
            

     public AnchorPane getThis()
    {
        return produit;
    }
     public void setValues(Produit b){
         libelle.setText(prod.getLibelle());
        Description.setText(prod.getDescription());
          reference.setText(prod.getReference());
          Float prixprod = prod.getPrix();
          
        prix.setText(prixprod.toString());
        circle.setStroke(Color.SEAGREEN);
        circle.setFill(Color.SNOW);
        circle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        circle.setFill(new ImagePattern(new Image("Images/camera.png")));
        Image img = ps.getPhoto(prod.getId());
        if (img != null) {
            try
            {
                circle.setFill(new ImagePattern(img));
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
     }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         prod = ListProduitsController.produits.get(index);
        setValues(prod);
        
    }    

    private void supprimerBoutique(ActionEvent event) {
         
    

        Alert alert = Utils.getAlert(Alert.AlertType.CONFIRMATION, "Suppression", null,
                "Voulez-vous vraiment supprimer cette boutique ?");
        alert.show();
        alert.resultProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == ButtonType.OK) {
                 MenuProduitsController.list.remove(prod);
                    contenu.remove(prod);
//        GridPane parent = (GridPane)user.getParent();
//        parent.getChildren().remove(user);

                ps.supprimerProduit(prod.getId());
                mc.updateItems("");
                
            }
        });
    }

  

    @FXML
    private void test(MouseEvent event) {
        mc.produitSelected = prod;
        mc.setValues(prod);
//        System.out.println(bou.toString() + index);
    }

    @FXML
    private void ajouterPanier(ActionEvent event) {
    }

    @FXML
    private void showProduit(ActionEvent event) {
         FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterProduit.fxml"));
      
        AjouterProduitController.voirProd = prod;
        AjouterProduitController.voir = true;
        Utils.getAnotherStage(loader, "voir produit ").show();
    }


  

}
