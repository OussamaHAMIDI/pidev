/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author monta
 */
public class LigneCommandeArtisantController implements Initializable {

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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void supprimerProduitPanier(MouseEvent event) {
    }

    @FXML
    private void ajouterQuantite(MouseEvent event) {
    }

    @FXML
    private void diminuerQuantite(MouseEvent event) {
    }

    @FXML
    private void test(MouseEvent event) {
    }
    
}
