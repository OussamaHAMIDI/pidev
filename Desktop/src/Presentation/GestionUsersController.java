/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.User;
import static Presentation.UserController.guc;
import Services.UserService;
import Utils.Enumerations.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import javax.imageio.ImageIO;
import tray.notification.NotificationType;

/**
 * FXML Controller class
 *
 * @author Hamdi
 */
public class GestionUsersController implements Initializable {

    @FXML
    private JFXTextField filter;
    @FXML
    private ScrollPane users;
    @FXML
    private JFXButton modifier;
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
    private JFXComboBox<String> etat;
    @FXML
    private JFXTextField type;
    @FXML
    private ToggleGroup sexe;
    @FXML
    private Button Bt_importer;
    @FXML
    private ImageView photo;
    @FXML
    private Label labelCompte;
    @FXML
    private JFXButton confirmer;
    @FXML
    private Tab tabPermis;
    @FXML
    private ImageView photoPermis;

    
    public static GridPane gridPane = null ;

    public static List<User> list;
    
    public static UserController uc;
    public static User userSelected;

    private FileInputStream photoProfil = null;

    UserService us = new UserService();

    ObservableList<String> etatList = FXCollections.observableArrayList("En attente", "Active", "Déconnecté", "Supprimé", "Connecté");
    private User user;

    public void addToGrid(List<User> list) {
        int totalItems = list.size();
        int nbrItems = gridPane.getChildren().size();
        int nbrRows = (nbrItems % 2 == 0) ? nbrItems / 2 : (nbrItems + 1) / 2;
        List<Parent> parents = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            try {
                UserController.index = i;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("User.fxml"));
                Parent root = loader.load();
                parents.add(root);
            } catch (IOException ex) {
                Logger.getLogger(GestionUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (nbrItems % 2 == 1) {// impaire
            if (parents.size() > 0) {
                gridPane.add(parents.get(0), 1, nbrRows - 1);
                parents.remove(0);
            }
        }
        //paire
        for (int ligne = nbrRows; ligne < nbrRows + totalItems; ligne++) {
            //for (int col = 0; col < 2; col++) {
            if (list.size() > 0) {
                gridPane.add(parents.get(0), 0, ligne);
                parents.remove(0);
            } else {
                return;
            }
            // }
        }

    }

    public void setValues(User userSelected, String label) {
        if (userSelected != null) {
            labelCompte.setText(label);

            switch (userSelected.getEtat()) {
                case Pending:
                    etat.setValue("En attente");
                    break;
                case Disconnected:
                    etat.setValue("Déconnecté");
                    break;
                case Connected:
                    etat.setValue("Connecté");
                    break;
                case Deleted:
                    etat.setValue("Supprimé");
                    break;
                default:
                    etat.setValue("Active");
                    break;
            }
            if (userSelected.getType() == TypeUser.Artisan & userSelected.getEtat()!= EtatUser.Deleted) {
                etat.setDisable(true);
            } else {
                etat.setDisable(false);
            }

            type.setText(userSelected.getType().toString());

            nom.setText(userSelected.getNom());
            prenom.setText(userSelected.getPrenom());
            adresse.setText(userSelected.getAdresse());
            tel.setText(userSelected.getTel());
            
            if (userSelected.getSexe().equals("Male")) {
                sexe.selectToggle(sexe.getToggles().get(0));
            } else {
                sexe.selectToggle(sexe.getToggles().get(1));
            }
            username.setText(userSelected.getUserName());
            email.setText(userSelected.getEmail());
            date.setValue(userSelected.getDateNaissance());

            photo.setImage(us.getPhoto(userSelected.getId()));

            // pour client : Pending -> Active (passage par code de vérification envoyé par mail
            // Pour l'artisan !! Pending -> Active (passage par confirmation de l'admin)
            if (userSelected.getType() == TypeUser.Artisan) {
                photoPermis.setImage(us.getPhotoArtisan(userSelected.getId()));
                if (userSelected.getEtat() == EtatUser.Pending) {
                    tabPermis.setDisable(false);
                    confirmer.setDisable(false);
                } else if (userSelected.getEtat() != EtatUser.Deleted) {
                    tabPermis.setDisable(false);
                    confirmer.setDisable(true);
                }
            } else {
                tabPermis.setDisable(true);
            }
            if (AccueilController.userConnected != null) {
                if (userSelected.getType() == TypeUser.Administrateur && userSelected.getId() == AccueilController.userConnected.getId()) {
                    etat.setDisable(true);
                }
            }

        }
    }

    public void disable(boolean b) {
        username.setDisable(true);
        nom.setDisable(b);
        prenom.setDisable(b);
        adresse.setDisable(b);
        tel.setDisable(b);
        type.setDisable(b);
        modifier.setDisable(b);
        Bt_importer.setDisable(b);
        Bt_importer.setVisible(true);
        date.setDisable(b);
        username.setEditable(true);
        nom.setEditable(true);
        prenom.setEditable(true);
        email.setEditable(true);
        adresse.setEditable(true);
        tel.setEditable(true);
        date.setEditable(true);
    }

    public void voir() {
        disable(false);
        username.setEditable(false);
        nom.setEditable(false);
        prenom.setEditable(false);
        adresse.setEditable(false);
        tel.setEditable(false);
        modifier.setDisable(false);
        date.setEditable(false);
        date.setDisable(true);
        modifier.setDisable(true);
        Bt_importer.setVisible(false);
    }

    public void updateItems(String text) {

        if (!text.isEmpty()) {
            list = list.stream().filter(u -> u.getPrenom().toLowerCase().contains(text.toLowerCase())
                    || u.getNom().toLowerCase().contains(text.toLowerCase())
                    || u.getType().toString().toLowerCase().startsWith(text.toLowerCase())).collect(Collectors.toList());
            gridPane.getChildren().clear();
            addToGrid(list);
        } else {
            list = us.getUsers();
            gridPane.getChildren().clear();
            addToGrid(list);
        }
    }

    private boolean verif() {

        if (nom.getText().isEmpty() || prenom.getText().isEmpty() || tel.getText().isEmpty() || adresse.getText().isEmpty() || date.getValue() == null) {
            Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien renseigner tous les champs !");
            return false;
        } else {

            if (!Pattern.matches("([a-zA-Z]+)|([a-zA-Z]+ [a-zA-Z]+)|([a-zA-Z]+ [a-zA-Z]+ [a-zA-Z]+)", nom.getText())) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez le nom ! ");
                nom.requestFocus();
                nom.selectEnd();
                return false;
            }

            if (!Pattern.matches("([a-zA-Z]+)|([a-zA-Z]+ [a-zA-Z]+)|([a-zA-Z]+ [a-zA-Z]+ [a-zA-Z]+)", prenom.getText())) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez le prénom ! ");
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
            if (date.getValue().getYear() > 2000) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Age doit être > 18 ans !");
                date.requestFocus();
                return false;
            }
            if (date.getValue().toString().length() != 10) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien renseigner la date de naissance.\nExemple : 1995-11-25");

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
        }
        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        gridPane = new GridPane();
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

