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
import com.sun.javafx.property.adapter.PropertyDescriptor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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
    private ImageView photo;
    @FXML
    private ImageView photoPermis;

    @FXML
    private Button Bt_importer;
    @FXML
    private Button Bt_importerPermis;

    public AnchorPane blur;
    private FileInputStream photoProfil = null;
    private FileInputStream PhotoPermis = null;

    private UserService us = new UserService();

    ObservableList<String> typeList = FXCollections.observableArrayList("Client", "Artisan");
    @FXML
    private ImageView close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        type.setItems(typeList);

        type.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.equals("Artisan")) {
                    photoPermis.setVisible(true);
                    Bt_importerPermis.setVisible(true);
                } else {
                    photoPermis.setVisible(false);
                    Bt_importerPermis.setVisible(false);
                }
            }
        });
        type.setValue("Client");
        date.setConverter(new StringConverter<LocalDate>() {
            private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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

    private void annulerClicked(ActionEvent event) {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

    @FXML
    private void onSetAction_importer(ActionEvent event) throws IOException {
        FileChooser file = new FileChooser(); //pour choisir la photo
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp"));
        file.setTitle("Choisir une photo de profil");

        File selected_photo = file.showOpenDialog((Stage) close.getScene().getWindow());
        if (selected_photo != null) {
            if ((selected_photo.length() / 1024) / 1024 < 2.0) {
                String path = selected_photo.getAbsolutePath();
                BufferedImage bufferedImage = ImageIO.read(selected_photo);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                photo.setImage(image);

                File img = new File(path);
                photoProfil = new FileInputStream(img);
            } else {
                Utils.showAlert(Alert.AlertType.ERROR, "Erreur", "Taile trop grande !", "Veuillez choisir une photo de profil avec une taille < 2 Mo");
            }
        }

    }

    @FXML
    private void inscrireClicked(ActionEvent event) {

        if (verif()) {
            if (type.getValue().equals("Artisan") && PhotoPermis == null) {
                Utils.showAlert(Alert.AlertType.WARNING, "Informations manquantes", "Informations manquantes", "Veuillez importer votre permis de vente afin que l'administrateur le vérifie");
            } else {
//              String mdp = Utils.hashPassword(password.getText(), salt);
                String mdp = BCrypt.hashpw(password.getText(),  BCrypt.gensalt(10));
                String code = Utils.generateCode(6);
                String sexe = ((RadioButton) this.sexe.getSelectedToggle()).getText();

                if (type.getValue().equals("Artisan")) {
                    code = null;
                }

                User u = new User(us.getNextId(), username.getText(), mdp, EtatUser.Pending, TypeUser.valueOf(type.getValue()), nom.getText(), prenom.getText(),
                        date.getValue(), sexe, email.getText(), adresse.getText(), tel.getText(),
                        null, null, "role va etre ajouter dans la methode ajouterUser", code, photoProfil);

                if (us.ajouterUser(u)) {

                    if (type.getValue().equals("Artisan") && PhotoPermis != null) {
                        us.addPhotoArtisan(u.getId(), PhotoPermis);
                    }

                    if (!type.getValue().equals("Artisan")) {
                        Utils.showTrayNotification(NotificationType.CUSTOM, "Inscription avec succès", null, "Veuillez verifier votre boite mail",
                                u.getPhoto(), 6000);
                        Utils.showAlert(Alert.AlertType.INFORMATION, "Presque terminé", "Derniere étape", "• Un code de vérification a été envoyé à :\n"
                                + u.getEmail() + "\n- Il vous sera demandé lors de votre 1ère tentative de connexion.");
                        Utils.sendMail(email.getText(), code);
                    } else {
                        Utils.showTrayNotification(NotificationType.CUSTOM, "Inscription avec succès", null, "Veuillez attendre la confirmation de "
                                + "l'administrateur", u.getPhoto(), 6000);
                        Utils.showAlert(Alert.AlertType.INFORMATION, "Presque terminé", "Derniere étape", "• Si l'administrateur confirme votre permis, un code "
                                + "de vérification sera envoyé à :\n" + u.getEmail() + "\n- Il vous sera demandé lors de votre 1ère tentative de connexion.");
                    }

                    Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    s.close();

                }
            }
        }
    }

    @FXML
    private void onSetAction_importerPermis(ActionEvent event) throws IOException {
        FileChooser file = new FileChooser(); //pour choisir la photo
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp"));
        file.setTitle("Choisir une photo de permis");

        File selected_photo = file.showOpenDialog((Stage) close.getScene().getWindow());
        if (selected_photo != null) {
            if ((selected_photo.length() / 1024) / 1024 < 2.0) {
                String path = selected_photo.getAbsolutePath();
                BufferedImage bufferedImage = ImageIO.read(selected_photo);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                photoPermis.setImage(image);

                File img = new File(path);
                this.PhotoPermis = new FileInputStream(img);
            } else {
                Utils.showAlert(Alert.AlertType.ERROR, "Erreur", "Taile trop grande !", "Veuillez choisir une photo de permis avec une taille < 2 Mo");
            }
        }
    }

    private boolean verif() {

        if (username.getText().isEmpty() || email.getText().isEmpty() || password.getText().isEmpty() || nom.getText().isEmpty()
                || prenom.getText().isEmpty() || tel.getText().isEmpty() || adresse.getText().isEmpty() || date.getValue() == null) {
            Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien renseigner tous les champs !");
            return false;
        } else {

            if (!Pattern.matches("([a-zA-Z]+)|([a-zA-Z]+ [a-zA-Z]+)|([a-zA-Z]+ [a-zA-Z]+ [a-zA-Z]+)", nom.getText())) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez votre nom ! ");
                nom.requestFocus();
                nom.selectEnd();
                return false;
            }

            if (!Pattern.matches("([a-zA-Z]+)|([a-zA-Z]+ [a-zA-Z]+)|([a-zA-Z]+ [a-zA-Z]+ [a-zA-Z]+)", prenom.getText())) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez votre prénom ! ");
                prenom.requestFocus();
                prenom.selectEnd();
                return false;
            }

            if (!Pattern.matches("\\d*", tel.getText()) || tel.getText().length() < 8) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Le numéro de telephone doit contenir 8 chiffres ou plus.\nExemple : 96451906");
                tel.requestFocus();
                tel.selectEnd();
                return false;
            }

            if (date.getValue().toString().length() != 10) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien renseigner votre date de naissance.\nExemple : 1995-11-25");

                date.requestFocus();
                return false;
            }

            if (date.getValue().getYear() > 2010) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vous êtes trop jeune !");
                date.requestFocus();
                return false;
            }

            if (!Pattern.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d", date.getValue().toString())) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Verifier bien renseigner une date de naissance valide.\nExemple : 1995-11-25");
                date.requestFocus();
                return false;
            }

            if (!Pattern.matches("([a-zA-Z])+", username.getText())) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Le nom d'utilisateur doit contenir que des lettres.\nExemple : TestSoukLemdina");
                username.requestFocus();
                username.selectEnd();
                return false;
            }

            if (!Utils.verifEmail(email.getText())) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Email non valide !");
                email.requestFocus();
                email.selectEnd();
                return false;
            }

            if (us.verifColumn("username", username.getText())) {// if existing in table user
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Nom d'utilisateur déjà existant, veuillez choisir un autre");
                username.requestFocus();
                username.selectEnd();
                return false;
            }

            if (us.verifColumn("email", email.getText())) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Email déjà existant, veuillez choisir un autre");
                email.requestFocus();
                email.selectEnd();
                return false;
            }
        }
        return true;
    }

    @FXML
    private void closeClicked(MouseEvent event) {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

}
