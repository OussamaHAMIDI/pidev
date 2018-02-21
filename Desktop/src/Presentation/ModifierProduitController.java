/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Produit;
import Services.ProduitService;
import Utils.Utils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author oussamahamidi
 */
public class ModifierProduitController implements Initializable {
    @FXML
    private TextField reference;
    @FXML
    private TextField libelle;
    @FXML
    private TextArea description;
    @FXML
    private TextField prix;
    @FXML
    private TextField taille;
    @FXML
    private TextField couleur;
    @FXML
    private TextField texture;
    @FXML
    private TextField poids;
    @FXML
    private Button modifier;
    @FXML
    private Button annuler;
    @FXML
    private Button supprimer;
    @FXML
    private Button ajouterPhoto;
    @FXML
    private ImageView photo;
    
    private FileInputStream photoProduit= null;
    
    public static Produit selectedProduit;
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reference.setText(selectedProduit.getReference());
        libelle.setText(selectedProduit.getLibelle());
        description.setText(selectedProduit.getDescription());
        prix.setText(String.valueOf(selectedProduit.getPrix()));
        taille.setText(selectedProduit.getTaille());
        couleur.setText(selectedProduit.getCouleur());
        texture.setText(selectedProduit.getTexture());
        poids.setText(String.valueOf(selectedProduit.getPoids()));
        photo.setImage(new Image(selectedProduit.getPhoto()));
    }    
    @FXML
    public void modifierProduit (ActionEvent event)
    {
        ProduitService ps=new ProduitService();
        Boutique b = new Boutique();
        ps.modifierProduit(new Produit(reference.getText(), libelle.getText(), description.getText(),Float.parseFloat(prix.getText()), taille.getText(), couleur.getText(), texture.getText(), Float.parseFloat(poids.getText()), b, LocalDateTime.MAX, photoProduit));
    }
    @FXML
    void uploadPhoto(ActionEvent event) throws IOException{
      FileChooser file = new FileChooser(); //pour choisir la photo
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp"));
        file.setTitle("Choisir une photo du produit");

        File selected_photo = file.showOpenDialog((Stage) annuler.getScene().getWindow());
        if (selected_photo != null) {
            if ((selected_photo.length() / 1024) / 1024 < 4.0) {
                String path = selected_photo.getAbsolutePath();
                BufferedImage bufferedImage = ImageIO.read(selected_photo);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                photo.setImage(image);

                File img = new File(path);
                photoProduit = new FileInputStream(img);
            } else {
                Utils.showAlert(Alert.AlertType.ERROR, "Erreur", "Taile trop grande !", "Veuillez choisir une photo de profil avec une taille < 4 Mo");
            }
        }

    }

    /**
     *
     * @param event
     */
    public void supprimerProduit (ActionEvent event)
    {
        ProduitService ps=new ProduitService();
        ps.supprimerProduit(selectedProduit.getId());
    }
}