        etat.setItems(etatList);
        disable(true);
        etat.setDisable(true);
        tabPermis.setDisable(true);
        
        filter.textProperty().addListener((observable, oldValue, newValue) -> updateItems(newValue));

        list = us.getUsers();
        UserController.contenu = list;
        UserController.guc = this;

        addToGrid(list);

        gridPane.setHgap(45);
        gridPane.setVgap(20);

        users.setPannable(true);
        users.setContent(gridPane);
    }

    @FXML
    private void modifierClicked(ActionEvent event) {
        user = userSelected;
        if (verif()) {
            String sexe = ((RadioButton) this.sexe.getSelectedToggle()).getText();
            EtatUser etatU = null;
            switch (etat.getValue()) {
                case "En attente":
                    etatU = EtatUser.Pending;
                    if (userSelected.getEtat() != EtatUser.Pending && userSelected.getEtat() != EtatUser.Deleted) {
                        Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Cet utilisateur est déjà confirmé,"
                                + " cette action n'est pas autorisée ! ");
                        return;
                    }
                    break;
                case "Active":
                    etatU = EtatUser.Active;
                    if (userSelected.getEtat() == EtatUser.Pending && userSelected.getType() != TypeUser.Artisan) {
                        Utils.showAlert(Alert.AlertType.ERROR, "Activation", "Activation", "Cet utilisateur est en attente,"
                                + "\nIl n'aura plus besoin de son code de vérification lors de sa premiere connexion.");
                        return;
                    }
                    break;
                case "Déconnecté":
                    etatU = EtatUser.Disconnected;
                    if (userSelected.getEtat() != EtatUser.Disconnected) {
                        Utils.showAlert(Alert.AlertType.ERROR, "Action n'est pas autorisée", "Action n'est pas autorisée",
                                "Vous ne pouvez pas modifié l'état d'un utilisateur à 'Déconnecté' s'il ne l'est pas déjà!");
                        return;
                    }
                    break;
                case "Connecté":
                    etatU = EtatUser.Connected;
                    if (userSelected.getEtat() != EtatUser.Connected) {
                        Utils.showAlert(Alert.AlertType.ERROR, "Action n'est pas autorisée", "Action n'est pas autorisée",
                                "Vous ne pouvez pas modifié l'état d'un utilisateur à 'Connecté' s'il ne l'est pas déjà!");
                        return;
                    }
                    break;
                case "Supprimé":
                    etatU = EtatUser.Deleted;
                    if (userSelected.getEtat() == EtatUser.Connected) {
                        Utils.showAlert(Alert.AlertType.ERROR, "Action n'est pas autorisée", "Action n'est pas autorisée",
                                "Vous ne pouvez pas supprimé un utilisateur connecté !");
                        return;
                    }
                    break;
            }
            userSelected.setEtat(etatU);
            userSelected.setNom(nom.getText());
            userSelected.setPrenom(prenom.getText());
            userSelected.setEmail(email.getText());
            userSelected.setAdresse(adresse.getText());
            userSelected.setDateNaissance(date.getValue());
            userSelected.setTel(tel.getText());
            userSelected.setSexe(sexe);

            userSelected.setPhoto(us.getPhotoUser(userSelected.getId()));
            userSelected.setPhoto(user.getPhoto());

            if (photoProfil != null) {
                // System.out.println("g choisis une nouvelle photo ");
                userSelected.setPhoto(photoProfil);
                photoProfil = null;
            }

            if (us.modifierUser(userSelected)) {
                Utils.showTrayNotification(NotificationType.CUSTOM, "Utilisateur modifié avec succès", null, userSelected.getUserName()
                        + "\n" + userSelected.getEmail(), photo.getImage(), 6000);

                list.set(list.indexOf(user), userSelected);
                uc.setValues(userSelected);
                disable(true);
                modifier.setDisable(true);
                Bt_importer.setVisible(false);
            }
        }
    }

    @FXML
    private void onSetAction_importer(ActionEvent event) throws IOException {
        FileChooser file = new FileChooser(); //pour choisir la photo
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp"));
        file.setTitle("Choisir une photo de profil");

        File selected_photo = file.showOpenDialog((Stage) modifier.getScene().getWindow());
        if (selected_photo != null) {
            if ((selected_photo.length() / 1024) / 1024 < 2.0) {
                String path = selected_photo.getAbsolutePath();
                BufferedImage bufferedImage = ImageIO.read(selected_photo);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                photo.setImage(image);

                File img = new File(path);
                photoProfil = new FileInputStream(img);
            } else {
                Utils.showAlert(Alert.AlertType.ERROR, "Erreur", "Taille trop grande !", "Veuillez choisir une photo de profil avec une taille < 2 Mo");
            }
        }
    }

    @FXML
    private void ajouterUser(MouseEvent event) {
        InscriptionAdminController.guc = this;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InscriptionAdmin.fxml"));
        Stage s = Utils.getAnotherStage(loader, "Ajout d'utilisateur en tant qu'administrateur");
        s.initStyle(StageStyle.UNDECORATED);
        s.show();
    }

    @FXML
    private void confirmerClicked(ActionEvent event) {
        Alert alert = Utils.getAlert(Alert.AlertType.CONFIRMATION, "Confirmation", null,
                "Voulez-vous vraiment confirmé cet artisan ?");
        alert.show();
        alert.resultProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == ButtonType.OK) {
                us.modifierEtatUser(userSelected.getId(), EtatUser.Active);
                etat.setValue("Active");
                etat.setDisable(true);
                confirmer.setDisable(true);

                String code = Utils.generateCode(6);
                us.changerToken(code, email.getText());
                Utils.sendMail(email.getText(), code);
                Utils.showTrayNotification(NotificationType.CUSTOM, "Confirmation", null, " Un code de vérification a été envoyé à :\n"
                        + email.getText(), photo.getImage(), 6000);
            }
        });

    }

}
