/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Azza
 */
public class InterfaceModifierBoutiqueController implements Initializable {

    @FXML
    private Button button1;
    @FXML
    private Button button2;
    @FXML
    private Button button3;
    @FXML
    private Button button4;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifierNom(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("ModifierNomBoutique.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    @FXML
    private void modifierAdresse(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("ModifierAdresseBoutique.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    @FXML
    private void modifierBou(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("ModifierBoutique.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    
}
