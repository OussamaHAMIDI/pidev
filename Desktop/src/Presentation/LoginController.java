/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.User;
import Services.UserService;
import Utils.Enumerations.EtatUser;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import tray.notification.NotificationType;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * FXML Controller class
 *
 * @author Hamdi
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane loginForm;
    @FXML
    private AnchorPane Header;
    @FXML
    private ImageView exit;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField password;
    @FXML
    private JFXButton login;
    @FXML
    private Label createUser;
    @FXML
    private AnchorPane blur;
    @FXML
    private StackPane pane;
    @FXML
    private AnchorPane loadPane;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        createUser.setCursor(Cursor.HAND);
        exit.setCursor(Cursor.HAND);
        username.setText("HamdiMegdiche");
        password.setText("25111995");
    }

    @FXML
    private void handleExit(MouseEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez vous vraiment quitter l'application ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Platform.exit();
            System.exit(0);
        }

    }

    @FXML
    private void handleLoginClicked(MouseEvent event) {

        String username_text = username.getText();
        String password_text = password.getText();
        if (username_text.equals("") || password_text.equals("")) {
            Utils.showAlert(Alert.AlertType.WARNING, "Champ(s) vide(s)", null, "Veuillez bien renseigner votre username et/ou mot de passe");
            username.requestFocus();
        } else {
            UserService us = new UserService();
            User u = null;
            u = us.getUserByUsername(username.getText());
            int idUser = u.getId();
            if (u != null) {
                String pswHashed = Utils.hashPassword(password_text, u.getSalt());
                if (pswHashed.equals(u.getMdp())) {
                    System.out.println("dkhal ");
                    if (u.getEtat() == EtatUser.Active || u.getEtat() == EtatUser.Disconnected) {
                        Utils.showTrayNotification(NotificationType.NOTICE, "Connexion etablie", u.getType().toString(), "Bienvenue " + u.getNom() + " "
                                + u.getPrenom(), new Image(u.getPhoto()),2000);
                        us.modifierEtatUser(idUser, EtatUser.Connected);
                        switch (u.getType()) {
                            case Administrateur:
                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage.close();

                                FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionUsers.fxml"));
                                stage = Utils.getAnotherStage(loader, "Bienvenue " + u.getPrenom() + " " + u.getNom());

                                GestionUsersController guc = loader.getController();
                                guc.id = Integer.toString(u.getId()); // transfer Id to new stage **************************************

                                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                    @Override
                                    public void handle(WindowEvent we) {
                                        UserService us = new UserService();
                                        us.modifierEtatUser(idUser, EtatUser.Disconnected);
                                    }
                                });

                                stage.show();

                                break;
                            case Artisan:

                                break;
                            case Client:

                                break;
                        }

                    } else if (u.getEtat() == EtatUser.Pending) {
                        Alert alert = Utils.getAlert(Alert.AlertType.CONFIRMATION, "Bienvenue", null, "Vous êtes maintenant membre");
                        TextField tf_code = new TextField();
                        tf_code.setPromptText("Entrez le code de validation");
                        alert.setGraphic(tf_code);
                        alert.show();
                        alert.resultProperty().addListener(new ChangeListener<ButtonType>() {
                            @Override
                            public void changed(ObservableValue<? extends ButtonType> observable, ButtonType oldValue, ButtonType newValue) {
                                if (newValue == ButtonType.OK) {
                                    User u = us.getUserById(idUser);
                                    if (u.getToken().equals(tf_code.getText())) {
                                        us.modifierEtatUser(u.getId(), EtatUser.Active);
                                        switch (u.getType()) {
                                            case Administrateur:
                                                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                                stage.close();
                                                FXMLLoader loader = new FXMLLoader(getClass().getResource("GestionUsers.fxml"));

                                                stage = Utils.getAnotherStage(loader, "Bienvenue " + u.getPrenom() + " " + u.getNom());

                                                GestionUsersController guc = loader.getController();
                                                guc.id = Integer.toString(u.getId()); // transfer Id to new stage **************************************

                                                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                                    @Override
                                                    public void handle(WindowEvent we) {
                                                        UserService us = new UserService();
                                                        us.modifierEtatUser(idUser, EtatUser.Disconnected);
                                                    }
                                                });

                                                stage.show();
                                                break;
                                            case Artisan:

                                                break;
                                            case Client:

                                                break;
                                        }
                                    }
                                }
                            }
                        });

                    }
                }
            } else {
                Utils.showAlert(Alert.AlertType.ERROR, "Erreur de connexion", null, "Username et/ou mot de passe invalide(s)");
                username.requestFocus();
            }
        }
    }

    @FXML
    private void handleCreateUser(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inscription.fxml"));
        Stage s = Utils.getAnotherStage(loader, "Inscription");
        s.show();

//        Stage s = (Stage) createUser.getScene().getWindow();
//        s.close();
    }

}
