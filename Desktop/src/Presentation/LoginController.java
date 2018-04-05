/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.User;
import Services.UserService;
import Utils.BCrypt;
import Utils.Enumerations.EtatUser;
import Utils.Enumerations.TypeUser;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import tray.notification.NotificationType;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private JFXButton createUser;
    @FXML
    private AnchorPane blur;
    @FXML
    private StackPane pane;
    @FXML
    private AnchorPane loadPane;
    @FXML
    private Label chnagerMdp;

    UserService us = new UserService();
    public static AccueilController ac;
    User u = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createUser.setCursor(Cursor.HAND);
        exit.setCursor(Cursor.HAND);

        username.setText("HamdiMegdiche");
        password.setText("test");
    }

    @FXML
    private void handleExit(MouseEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleLoginClicked(MouseEvent event) {

        String username_text = username.getText();
        String password_text = password.getText();
        if (username_text.isEmpty() || password_text.isEmpty()) {
            Utils.showAlert(Alert.AlertType.WARNING, "Champ(s) vide(s)", null, "Veuillez bien renseigner votre username et/ou mot de passe");
            username.requestFocus();
        } else {
            u = us.getUserByUsername(username_text);
            if (username_text.contains("@")) {
                u = us.getUserByEmail(username_text);
            }

            if (u != null) {
                if (BCrypt.checkpw(password_text, u.getMdp().replace("$2y$10$", "$2a$10$"))) {

                    if (u.getEtat() == EtatUser.Active || u.getEtat() == EtatUser.Disconnected || u.getEtat() == EtatUser.Connected) {

                        Utils.showTrayNotification(NotificationType.NOTICE, "Connexion établie", u.getType().toString(), "Bienvenue " + u.getNom() + " "
                                + u.getPrenom(), u.getPhoto(), 5000);

                        us.modifierEtatUser(u.getId(), EtatUser.Connected);
                        AccueilController.userConnected = u;
                        ac.setAccueil();

                        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        stage.close();

                    } else if (u.getEtat() == EtatUser.Pending) {
                        if (u.getType() == TypeUser.Artisan && (u.getToken() == null || u.getToken().isEmpty())) {
                            Utils.showAlert(Alert.AlertType.ERROR, "Confirmation en attente", null, "Bienvenue artisan " + u.getPrenom() + ".\n"
                                    + "Vos données sont corrects mais il faut attendre la confirmation de votre permis de vente de la part de l'administrateur.\n"
                                    + "Un mail sera envoyé lors de la confirmation à : " + u.getEmail());
                        } else {
                            Alert alert = Utils.getAlert(Alert.AlertType.CONFIRMATION, "Bienvenue", null, "Entrez le code de validation envoyé à votre mail :"
                                    + "\n" + u.getEmail().substring(0, 5) + "***" + u.getEmail().substring(u.getEmail().indexOf("@"), u.getEmail().indexOf("@") + 5) + "***");
                            JFXTextField tf_code = new JFXTextField();
                            tf_code.setPromptText("######");
                            alert.setGraphic(tf_code);
                            tf_code.requestFocus();
                            alert.show();
                            alert.resultProperty().addListener((observable, oldValue, newValue) -> {
                                if (newValue == ButtonType.OK) {
                                    if (u.getToken().equals(tf_code.getText().trim())) {
                                        us.modifierEtatUser(u.getId(), EtatUser.Active);
                                        Utils.showTrayNotification(NotificationType.NOTICE, "Vous êtes confirmé", u.getType().toString(), "Veuillez-vous connecter",
                                                u.getPhoto(), 5000);
                                    }
                                }
                            });
                        }
                    } else if (u.getEtat() == EtatUser.Deleted) {
                        Alert alert = Utils.getAlert(Alert.AlertType.CONFIRMATION, "Bienvenue", null, "Votre compte est supprimé, voulez-vous le récuperer ?");
                        alert.show();
                        alert.resultProperty().addListener((observable, oldValue, newValue) -> {
                            if (newValue == ButtonType.OK) {
                                alert.close();
                                String code = Utils.generateCode(6);
                                Utils.sendMail(u.getEmail(), code);
                                us.changerToken(code, u.getEmail());
                                Alert alert2 = Utils.getAlert(Alert.AlertType.CONFIRMATION, "Bienvenue", null, "Entrez le code de validation envoyé à votre mail :"
                                        + "\n" + u.getEmail().substring(0, 5) + "***" + u.getEmail().substring(u.getEmail().indexOf("@"), u.getEmail().indexOf("@") + 5) + "***");
                                JFXTextField tf_code = new JFXTextField();
                                tf_code.setPromptText("######");
                                tf_code.requestFocus();
                                alert2.setGraphic(tf_code);
                                alert2.show();
                                alert2.resultProperty().addListener((observable2, oldValue2, newValue2) -> {
                                    if (newValue2 == ButtonType.OK) {
                                        if (code.equals(tf_code.getText().trim())) {
                                            us.modifierEtatUser(u.getId(), EtatUser.Connected);
                                            AccueilController.userConnected = u;
                                            ac.setAccueil();

                                            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                            stage.close();
                                        }
                                    }
                                });
                            }
                        });
                    }
                } else {
                    Utils.showAlert(Alert.AlertType.ERROR, "Erreur de connexion", null, "Username et/ou mot de passe invalide(s)");
                    username.requestFocus();
                }
            } else {
                Utils.showAlert(Alert.AlertType.ERROR, "Erreur de connexion", null, "Username et/ou mot de passe invalide(s)");
                username.requestFocus();
            }
        }
    }

    @FXML
    private void changerMdpClicked(MouseEvent event) {
        String usr = username.getText();

        if (usr.equals("")) {
            Utils.showAlert(Alert.AlertType.WARNING, "Champ vide", null, "Veuillez bien renseigner votre nom d'utilisateur ou email");
            username.requestFocus();
        } else {
            User u;
            if (usr.contains("@")) {
                u = us.getUserByEmail(usr);
            } else {
                u = us.getUserByUsername(usr);
            }
            if (u != null) {
                String code = Utils.generateCode(6);
                Utils.sendMail(u.getEmail(), code);
                ChangerMdpController.set(code, u, blur);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangerMdp.fxml"));
                Stage stage = Utils.getAnotherStage(loader, "Changer mot de passe");
                stage.initStyle(StageStyle.TRANSPARENT);
                stage.show();

            } else {
                Alert alert = Utils.getAlert(Alert.AlertType.CONFIRMATION, "Erreur de récupération", null,
                        "Username ou mail n'existe pas dans notre base de données \nVoulez-vous faire une inscription ?");
                alert.show();
                alert.resultProperty().addListener(new ChangeListener<ButtonType>() {
                    @Override
                    public void changed(ObservableValue<? extends ButtonType> observable, ButtonType oldValue, ButtonType newValue) {
                        if (newValue == ButtonType.OK) {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("Inscription.fxml"));
                            Utils.getAnotherStage(loader, "Inscription").show();
                        }
                    }
                });

                username.requestFocus();
            }
        }
    }

    @FXML
    private void createUserClicked(MouseEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Inscription.fxml"));
        Utils.getAnotherStage(loader, "Inscription").show();
    }

}
