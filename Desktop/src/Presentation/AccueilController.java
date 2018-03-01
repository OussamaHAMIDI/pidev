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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import tray.notification.NotificationType;

public class AccueilController implements Initializable {

    @FXML
    private AnchorPane holderPane;

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
    private MenuItem supprimerCompte;
    @FXML
    private VBox sideBarAdmin;
    @FXML
    private AnchorPane menuBar;

    private UserService us = new UserService();
    public static User userConnected = null;
    AnchorPane users;
    AnchorPane anchor;

    @FXML
    private VBox sideBarArtisan;
    @FXML
    private VBox sideBarClient;
    @FXML
    private VBox sideBarVisiteur;

    //Set selected node to a content holder
    private void setNode(String nomFichierFxml) {

        try {
            anchor = FXMLLoader.load(getClass().getResource(nomFichierFxml + ".fxml"));

        } catch (IOException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }

        holderPane.getChildren().clear();
        holderPane.getChildren().add(anchor);
        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(anchor);
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
                    sideBarArtisan.setVisible(false);
                    sideBarClient.setVisible(false);
                    sideBarVisiteur.setVisible(false);
                    break;
                case Artisan:
                    sideBarAdmin.setVisible(false);
                    sideBarArtisan.setVisible(true);
                    sideBarClient.setVisible(false);
                    sideBarVisiteur.setVisible(false);
                    break;
                case Client:
                    sideBarAdmin.setVisible(false);
                    sideBarArtisan.setVisible(false);
                    sideBarClient.setVisible(true);
                    sideBarVisiteur.setVisible(false);
                    break;
            }
        } else {
            btn_logout.setVisible(false);
            btn_user.setVisible(false);
            btn_login.setVisible(true);
            username.setText("Bienvenue visiteur");

            sideBarAdmin.setVisible(false);
            sideBarArtisan.setVisible(false);
            sideBarClient.setVisible(false);
            sideBarVisiteur.setVisible(true);

            setNode("MenuProduits");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LoginController.ac = this;
        infos.setOnAction(event -> {
            ModifierUserController.blur = holderPane;
            ModifierUserController.userSelected = userConnected;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierUser.fxml"));
            Stage stage = Utils.getAnotherStage(loader, "Modifier infos user");
            stage.show();
        });
        changerMdp.setOnAction(event -> {
            String code = Utils.generateCode(6);
            Utils.sendMail(userConnected.getEmail(), code);
            ChangerMdpController.set(code, userConnected, holderPane);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangerMdp.fxml"));
            Stage stage = Utils.getAnotherStage(loader, "Changer mot de passe");
            stage.show();
        });
        supprimerCompte.setOnAction(event -> {
            Alert alert = Utils.getAlert(Alert.AlertType.CONFIRMATION, "Confirmation", null,
                    "Voulez-vous vraiment supprimé votre compte ?");
            alert.show();
            alert.resultProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue == ButtonType.OK) {
                    us.supprimerUser(userConnected.getId());
                    Utils.showTrayNotification(NotificationType.CUSTOM, "Informations", null, "Ce compte a été supprimé", us.getPhoto(userConnected.getId()), 6000);
                    userConnected = null;
                    setAccueil();
                }
            });

        });
        
        setAccueil();

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
        if (userConnected == null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
            Utils.getAnotherStage(loader, "Connexion").show();
        }
    }

    @FXML
    private void switchAccueilArtisan(ActionEvent event) {
        setNode("StatistiqueArtisan");
    }

    @FXML
    private void switchProduitsArtisan(ActionEvent event) {
        setNode("MenuProduits");
    }

    @FXML
    private void switchBoutiqueArtisan(ActionEvent event) {
          setNode("MenuBoutique");
    }

    @FXML
    private void switchAccueilClient(ActionEvent event) {
        setNode("MenuProduits");
    }

    @FXML
    private void switchProduitsClient(ActionEvent event) {
        setNode("MenuProduits");
    }

    @FXML
    private void switchBoutiquesClient(ActionEvent event) {
        setNode("MenuBoutique");
    }

    @FXML
    private void switchHistoriqueClient(ActionEvent event) {
         setNode("Panier");
    }

    @FXML
    private void switchAccueilAdmin(ActionEvent event) {
         setNode("Statistique");
    }

    @FXML
    private void switchUsersAdmin(ActionEvent event) {
        setNode("GestionUsers");
    }

    @FXML
    private void switchProduitsAdmin(ActionEvent event) {
        setNode("MenuProduits");
    }

    @FXML
    private void switchBoutiquesAdmin(ActionEvent event) {
         setNode("MenuBoutique");
    }

    @FXML
    private void switchReclamationsAdmin(ActionEvent event) {
         setNode("ReclamationAdmin2");
    }

    @FXML
    private void switchAccueilVisiteur(ActionEvent event) {
         setNode("MenuProduits");
    }

    @FXML
    private void switchProduitsVisiteur(ActionEvent event) {
         setNode("MenuProduits");
    }

    @FXML
    private void switchBoutiquesVisiteur(ActionEvent event) {
        setNode("MenuBoutique");
    }

}
