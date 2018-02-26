package Presentation;

import Entities.User;
import Services.UserService;
import Utils.Enumerations.*;
import Utils.Utils;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class AccueilController implements Initializable {

    @FXML
    private JFXButton btnHome;
    @FXML
    private AnchorPane holderPane;
    @FXML
    private JFXButton btnUsers;

    private AnchorPane menu;
    @FXML
    private Label username;
    @FXML
    private Circle photoUser;
    @FXML
    private JFXButton btn_logout;
    @FXML
    private MenuButton btn_user;
    @FXML
    private MenuItem infos;
    @FXML
    private MenuItem changerMdp;
    @FXML
    private JFXButton btn_login;
    @FXML
    private VBox sideBarAdmin;
    @FXML
    private AnchorPane menuBar;

    private UserService us = new UserService();
    public static User userConnected = null;
    AnchorPane users;

    //Set selected node to a content holder
    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);
        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

    public void setAccueil() {
        if (userConnected != null) {
            btn_logout.setVisible(true);
            btn_user.setVisible(true);
            btn_login.setVisible(false);
            username.setText("Bienvenue " + userConnected.getNom() + " " + userConnected.getPrenom());
            photoUser.setStroke(Color.SEAGREEN);
            photoUser.setFill(Color.SNOW);
            photoUser.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
            photoUser.setFill(new ImagePattern(us.getPhoto(userConnected.getId())));

            switch (userConnected.getType()) {
                case Administrateur:
                    sideBarAdmin.setVisible(true);

                    try {
                        users = FXMLLoader.load(getClass().getResource("GestionUsers.fxml"));
                        setNode(users);
                    } catch (IOException ex) {
                        Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case Artisan:

                    break;
                case Client:

                    break;
            }
        } else {
            btn_logout.setVisible(false);
            btn_user.setVisible(false);
            btn_login.setVisible(true);
            username.setText("Bienvenue visiteur");
            //charger le menu pour visiteur
            sideBarAdmin.setVisible(true);

            //set node home visiteur
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LoginController.ac = this;
        infos.setOnAction(event -> {

            System.out.println("Option infos selected via Lambda");
        });
        changerMdp.setOnAction(event -> {
            String code = Utils.generateCode(6);
            Utils.sendMail(userConnected.getEmail(), code);
            ChangerMdpController.set(code, userConnected, holderPane);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangerMdp.fxml"));
            Stage stage = Utils.getAnotherStage(loader, "Changer mot de passe");
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
        });

        try {
            // GestionUsersController.list = new UserService().getUsers();

            users = FXMLLoader.load(getClass().getResource("GestionUsers.fxml"));
            setNode(users);
        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        setAccueil();
    }

    @FXML
    private void switchUsers(ActionEvent event) {
        setNode(users);
    }

    @FXML
    private void logout(ActionEvent event) {
        if (userConnected != null) {
            us.modifierEtatUser(userConnected.getId(), EtatUser.Disconnected);
            userConnected = null;
            setAccueil();
        }
    }

    @FXML
    private void loginClicked(MouseEvent event) {
        System.out.println("dkhal");
        if (userConnected == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Utils.getAnotherStage(loader, "Connexion").show();
        }
    }

}
