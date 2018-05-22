/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Services.BoutiqueService;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 * FXML Controller class
 *
 * @author Azza
 */
public class ModifierBoutiqueController implements Initializable {

    @FXML
    private JFXTextField nomBoutique;
    @FXML
    private JFXTextField adresseBoutique;
    private JFXTextField idBoutique;
    @FXML
    private JFXButton photo;
    
    @FXML
    private JFXButton retour;
    
    @FXML
    private ImageView photoB;

    private BoutiqueService bs = new BoutiqueService();
    private Boutique b;
    private String photoBoutique= null;
    public static Boutique boutiqueSelected=null;
    public static UneBoutiqueArtisanController bc;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (boutiqueSelected!=null){
            b=boutiqueSelected;
            nomBoutique.setText(b.getNom());
            adresseBoutique.setText(b.getAdresse());
            photoB.setImage(bs.getPhoto(b.getId()));
        }
        
    }    

    @FXML
    private void modifierBoutique(ActionEvent event) {
        boutiqueSelected.setNom(nomBoutique.getText());
        boutiqueSelected.setAdresse(adresseBoutique.getText());
//        boutiqueSelected.setPhoto(bs.getPhotoBoutique(boutiqueSelected.getId()));
        boutiqueSelected.setPhoto(b.getPhoto());
//        if(photoBoutique!=null){
//           boutiqueSelected.setPhoto(photoBoutique);
//           photoBoutique=null;
//        }
       
        bs.modifierBoutique(boutiqueSelected);
         bc.setValues(b);
         Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
        
        
    }
    @FXML
    private void annuler(ActionEvent event) throws IOException {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

    @FXML
    private void changerPhoto(ActionEvent event) throws IOException {
         FileChooser file = new FileChooser(); //pour choisir la photo
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp"));
        file.setTitle("Choisir une photo du produit");

        File selected_photo = file.showOpenDialog((Stage) retour.getScene().getWindow());
        if (selected_photo != null) {
            if ((selected_photo.length() / 1024) / 1024 < 4.0) {
                 photoBoutique = selected_photo.getAbsolutePath();
                BufferedImage bufferedImage = ImageIO.read(selected_photo);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                photoB.setImage(image);
            } else {
                Utils.showAlert(Alert.AlertType.ERROR, "Erreur", "Taile trop grande !", "Veuillez choisir une photo de profil avec une taille < 4 Mo");
            }
        }
    }
    
}
