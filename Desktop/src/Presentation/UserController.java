/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.User;
import Services.UserService;
import Utils.Enumerations.*;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

/**
 * FXML Controller class
 *
 * @author Hamdi
 */
public class UserController implements Initializable {

    @FXML
    private Label nomPrenom;
    @FXML
    private Label tel;
    @FXML
    private Label typeUser;
    @FXML
    private Label adresse;
    @FXML
    private JFXButton supprimer;
    @FXML
    private Label email;
    @FXML
    private AnchorPane user;
    @FXML
    private Circle circle;

    private User u;
    static public int index;
    static public List<User> contenu;
    public static GestionUsersController guc;
    
    private UserService us = new UserService();

    public void setValues(User u) {
        typeUser.setText(u.getType().toString());
        adresse.setText(u.getAdresse());
        nomPrenom.setText(u.getNom() + " " + u.getPrenom());
        tel.setText(u.getTel());
        email.setText(u.getEmail());
        circle.setStroke(Color.SEAGREEN);
        circle.setFill(Color.SNOW);
        circle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        circle.setFill(new ImagePattern(new Image("Images/user.png")));

        Image img = us.getPhoto(u.getId());
        if (img != null) {
            circle.setFill(new ImagePattern(img));
        }
        if (AccueilController.userConnected != null) {
            if (u.getType() == TypeUser.Administrateur && u.getId() == AccueilController.userConnected.getId()) {
                supprimer.setVisible(false);
            }
        }

        if (u.getEtat() == EtatUser.Deleted) {
            supprimer.setVisible(false);
        }else{
             supprimer.setVisible(true);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        u = GestionUsersController.list.get(index);
        //GestionUsersController.userCont = this;
        setValues(u);
        if (u.getType() == TypeUser.Administrateur || u.getEtat() == EtatUser.Deleted) {
            supprimer.setVisible(false);
        }

    }

    @FXML
    private void voirUser(MouseEvent event) {
        GestionUsersController.userSelected = u;
        guc.setValues(u, "Informations compte");
        guc.voir();
    }

    @FXML
    private void supprimerUser(MouseEvent event) {
//        GestionUsersController.list.remove(u);
//        contenu.remove(u);
//        GridPane parent = (GridPane)user.getParent();
//        parent.getChildren().remove(user);

        Alert alert = Utils.getAlert(Alert.AlertType.CONFIRMATION, "Suppression", null,
                "Voulez-vous vraiment supprimer cet utilisateur ?\nIl ne pourra plus se connecter, mais vous pouvez rétablir son compte en modifiant son état ultérieurement.");
        alert.show();
        alert.resultProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == ButtonType.OK) {
                us.supprimerUser(u.getId());
                supprimer.setVisible(false);
                guc.setValues(new User(0, "", "", EtatUser.Active, TypeUser.Client, "", "", null, "Male", "", "", "", null, "", "", ""), "");
                guc.disable(true);
                guc.updateItems("");
            }
        });
    }

    @FXML
    private void editerUser(MouseEvent event) {
        GestionUsersController.userSelected = u;
        GestionUsersController.uc = this;
        guc.setValues(u, "Modifier compte");
        guc.disable(false);
    }

}
