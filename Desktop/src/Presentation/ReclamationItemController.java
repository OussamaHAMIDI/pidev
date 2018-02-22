/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Reclamation;
import Utils.Enumerations;
import Utils.Enumerations.TypeReclamation;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class ReclamationItemController implements Initializable {
    
    public static Reclamation reclamationPassee;
    @FXML
    private AnchorPane reclamation;
    @FXML
    private Pane idDescription;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Pane buttons;
    @FXML
    private JFXButton buttonBP;
    @FXML
    private JFXButton buttonU;

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        description.setText(reclamationPassee.getDescription());
        id.setText(((Integer)reclamationPassee.getId()).toString());
        if (reclamationPassee.getType()==TypeReclamation.Boutique){
            buttonBP.setText("Voir Boutique");
        }else{
            buttonBP.setText("Voir Produit");
        }
    }    

    private void redirectProduit() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Evaluation.fxml"));
        ReclamationAdmin2Controller.reclamationsChildren.setAll(pane);
        
    }
    private void redirectBoutique() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Evaluation.fxml"));
        ReclamationAdmin2Controller.reclamationsChildren.setAll(pane);
    }
    private void redirectUser() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Evaluation.fxml"));
        ReclamationAdmin2Controller.reclamationsChildren.setAll(pane);
    }

    @FXML
    private void voirBP(ActionEvent event) {
        if (reclamationPassee.getType()==TypeReclamation.Boutique){
            try {
                redirectBoutique();
            } catch (IOException ex) {
                Logger.getLogger(ReclamationItemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else
            try {
                redirectProduit();
        } catch (IOException ex) {
            Logger.getLogger(ReclamationItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void VoirU(ActionEvent event) {
        try {
            redirectUser();
        } catch (IOException ex) {
            Logger.getLogger(ReclamationItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
