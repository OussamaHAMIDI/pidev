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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import org.mindrot.BCrypt;
import tray.notification.NotificationType;

/**
 * FXML Controller class
 *
 * @author Hamdi
 */
public class InscriptionController implements Initializable {

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
    private JFXComboBox<String> type;
    @FXML
    private ToggleGroup sexe;
    @FXML
    private Label titre;
    @FXML
    private Button Bt_importer;
    @FXML
    private ImageView photo;
    @FXML
    private JFXButton inscrire;

    ObservableList<String> typeList = FXCollections.observableArrayList("Administrateur", "Client", "Artisan");
    private FileInputStream photoProfil = null;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        type.setValue("Client");
        type.setItems(typeList);
        date.setConverter(new StringConverter<LocalDate>() {
            private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            @Override
            public String toString(LocalDate localDate) {
                if (localDate == null) {
                    return "";
                }
                return dateTimeFormatter.format(localDate);
            }

            @Override
            public LocalDate fromString(String dateString) {
                if (dateString == null || dateString.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(dateString, dateTimeFormatter);
            }
        });
    }

    @FXML
    private void annulerClicked(ActionEvent event) {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

    @FXML
    private void onSetAction_importer(ActionEvent event) throws IOException {
        FileChooser file = new FileChooser(); //pour choisir la photo
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp"));
        file.setTitle("Choisir une photo de profil");

        File selected_photo = file.showOpenDialog((Stage) annuler.getScene().getWindow());
        if (selected_photo != null) {
            if ((selected_photo.length() / 1024) / 1024 < 4.0) {
                String path = selected_photo.getAbsolutePath();
                BufferedImage bufferedImage = ImageIO.read(selected_photo);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                photo.setImage(image);

                File img = new File(path);
                photoProfil = new FileInputStream(img);
                //img = (int) img.length();
            } else {
                Utils.showAlert(Alert.AlertType.ERROR, "Erreur", "Taile trop grande !", "Veuillez choisir une photo de profil avec une taille < 4 Mo");
            }
        }

    }

    @FXML
    private void inscrireClicked(ActionEvent event) {
        UserService us = new UserService();
        if (us.verifColumn("username", username.getText())) {// if existing in table user
            Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Nom d'utilisateur déjà existant, veuillez choisir un autre");
            username.requestFocus();
        } else if (us.verifColumn("email", email.getText())) {
            Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Email déjà existant, veuillez choisir un autre");
            email.requestFocus();
        } else if (date.getValue() == null) {
            Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien renseigner votre date de naissance.\nExemple : 1995-11-25");
            email.requestFocus();
        } else {
            String salt = BCrypt.gensalt(4);
            String mdp = Utils.hashPassword(password.getText(), salt);
            String code = Utils.generateCode(6);
            String sexe = ((RadioButton) this.sexe.getSelectedToggle()).getText();

            User u = new User(us.getNextId(), username.getText(), mdp, EtatUser.Active, TypeUser.valueOf(type.getValue()), nom.getText(), prenom.getText(),
                    date.getValue(), sexe, email.getText(), adresse.getText(), tel.getText(),
                    null, salt, "role va etre ajouter dans la methode ajouterUser", code, photoProfil);

            if (us.ajouterUser(u)) {
/************************************************************************************************************************************************************/
                //Utils.sendMail(email.getText(), code);
                
/************************************************************************************************************************************************************/
                Utils.showTrayNotification(NotificationType.CUSTOM, "Inscription avec succès", null, "Veuillez verifier votre boite mail", 
                        new Image(u.getPhoto()), 6000);

                Utils.showAlert(Alert.AlertType.INFORMATION, "Presque terminé", "Derniere étape", "• Un code de vérification a été envoyé à :\n" + u.getEmail()
                        + "\n- Il vous sera demandé lors de votre 1ère tentative de connexion.");
                Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
                s.close();

            }
        }
    }

}
