/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

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
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Hamdi
 */
public class AddUserController implements Initializable {

    @FXML
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
    private ImageView close;

    public AnchorPane blur;

    ObservableList<String> etatList = FXCollections.observableArrayList("En attente", "Active", "Connecté");
    ObservableList<String> typeList = FXCollections.observableArrayList("Administrateur", "Client", "Artisan");
    private FileInputStream photoProfil = null;
  

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        close.setCursor(Cursor.HAND);

        type.setValue("Client");
        type.setItems(typeList);
        etat.setValue("Active");
        etat.setItems(etatList);
        Utils.verifTextField(tel, "[0-9]*", "Seuls les chiffres sont permis !");
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
    private void ajouterClicked(ActionEvent event) {

    }

    @FXML
    private void closeClicked(MouseEvent event) {
        Stage s = (Stage) annuler.getScene().getWindow();
        blur.setEffect(null);
        s.close();
    }

}
