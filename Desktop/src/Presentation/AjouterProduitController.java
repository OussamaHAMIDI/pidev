/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Produit;
import Services.ProduitService;
import Utils.SmsSender;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author oussamahamidi
 */
public class AjouterProduitController implements Initializable {

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
    private Button ajouterPhoto;
    @FXML
    private ImageView photo;
    private FileInputStream photoProduit = null;
    private JFXButton modifier;
    @FXML
    private JFXButton ajouter;
    @FXML
    private JFXButton retour;

    private Produit pro;
    static public int index;
    static public List<Produit> contenu;
    static public MenuProduitsController mc;
    public static boolean voir = false;
    public static Produit voirProd;

    /**
     * Initializes the controller class.
     *
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
         if(voir==true)
            {
            ProduitService ps = new ProduitService();
            reference.setText(voirProd.getReference());
            libelle.setText(voirProd.getLibelle());
            description.setText(voirProd.getDescription());
            prix.setText(String.valueOf(voirProd.getPrix()));
            taille.setText(voirProd.getTaille());
            couleur.setText(voirProd.getCouleur());
            texture.setText(voirProd.getTexture());
            poids.setText(String.valueOf(voirProd.getPoids()));
            if (voirProd.getPhoto() != null) {
                photo.setImage(ps.getPhoto(voirProd.getId()));
            }
            reference.setEditable(true);
            libelle.setEditable(true);
            description.setEditable(true);
            prix.setEditable(true);
            taille.setEditable(true);
            couleur.setEditable(true);
            texture.setEditable(true);
            poids.setEditable(true);
            ajouterPhoto.setVisible(false);
            ajouter.setVisible(false);
            }
            else
            {

             reference.setEditable(false);
            libelle.setEditable(false);
            description.setEditable(false);
            prix.setEditable(false);
            taille.setEditable(false);
            couleur.setEditable(false);
            texture.setEditable(false);
            poids.setEditable(false);

            ajouter.setVisible(true);
            ajouterPhoto.setVisible(true);
            }
    }

    @FXML
    public void ajouterProduit(ActionEvent event) throws IOException {
        ProduitService ps = new ProduitService();
        Boutique b = new Boutique();
        if (controleDeSaisi()) {
            if (taille.getText().isEmpty()) {
                taille.setText("");
            }
            if (couleur.getText().isEmpty()) {
                couleur.setText("");
            }
            if (texture.getText().isEmpty()) {
                texture.setText("");
            }
            if (poids.getText().isEmpty()) {
                poids.setText("0.0");
            }
            String t = poids.getText();
            Produit p = new Produit(reference.getText(), libelle.getText(), description.getText(), Float.parseFloat(prix.getText()), taille.getText(), couleur.getText(), texture.getText(), 
                    Float.parseFloat(poids.getText()), b, LocalDateTime.MAX, photoProduit);
            ps.ajouterProduit(p);
//            SmsSender ss = new SmsSender();
//            ss.sendSms("ajout%20effectué", "54476969");

            MenuProduitsController.list.add(p);
            mc.updateItems("");
//            List<Produit> tmp = new ArrayList<Produit>();
//            tmp.add(p);
//            mc.addToGrid(tmp);
            Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
            s.close();

        }

    }

    @FXML
    void uploadPhoto(ActionEvent event) throws IOException {
        FileChooser file = new FileChooser(); //pour choisir la photo
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp"));
        file.setTitle("Choisir une photo du produit");

        File selected_photo = file.showOpenDialog((Stage) ajouter.getScene().getWindow());
        if (selected_photo != null) {
            if ((selected_photo.length() / 1024) / 1024 < 2.0) {
                String path = selected_photo.getAbsolutePath();
                BufferedImage bufferedImage = ImageIO.read(selected_photo);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                photo.setImage(image);

                File img = new File(path);
                photoProduit = new FileInputStream(img);
            } else {
                Utils.showAlert(Alert.AlertType.ERROR, "Erreur", "Taile trop grande !", "Veuillez choisir une photo de profil avec une taille < 2 Mo");
            }
        }

    }

    @FXML
    private void annuler(ActionEvent event) {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
                voir=false;
    }

    @FXML
    private void payerPanier(MouseEvent event) {
    }

    private boolean controleDeSaisi() {

        if (reference.getText().isEmpty() || libelle.getText().isEmpty() || description.getText().isEmpty()
                || prix.getText().isEmpty()) {
            Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Veuillez bien remplir tous les champs !");
            return false;
        } else {

            if (!Pattern.matches("^[a-zA-Z0-9]*$", reference.getText())) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez la reference ! ");
                reference.requestFocus();
                reference.selectEnd();
                return false;
            }

            if (!Pattern.matches("^[\\p{L} .'-]+$", libelle.getText())) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez le libelle du produit ! ");
                libelle.requestFocus();
                libelle.selectEnd();
                return false;
            }

            if (!Pattern.matches("^[A-Za-z0-9 _]*[A-Za-z0-9][A-Za-z0-9 _]*$", description.getText())) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez la description du produit ! ");
                description.requestFocus();
                description.selectEnd();
                return false;
            }

            if (!Pattern.matches("\\d*", prix.getText())) {
                Utils.showAlert(Alert.AlertType.ERROR, "Données erronés", "Verifier les données", "Vérifiez le prix du produit !");
                prix.requestFocus();
                prix.selectEnd();
                return false;
            }
        }
        return true;
    }
}
