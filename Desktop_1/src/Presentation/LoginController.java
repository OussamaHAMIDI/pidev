/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Utils.mouseDrag;

import Entities.User;
import Services.UserService;
import Utils.BCrypt;
import Utils.Enumerations.EtatUser;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import tray.notification.NotificationType;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
        // TODO
    }

    @FXML
    private void handleExit(MouseEvent event) {
        exitClicked();
//        blur.setEffect(new GaussianBlur(10));
//       // new FadeInRightTransition(pane).play();
//        AnchorPane pane = null;
//        try {
//            pane = FXMLLoader.load(getClass().getResource("addUser.fxml"));
//        } catch (IOException ex) {
//            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        loadPane.getChildren().setAll(pane);
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
            if (u != null) {
                String pswHashed = BCrypt.hashpw(password.getText(), u.getSalt());
                if (pswHashed.equals(u.getMdp())) {
                    if (u.getEtat() == EtatUser.Active || u.getEtat() == EtatUser.Disconnected) {
                        Utils.showTrayNotification(NotificationType.NOTICE, "Connexion etablie", u.getType().toString(), "Bienvenue " + u.getNom() + " " + u.getPrenom(),
                                u.getPhoto());
                        switch (u.getType()) {
                            case Administrateur:
                                Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                stage2.hide();

                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(getClass().getResource("HomeAdmin.fxml"));
                                try {
                                    loader.load();
                                } catch (Exception e) {
                                }
                                HomeAdminController hc = loader.getController();
                                hc.setValue(u.getId());
                                Parent p = loader.getRoot();
                                Stage stage = new Stage();
                                Scene pp = new Scene(p);
                                pp.setFill(javafx.scene.paint.Color.TRANSPARENT);
                                stage.initStyle(StageStyle.TRANSPARENT);
                                stage.setScene(pp);
                                stage.setTitle("Home");
                                stage.getIcons().add(new Image("Images/souk.png"));
                                mouseDrag md = new mouseDrag();
                                md.setDragged(p, stage);
                                stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                                    @Override
                                    public void handle(WindowEvent event) {
                                        Platform.runLater(new Runnable() {
                                            @Override
                                            public void run() {
//                                                UserService uss = new UserService();
//                                                
//                                                uss.modifierEtatUser(uss.getUserById(u.getId()), EtatUser.Active);
//                                                us.modifierEtatUser(u, Enumerations.EtatUser.Disconnected);
                                                System.exit(0);
                                            }
                                        });
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
            } else {
                Utils.showAlert(Alert.AlertType.ERROR, "Erreur de connexion", null, "Username et/ou mot de passe invalide(s)");
                username.requestFocus();
            }
        }
    }

    @FXML
    private void handleCreateUser(MouseEvent event) {

    }

    
    public void exitClicked() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText("Voulez vous vraiment quitter l'application ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    public Image convertToFile(InputStream img) {
        InputStream inputStream = img;
        OutputStream outputStream = null;
        Image imgConverted = null;
        String path = null;
        try {
            path = "/src/Images/photo.png";
            // write the inputStream to a FileOutputStream
            outputStream = new FileOutputStream(new File(path));

            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }

            System.out.println("Done!");

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    // outputStream.flush();
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
        return new Image(path);
    }

}
