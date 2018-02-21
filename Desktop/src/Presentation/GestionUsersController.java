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
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
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
    private JFXComboBox<String> type;
    @FXML
    private ToggleGroup sexe;
    @FXML
    private Button Bt_importer;
    @FXML
    private ImageView photo;
    @FXML
    private Label labelCompte;

    /**
     * ***************************************************************
     */
    GridPane gridPane = new GridPane();
    public static List<User> list;
    public static UserController uc;
    public static User userSelected;

    private FileInputStream photoProfil;

    UserService us = new UserService();

    ObservableList<String> etatList = FXCollections.observableArrayList("En attente", "Active", "Déconnecté", "Supprimé");
    ObservableList<String> typeList = FXCollections.observableArrayList("Administrateur", "Client", "Artisan");
    private User user;

    /**
     * Initializes the controller class.
     */
    private void addToGrid(List<User> list, GridPane gridPane) {
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
            if (list.size() > 0) {
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
            if (null == userSelected.getEtat()) {
                etat.setValue("Active");
            } else {
                switch (userSelected.getEtat()) {
                    case Pending:
                        etat.setValue("En attente");
                        break;
                    case Disconnected:
                        etat.setValue("Déconnecté");
                        break;
                    case Deleted:
                        etat.setValue("Supprimé");
                        break;
                    default:
                        etat.setValue("Active");
                        break;
                }
            }

            type.setValue(userSelected.getType().toString());

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
            Image img = us.getPhoto(userSelected.getId());
            if (img != null) {
                photo.setImage(img);
            } else {
                photo.setImage(new Image("Images/user.png"));
            }
            date.setValue(userSelected.getDateNaissance());
        }
    }

    public void disable(boolean b) {
        username.setDisable(true);
        nom.setDisable(b);
        prenom.setDisable(b);
        email.setDisable(b);
        adresse.setDisable(b);
        tel.setDisable(b);
        etat.setDisable(b);
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
        modifier.setDisable(false);
        date.setEditable(true);
    }

    public void voir() {
        disable(false);
        username.setEditable(false);
        nom.setEditable(false);
        prenom.setEditable(false);
        email.setEditable(false);
        adresse.setEditable(false);
        tel.setEditable(false);
        modifier.setDisable(false);
        date.setEditable(false);
        modifier.setDisable(true);
        Bt_importer.setVisible(false);
    }

    private void updateItems(String text) {

        if (!text.isEmpty()) {
            gridPane.getChildren().clear();
            list = list.stream().filter(u -> u.getPrenom().toLowerCase().contains(text.toLowerCase())
                    || u.getNom().toLowerCase().contains(text.toLowerCase())
                    || u.getType().toString().toLowerCase().startsWith(text.toLowerCase())).collect(Collectors.toList());
            addToGrid(list, gridPane);
        } else {
            list = us.getUsers();
            gridPane.getChildren().clear();
            addToGrid(list, gridPane);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        /**
         * **************** initialize modifier ****************
         */
        
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
        type.setItems(typeList);
        etat.setItems(etatList);
        disable(true);
        modifier.setDisable(false);

        /**
         * ************************************************************************
         */
        filter.textProperty().addListener((observable, oldValue, newValue) -> updateItems(newValue));

        list = us.getUsers();
        UserController.contenu = list;
        UserController.guc = this;

        addToGrid(list, gridPane);

        gridPane.setHgap(45);
        gridPane.setVgap(20);

        users.setPannable(true);
        users.setContent(gridPane);

        users.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() == users.getVmax()) {
                    System.out.println("AT BOTTOM");
                    // load more items
//                    List<Parent> list2 = parents;
//                    for (int i = 0; i < list.size(); i++) {
//                        try {
//                            FXMLLoader loader = new FXMLLoader(getClass().getResource("User.fxml"));
//                            Parent root = loader.load();
//                            list2.add(root);
//                        } catch (IOException ex) {
//                            Logger.getLogger(TestController.class.getName()).log(Level.SEVERE, null, ex);
//                        }
//                    }
//                    addToGrid(parents, gridPane);
                }
            }
        }
        );
    }

    @FXML
    private void modifierClicked(ActionEvent event) {
        user = userSelected;
        if (verfier()) {
            //String code = Utils.generateCode(6);
            String sexe = ((RadioButton) this.sexe.getSelectedToggle()).getText();
            EtatUser etatU = null;
            switch (etat.getValue()) {
                case "En attente":
                    etatU = EtatUser.Pending;
                    break;
                case "Active":
                    etatU = EtatUser.Active;
                    break;
                case "Déconnecté":
                    etatU = EtatUser.Disconnected;
                    break;
                case "Supprimé":
                    etatU = EtatUser.Deleted;
                    break;
            }

            userSelected.setType(TypeUser.valueOf(type.getValue()));
            userSelected.setEtat(etatU);
            userSelected.setNom(nom.getText());
            userSelected.setPrenom(prenom.getText());
            userSelected.setEmail(email.getText());
            userSelected.setAdresse(adresse.getText());
            userSelected.setDateNaissance(date.getValue());
            userSelected.setTel(tel.getText());

            userSelected.setSexe(sexe);
            userSelected.setPhoto(us.getPhotoUser(userSelected.getId()));
            if (photoProfil != null) {
                userSelected.setPhoto(photoProfil);
            }

            date.setValue(userSelected.getDateNaissance());

            if (us.modifierUser(userSelected)) {

                Utils.showTrayNotification(NotificationType.CUSTOM, "Utilisateur modifié avec succès", null, userSelected.getUserName()
                        + "\n" + userSelected.getEmail(), photo.getImage(), 6000);

                list.set(list.indexOf(user), userSelected);
                uc.setValues(userSelected);
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
            if ((selected_photo.length() / 1024) / 1024 < 4.0) {
                String path = selected_photo.getAbsolutePath();
                BufferedImage bufferedImage = ImageIO.read(selected_photo);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                photo.setImage(image);

                File img = new File(path);
                photoProfil = new FileInputStream(img);
            } else {
                Utils.showAlert(Alert.AlertType.ERROR, "Erreur", "Taile trop grande !", "Veuillez choisir une photo de profil avec une taille < 4 Mo");
            }
        }
    }

    private boolean verfier() {
        if (!userSelected.getEmail().equals(email.getText()) && us.verifColumn("email", email.getText())) {
            Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Email déjà existant, veuillez choisir un autre");
            email.requestFocus();
            return false;
        } else if (date.getValue() == null) {
            Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien renseigner votre date de naissance.\nExemple : 1995-11-25");
            date.requestFocus();
            return false;
        }

        return true;
    }

}
