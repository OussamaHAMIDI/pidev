/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Produit;
import Services.ProduitService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class FXMLController implements Initializable {
    @FXML
    private TextField tf_libelle;
    @FXML
    private TextField tf_prix;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void ajoutAction(ActionEvent event) {
        
        Produit p = new Produit();
        p.setLibelle(tf_libelle.getText());
        p.setPrix(Integer.parseInt(tf_prix.getText()));
        
        ProduitService ps = new ProduitService();
        ps.ajouterProduit(p);
    }

    @FXML
    private void vide(ActionEvent event) {
        
        tf_libelle.setText("");
        tf_prix.setText("");
       
    }
    
}
