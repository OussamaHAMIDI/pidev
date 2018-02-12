/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleGroup;

/**
 * FXML Controller class
 *
 * @author Hamdi
 */
public class AddUserController implements Initializable {

    @FXML
    private JFXButton ajouter;
    @FXML
    private JFXButton annuler;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField nom;
    @FXML
    private JFXTextField prenom;
    @FXML
    private JFXTextField adresse;
    @FXML
    private JFXTextField tel;
    @FXML
    private JFXDatePicker date;
    @FXML
    private JFXComboBox<String> etat;
    @FXML
    private JFXComboBox<String> type;
    @FXML
    private ToggleGroup sexe;
    
    ObservableList<String> etatList = FXCollections.observableArrayList("En attente","Active","Connecté");
     ObservableList<String> typeList = FXCollections.observableArrayList("Administrateur","Client","Artisan");
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        type.setValue("Client");
        type.setItems(typeList);
        etat.setValue("Active");
        etat.setItems(etatList);
    }    

    @FXML
    private void ajouterClicked(ActionEvent event) {
    }

    @FXML
    private void annulerClicked(ActionEvent event) {
    }
    
}
