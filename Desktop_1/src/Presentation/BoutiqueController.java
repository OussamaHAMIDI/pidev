/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.User;
import Services.BoutiqueService;
import Services.ProduitService;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import jfxtras.scene.control.LocalDateTextField;
import jfxtras.scene.control.LocalDateTimeTextField;

/**
 * FXML Controller class
 *
 * @author Azza
 */
public class BoutiqueController implements Initializable {

    @FXML
    private JFXTextField btIdUser;
    @FXML
    private JFXTextField btNom;
    @FXML
    private LocalDateTimeTextField btDate;
    @FXML
    private JFXTextField btAdresse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void validerAction(ActionEvent event) {
        
        BoutiqueService bs = new BoutiqueService();
        Boutique boutique = new Boutique();
        boutique.setIDUser(Integer.parseInt(btIdUser.getText()));
        boutique.setNom(btNom.getText());
        boutique.setDateCreation(btDate.getLocalDateTime());
        boutique.setAdresse(btAdresse.getText());
//        System.out.println("-------------------"+boutique.toString());
        
        bs.ajouterBoutique(boutique);
    }

    @FXML
    private void retourAction(ActionEvent event) {
    }
    
}
