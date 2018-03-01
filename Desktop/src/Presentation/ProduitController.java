/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Produit;
import Services.ProduitService;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author oussamahamidi
 */
public class ProduitController implements Initializable {

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
    @FXML
    private JFXButton supprimer;

    @FXML
    private Circle circle;
    @FXML
    private Label prix;
    @FXML
    private Label libelle;
    @FXML
    private Label Description;
    @FXML
    private JFXButton modifierP;
    @FXML
    private Label reference;

    ProduitService ps = new ProduitService();

    public AnchorPane getThis() {
        return produit;
    }

    public void setValues(Produit p) {
        prod = p;
        libelle.setText(p.getLibelle());
        Description.setText(p.getDescription());
        reference.setText(p.getReference());
        Float prixprod = p.getPrix();

        prix.setText(prixprod.toString());
        circle.setStroke(Color.SEAGREEN);
        circle.setFill(Color.SNOW);
        circle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        circle.setFill(new ImagePattern(new Image("Images/camera.png")));
        Image img = ps.getPhoto(p.getId());
        if (img != null) {
            try {
                circle.setFill(new ImagePattern(img));
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prod = MenuProduitsController.list.get(index);
        setValues(prod);

    }

    private void supprimerBoutique(ActionEvent event) {

        Alert alert = Utils.getAlert(Alert.AlertType.CONFIRMATION, "Suppression", null,
                "Voulez-vous vraiment supprimer ce produit ?");
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

    private void modifierBoutique(ActionEvent event) throws IOException {
        ModifierProduitController.bc = this;
        ModifierProduitController.selectedProduit = prod;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierProduit.fxml"));
        Stage s = Utils.getAnotherStage(loader, "Modification d'un produit ");
        s.initStyle(StageStyle.UNDECORATED);
        s.show();
        //prod = ModifierProduitController.selectedProduit;
    }

    @FXML
    private void test(MouseEvent event) {
        
       mc.produitSelected=prod;
        mc.setValues(prod);
    }

    @FXML
    private void supprimerProduit(ActionEvent event) {

        Alert alert = Utils.getAlert(Alert.AlertType.CONFIRMATION, "Suppression", null,
                "Voulez-vous vraiment supprimer ce produit ?");
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
    private void modifierProduit(ActionEvent event) {
        ModifierProduitController.bc = this;
        ModifierProduitController.selectedProduit = prod;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierProduit.fxml"));
        Stage s = Utils.getAnotherStage(loader, "Modification d'un produit ");
        s.initStyle(StageStyle.UNDECORATED);
        s.show();
    }

}
