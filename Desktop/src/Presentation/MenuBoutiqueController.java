/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Services.BoutiqueService;
import Utils.NavigatorData;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Azza
 */
public class MenuBoutiqueController implements Initializable {

    Boutique boutique = new Boutique();
    @FXML
    private Label idB;
    @FXML
    private Label dateB;
    @FXML
    private Label adresseB;
    @FXML
    private Label NomB;
    @FXML
    private JFXButton MapB;
    @FXML
    private JFXButton produitB;
    @FXML
    private ScrollPane boutiques;
    @FXML
    private JFXTextField filter;
    public static GridPane gridPane = new GridPane();
    public static List<Boutique> list;
    public static UneBoutiqueArtisanController bc;
    public static Boutique boutiqueSelected;

    private FileInputStream photoProfil = null;

    BoutiqueService bs = new BoutiqueService();
    @FXML
    private ImageView photo;

    public void addToGrid(List<Boutique> list) {
        int totalItems = list.size();
        int nbrItems = gridPane.getChildren().size();
        int nbrRows = (nbrItems % 2 == 0) ? nbrItems / 2 : (nbrItems + 1) / 2;
        List<Parent> parents = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            try {
                UneBoutiqueArtisanController.index = i;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UneBoutiqueArtisan.fxml"));
                Parent root = loader.load();
                parents.add(root);
            } catch (IOException ex) {
                Logger.getLogger(MenuBoutiqueController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (nbrItems % 2 == 1) {// impaire
            if (list.size() > 0) {
                gridPane.add(parents.get(0), 1, nbrRows - 1);
                parents.remove(0);
            }
        }
        //paire // erreur affichage
        for (int ligne = nbrRows; ligne < nbrRows + totalItems; ligne++) {
            for (int col = 0; col < 2; col++) {
                if (parents.size() > 0) {
                    gridPane.add(parents.get(0), col, ligne);
                    parents.remove(0);
                } else {
                    return;
                }
            }
        }
    }

    public void setValues(Boutique boutiqueSelected) {
        if (boutiqueSelected != null) {
            NomB.setText(boutiqueSelected.getNom());
            idB.setText(String.valueOf(boutiqueSelected.getId()));
            adresseB.setText(boutiqueSelected.getAdresse());
            dateB.setText(boutiqueSelected.getDateCreation().toString().replace("T", " "));

            photo.setImage(bs.getPhoto(boutiqueSelected.getId()));
        }
    }

    public void updateItems(String text) {

        if (!text.isEmpty()) {
            list = list.stream().filter(b -> b.getNom().toLowerCase().contains(text.toLowerCase())
                    || b.getAdresse().toLowerCase().contains(text.toLowerCase())).collect(Collectors.toList());
            gridPane.getChildren().clear();
            addToGrid(list);
        } else {
            list = bs.lireBoutiques();
            gridPane.getChildren().clear();
            addToGrid(list);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        gridPane = new GridPane();
        filter.textProperty().addListener((observable, oldValue, newValue) -> updateItems(newValue));
        list = bs.lireBoutiques();
        UneBoutiqueArtisanController.contenu = list;
        UneBoutiqueArtisanController.mc = this;
        addToGrid(list);
        gridPane.setHgap(25);
        gridPane.setVgap(25);
        boutiques.setPannable(true);
        boutiques.setContent(gridPane);

    }

    @FXML
    private void MapBoutique(ActionEvent event) throws IOException {
        Boutique B = new Boutique();
        if (boutiqueSelected != null) {
            BoutiqueService bt = new BoutiqueService();
            B = bt.chercherBoutiqueParID(boutiqueSelected.getId());
            //System.out.println(B.getLat() + "  " + B.getLong());

            NavigatorData.getInstance().setLat(B.getLat());
            NavigatorData.getInstance().setLong(B.getLong());
            NavigatorData.getInstance().setAdr(B.getAdresse());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("BoutiqueMap.fxml"));
            Stage s = Utils.getAnotherStage(loader, "La position de la boutique sur la carte");
            s.initStyle(StageStyle.DECORATED);
            s.show();

//            Stage stage=new Stage();
//            Parent root = FXMLLoader.load(getClass().getResource("BoutiqueMap.fxml"));
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
        }
    }

    @FXML
    private void afficherProduit(ActionEvent event) {
    }

    @FXML
    private void ajouterBoutique(MouseEvent event) {
        BoutiqueController.mc = this;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Boutique.fxml"));
        Stage s = Utils.getAnotherStage(loader, "Ajout d'une boutique ");
        s.initStyle(StageStyle.UNDECORATED);
        s.show();
    }

    @FXML
    private void rechercheBoutique(MouseEvent event) {
    }

}
