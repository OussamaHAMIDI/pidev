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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    BoutiqueService bs = new BoutiqueService();
    @FXML
    private Circle circle;

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
//        GridPane parent = (GridPane)user.getParent();
//        parent.getChildren().remove(user);

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
        Stage s = Utils.getAnotherStage(loader, "Modification d'une boutique ");
        s.initStyle(StageStyle.UNDECORATED);
        s.show();
    }

    @FXML
    private void test(MouseEvent event) {
        mc.boutiqueSelected = bou;
        mc.setValues(bou);
//        System.out.println(bou.toString() + index);
    }

    @FXML
    private void partagerBoutique(ActionEvent event) {
        Boutique B = new Boutique();

        BoutiqueService bt = new BoutiqueService();
        B = bt.chercherBoutiqueParID(B.getId());

        String accessToken = "EAACEdEose0cBALnh8FXxkOGAmDxrhvukgahtLksbuUITg8vydx3NFcBjAQnZBcXNFjmTIyFMIisUrB2gKFwSbzjYnnuMkOLySiQJRWe2kkXxlwZAbmYlkvZBKduoPXPqrhwWsgdfn9ivXJY4SweyM0ZBH2XZBZANgJNHLH3LaLMibaUhKejQdlA5H9taDsrQYZD";
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
