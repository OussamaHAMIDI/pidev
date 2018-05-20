/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.User;
import Utils.Utils;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Hamdi
 */
public class ShowUserController implements Initializable {

    @FXML
    private Label titre;
    @FXML
    private JFXTextField username;
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
    private ImageView photo;
    @FXML
    private ImageView close;
    @FXML
    private JFXTextField type;
    @FXML
    private JFXTextField sexe;
    @FXML
    private JFXTextField date;

    public static User selectedUser = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (selectedUser != null) {
            photo.setImage(new Image(Utils.dir + selectedUser.getPhoto()));
            titre.setText("Informations " + selectedUser.getType().toString());
            nom.setText(selectedUser.getNom());
            prenom.setText(selectedUser.getPrenom());
            username.setText(selectedUser.getUserName());
            email.setText(selectedUser.getEmail());
            adresse.setText(selectedUser.getAdresse());
            type.setText(selectedUser.getType().toString());
            tel.setText(selectedUser.getTel());
            sexe.setText(selectedUser.getSexe());
            date.setText(selectedUser.getDateNaissance().toString());
            selectedUser = null;
        }

    }

    @FXML
    private void closeClicked(MouseEvent event) {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

}
