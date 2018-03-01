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
        circle.setFill(new ImagePattern(new Image("Images/produit_icon.png")));
        Image img = bs.getPhoto(b.getId());
        if (img != null) {
            circle.setFill(new ImagePattern(img));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (AccueilController.userConnected != null) {
            if (AccueilController.userConnected.getType() == TypeUser.Administrateur) {
                gris.setVisible(true);
                supprimer.setVisible(true);
                modifierB.setVisible(true);
                partagerB.setVisible(false);           
            } else if (AccueilController.userConnected.getType() == TypeUser.Artisan) {
                gris.setVisible(true);
                supprimer.setVisible(true);
                modifierB.setVisible(true);
                partagerB.setVisible(true);
            } else if (AccueilController.userConnected.getType() == TypeUser.Client) {
                gris.setVisible(false);
            }
        } else {//visiteur
            gris.setVisible(false);
        }
        bou = MenuBoutiqueController.list.get(index);
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
         if (boutiqueSelected != null) {
        BoutiqueService bt = new BoutiqueService();
        B = bt.chercherBoutiqueParID(boutiqueSelected.getId());

        String accessToken = "EAACEdEose0cBABMNP3nEnlZC7y6mhexfCK1EiX5jQjvRlsXZB8x7HfE6efHK0zwJt4ZCwQgiKQ2JgF0eiTheReTnhNfrukJhO8AvEw8cCLo5jWwZAUdOf863gY79BMeRe2I1nH7PUYdxFKf3sxyWIhYyS4ZAQqxqrc7RqjAdrxt3M1PNCadYdUIf2R60IjO3xu8iQeupt4QZDZD";
        Scanner s = new Scanner(System.in);
        FacebookClient fbClient = new DefaultFacebookClient(accessToken);
        FacebookType response = fbClient.publish("me/feed", FacebookType.class,
                Parameter.with("message", "Boutique" + B.getNom() + " at" + B.getAdresse()),
                Parameter.with("link", "http://127.168.0.1/"));
        System.out.println("fb.com/" + response.getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("success");
        alert.setContentText("Votre annonce à été publié");
        alert.showAndWait();

    }
    }

}
