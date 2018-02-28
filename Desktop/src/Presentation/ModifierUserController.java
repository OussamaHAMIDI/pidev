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
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import tray.notification.NotificationType;

/**
 * FXML Controller class
 *
 * @author Hamdi
 */
public class ModifierUserController implements Initializable {

    @FXML
    private JFXButton modifier;
    @FXML
    private JFXButton annuler;
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
    private JFXDatePicker date;
    @FXML
    private JFXComboBox<String> type;
    @FXML
    private ToggleGroup sexe;
    @FXML
    private Button Bt_importer;
    @FXML
    private ImageView photo;
    @FXML
    private ImageView close;

    public static AnchorPane blur;
    public static User userSelected;
    private FileInputStream photoProfil = null;
    private User user;
    UserService us = new UserService();

    ObservableList<String> typeList = FXCollections.observableArrayList("Administrateur", "Client", "Artisan");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
        if (userSelected != null) {
            blur.setEffect(new GaussianBlur(5));
            user = userSelected;

            type.setValue(userSelected.getType().toString());
            type.setItems(typeList);

            nom.setText(userSelected.getNom());
            prenom.setText(userSelected.getPrenom());
            adresse.setText(userSelected.getAdresse());
            tel.setText(userSelected.getTel());
            if (userSelected.getSexe().equals("Femelle")) {
                sexe.selectToggle(sexe.getToggles().get(1));
            }
            username.setText(userSelected.getUserName());
            email.setText(userSelected.getEmail());
            date.setValue(userSelected.getDateNaissance());

            photo.setImage(us.getPhoto(userSelected.getId()));

        }

    }

    @FXML
    private void modifierClicked(ActionEvent event) {
        if (verif()) {
            String code = Utils.generateCode(6);
            String sexe = ((RadioButton) this.sexe.getSelectedToggle()).getText();

            userSelected.setUserName(username.getText());
            userSelected.setNom(nom.getText());
            userSelected.setPrenom(prenom.getText());
            userSelected.setEmail(email.getText());
            userSelected.setAdresse(adresse.getText());
            userSelected.setDateNaissance(date.getValue());
            userSelected.setSexe(sexe);

            userSelected.setPhoto(us.getPhotoUser(userSelected.getId()));
            userSelected.setPhoto(user.getPhoto());
            
            if (photoProfil != null) {
                userSelected.setPhoto(photoProfil);
                photoProfil=null;
            }

            if (us.modifierUser(userSelected)) {

                Utils.showTrayNotification(NotificationType.CUSTOM, "Informations modifiées avec succès", null, userSelected.getUserName()
                        + "\n" + userSelected.getEmail(), photo.getImage(), 6000);

                Utils.showAlert(Alert.AlertType.INFORMATION, "Modification des données", "Vos informations sont mises à jour",
                        "Nom d'utilisateur : " + userSelected.getUserName()
                        + "\nEmail : " + userSelected.getEmail());

                blur.setEffect(null);
                Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
                s.close();
            }
        }
    }

    @FXML
    private void annulerClicked(ActionEvent event) {
        Stage s = (Stage) annuler.getScene().getWindow();
        blur.setEffect(null);
        s.close();
    }

    @FXML
    private void onSetAction_importer(ActionEvent event) throws IOException {
        FileChooser file = new FileChooser(); //pour choisir la photo
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp"));
        file.setTitle("Choisir une photo de profil");

        File selected_photo = file.showOpenDialog((Stage) annuler.getScene().getWindow());
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
    private void closeClicked(MouseEvent event) {
        Stage s = (Stage) annuler.getScene().getWindow();
        blur.setEffect(null);
        s.close();

    }

    private boolean verif() {

        if (username.getText().isEmpty() || email.getText().isEmpty() || nom.getText().isEmpty()
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

            if (us.verifColumn("username", username.getText()) && !username.getText().equals(user.getUserName())) {// if existing in table user
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Nom d'utilisateur déjà existant, veuillez choisir un autre");
                username.requestFocus();
                username.selectEnd();
                return false;
            }

            if (us.verifColumn("email", email.getText()) && !email.getText().equals(user.getEmail())) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Email déjà existant, veuillez choisir un autre");
                email.requestFocus();
                email.selectEnd();
                return false;
            }
        }
        return true;
    }
}
