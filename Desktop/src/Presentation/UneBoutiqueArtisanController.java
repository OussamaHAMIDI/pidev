/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import static Presentation.MenuBoutiqueController.boutiqueSelected;
import Services.BoutiqueService;
import Utils.Utils;
import Utils.Enumerations.*;
import com.jfoenix.controls.JFXButton;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Azza
 */
public class UneBoutiqueArtisanController implements Initializable {

    private Boutique bou;
    static public int index;
    static public List<Boutique> contenu;
    static public MenuBoutiqueController mc;
    @FXML
    private AnchorPane boutique;
    @FXML
    private Label adresseB;
    @FXML
    private Label nomB;
    @FXML
    private Label dateB;
    @FXML
    private JFXButton modifierB;
    @FXML
    private JFXButton partagerB;
    @FXML
    private Label userB;
    @FXML
    private JFXButton supprimer;
    @FXML
    private Circle circle;

    BoutiqueService bs = new BoutiqueService();
    @FXML
    private Pane gris;
    public boolean testBou=true;
    @FXML
    private FontAwesomeIconView userP;
    
    public AnchorPane getThis() {
        return boutique;
    }

    public void setValues(Boutique b) {
        dateB.setText(b.getDateCreation().toString().replace("T", " "));
        nomB.setText(b.getNom());
        adresseB.setText(b.getAdresse());
        circle.setStroke(Color.SEAGREEN);
        circle.setFill(Color.SNOW);
        circle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        try{
             System.out.println((Utils.dir +"images/"+ b.getPhoto()).trim());
             Image img = new Image((Utils.dir +"images/"+ b.getPhoto()).trim());
             ImagePattern imgp = new ImagePattern(img);
             circle.setFill(imgp);
              System.out.println("");
             //circle.setFill(new ImagePattern(new Image(Utils.dir + p.getPhoto())));
       }
       catch(Exception e )
       {
           System.out.println(e.getMessage());
       }
      
//        circle.setFill(new ImagePattern(new Image("Images/produit_icon.png")));
//        Image img = bs.getPhoto(b.getId());
//        if (img != null) {
//            circle.setFill(new ImagePattern(img));
//        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (AccueilController.userConnected != null) {
            if (AccueilController.userConnected.getType() == TypeUser.Administrateur) {
                userB.setVisible(true);
                userP.setVisible(true);
                gris.setVisible(true);
                supprimer.setVisible(true);
                modifierB.setVisible(true);
                partagerB.setVisible(false);           
            } else if (AccueilController.userConnected.getType() == TypeUser.Artisan) {
                 userB.setVisible(false);
                userP.setVisible(false);

                gris.setVisible(true);
                supprimer.setVisible(true);
                modifierB.setVisible(true);
                partagerB.setVisible(true);
            } else if (AccueilController.userConnected.getType() == TypeUser.Client) {
                 userB.setVisible(false);
                userP.setVisible(false);

                gris.setVisible(false);
            }
        } else {//visiteur
             userB.setVisible(false);
                userP.setVisible(false);
            gris.setVisible(false);
        }
        if (!testBou) {
            bou = MenuBoutiqueController.list.get(index);
        }
        else {
            bou=AffichageProdController.b.get(index);
        }
        setValues(bou);
    }

    @FXML
    private void supprimerBoutique(ActionEvent event) {
        Alert alert = Utils.getAlert(Alert.AlertType.CONFIRMATION, "Suppression", null,
                "Voulez-vous vraiment supprimer cette boutique ?");
        alert.show();
        alert.resultProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == ButtonType.OK) {
                MenuBoutiqueController.list.remove(bou);
                contenu.remove(bou);
                bs.supprimerBoutique(bou);
                mc.updateItems("");
            }
        });
    }

    @FXML
    private void modifierBoutique(ActionEvent event) throws IOException {
        ModifierBoutiqueController.bc = this;
        ModifierBoutiqueController.boutiqueSelected = bou;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierBoutique.fxml"));
        Utils.getAnotherStage(loader, "Modification d'une boutique ").show();
    }

    @FXML
    private void test(MouseEvent event) {
        mc.boutiqueSelected = bou;
        mc.setValues(bou);
    }

    @FXML
    private void partagerBoutique(ActionEvent event) {
        Boutique B = new Boutique();

        BoutiqueService bt = new BoutiqueService();

        B = bt.chercherBoutiqueParID(bou.getId());

        String accessToken = "EAACEdEose0cBAPa1dPYgDuF4KzAXyHZBaMa1JY9mfwICzu0JlNZBz4FgiB7R9jPYi6wCaXWf2woYq4T7oqSK796oXkM45E7h3AB5PkMbiPZBQyFNDSHYfrbMKQqZC19pLMPEbXZAWZBZCBLsAqepcZA2NvUQReuux1eEnGyoGEn2ZBkAdlJ2fR4Op6q11pZAsX3RY44EZAyK5zHbgZDZD";
        Scanner s = new Scanner(System.in);
        FacebookClient fbClient = new DefaultFacebookClient(accessToken);
        FacebookType response = fbClient.publish("me/feed", FacebookType.class,
                Parameter.with("message", "Boutique " + B.getNom() + " à l'adresse" + B.getAdresse()),
                Parameter.with("link", "http://127.168.0.1/"));
        System.out.println("fb.com/" + response.getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("success");
        alert.setContentText("Votre annonce à été publiée");
        alert.showAndWait();

    }


}
