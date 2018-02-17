/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Produit;
import Entities.Reclamation;
import Entities.User;
import Services.ProduitService;
import Services.ReclamationService;
import Services.UserService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class ReclamationController implements Initializable {
    @FXML
    private Button reclamer;
    @FXML
    private TextArea reclamation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
    }    
    
    @FXML
    private void reclamer(ActionEvent event) {
        
        ProduitService ps = new ProduitService();
        UserService us = new UserService();
        ReclamationService rs = new ReclamationService();
        Produit p = new Produit();
        p.setId(2);
        User u = us.getUserById(2);
        rs.ajouterReclamation(new Reclamation(u,p,reclamation.getText()));
    }
    
}
