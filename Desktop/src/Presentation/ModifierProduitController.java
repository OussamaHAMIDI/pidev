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
import com.jfoenix.controls.JFXButton;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDateTime;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
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
    private Button ajouterPhoto;
    @FXML
    private ImageView photo;
    
    private FileInputStream photoProduit= null;
             ProduitService ps=new ProduitService();
    public static ProduitController bc;
    private Produit p;
    public static Produit selectedProduit = new Produit(106);
    @FXML
    private JFXButton Supprimer;
    @FXML
    private JFXButton retour;
    /**
     * Initializes the controller class.
     * @param url
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

          if (selectedProduit!=null){
            p=selectedProduit;
            
        reference.setText(p.getReference());
        libelle.setText(p.getLibelle());
        description.setText(p.getDescription());
        prix.setText(String.valueOf(p.getPrix()));
        taille.setText(p.getTaille());
        couleur.setText(p.getCouleur());
        texture.setText(p.getTexture());
        poids.setText(String.valueOf(p.getPoids()));
        if (p.getPhoto()!=null) {
            photo.setImage(ps.getPhoto(p.getId()));
        }
        }
       
       // selectedProduit=ps.chercherProduitParID(selectedProduit.getId());
        
      
    }    
    @FXML
    public void modifierProduit (ActionEvent event)
    {
        ProduitService ps=new ProduitService();
        Boutique b = new Boutique();
        if(photoProduit!=null)
        {
        p.setPhoto(photoProduit);
        }
        if (controleDeSaisi()) {
            if (taille.getText()=="") {taille.setText("");}
            if (couleur.getText()=="") {couleur.setText("");}
            if (texture.getText()=="") {texture.setText("");}
            if (poids.getText().equals("")) {poids.setText("0.0");}
            ps.modifierProduit(new Produit(p.getId(),reference.getText(), libelle.getText(), description.getText(),Float.parseFloat(prix.getText()), taille.getText(), couleur.getText(), texture.getText(), Float.parseFloat(poids.getText()), b, LocalDateTime.MAX, p.getPhoto()));
            Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
            s.close();
        }
    }
    @FXML
    void uploadPhoto(ActionEvent event) throws IOException{
      FileChooser file = new FileChooser(); //pour choisir la photo
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp"));
        file.setTitle("Choisir une photo du produit");

        File selected_photo = file.showOpenDialog((Stage) prix.getScene().getWindow());
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
    @FXML
    public void supprimerProduit (ActionEvent event)
    {
      //  ProduitService ps=new ProduitService();
        ps.supprimerProduit(p.getId());
    }

    @FXML
    private void annuler(ActionEvent event) {
         Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
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
