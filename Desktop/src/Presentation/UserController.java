/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.User;
import Services.UserService;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Hamdi
 */
public class UserController implements Initializable {

    public static User u;

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
    private ImageView photoUser;
    @FXML
    private Label email;
    @FXML
    private AnchorPane user;
    @FXML
    private Circle circle;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeUser.setText(u.getType().toString());
        adresse.setText(u.getAdresse());
        nomPrenom.setText(u.getNom() + " " + u.getPrenom());
        tel.setText(u.getTel());
        email.setText(u.getEmail());
        if (u.getPhoto() != null) {
            circle.setStroke(Color.SEAGREEN);
            circle.setFill(Color.SNOW);
            circle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
            circle.setFill(new ImagePattern(new Image(u.getPhoto())));
            photoUser.setVisible(false);
        }

    }

    @FXML
    private void voirUser(MouseEvent event) {
    }

    @FXML
    private void supprimerUser(MouseEvent event) {
    }

    @FXML
    private void editerUser(MouseEvent event) {
        user.setEffect(new GaussianBlur(5));
        ModifierUserController.blur = user;
        ModifierUserController.userSelected = new UserService().getUserById(u.getId());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierUser.fxml"));
        Stage stage = Utils.getAnotherStage(loader, "Modification");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                user.setEffect(null);
            }
        });
        stage.show();
    }

}
