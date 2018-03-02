/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Evaluation;
import Entities.Reclamation;
import Entities.User;
import Services.BoutiqueService;
import Services.EvaluationService;
import Services.ReclamationService;
import Services.UserService;
import Utils.NavigatorData;
import Utils.Enumerations.*;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
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
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Rating;

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
    @FXML
    private ImageView photo;
    @FXML
    private Rating evaluation;
    @FXML
    private JFXButton reclamationB;
    @FXML
    private JFXTextArea reclamation;
    @FXML
    private JFXButton validation;
    @FXML
    private Label warning;
    @FXML
    private JFXButton addBoutique;
    @FXML
    private Separator separateur;
    
    public static GridPane gridPane = new GridPane();
    public static List<Boutique> list;
    public static UneBoutiqueArtisanController bc;
    public static Boutique boutiqueSelected;
    private FileInputStream photoProfil = null;
    BoutiqueService bs = new BoutiqueService();

    EvaluationService es = new EvaluationService();
    ReclamationService rs = new ReclamationService();
    UserService us = new UserService();
    @FXML
    private JFXButton stock;


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
            if (parents.size() > 0) {
                gridPane.add(parents.get(0), 1, nbrRows - 1);
                parents.remove(0);
            }
        }
        //paire 
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

    public void setValues(Boutique boutiqueSelectede) {
        if (boutiqueSelectede != null) {
            NomB.setText(boutiqueSelectede.getNom());
            idB.setText(String.valueOf(boutiqueSelectede.getId()));
            adresseB.setText(boutiqueSelectede.getAdresse());
            dateB.setText(boutiqueSelectede.getDateCreation().toString().replace("T", " "));
            evaluation.setRating(es.getNoteBoutique(boutiqueSelectede));
            evaluation.setPartialRating(true);
            if (es.peutEvaluer(AccueilController.userConnected, boutiqueSelectede)) {
                evaluation.setDisable(false);
            } else {
                evaluation.setDisable(true);
            }
            if (rs.peutReclamer(AccueilController.userConnected, boutiqueSelectede)){
                reclamationB.setVisible(true);
                reclamation.setVisible(true);
                validation.setVisible(true);
                warning.setVisible(true);
            } else {
                reclamationB.setVisible(false);
                reclamation.setVisible(false);
                validation.setVisible(false);
                warning.setVisible(false);
            }
            photo.setImage(bs.getPhoto(boutiqueSelectede.getId()));
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
        if (AccueilController.userConnected != null) {
            if (AccueilController.userConnected.getType() == TypeUser.Administrateur) {
                separateur.setVisible(true);
                idB.setVisible(true);
                evaluation.setDisable(true);
                warning.setVisible(false);
                validation.setVisible(false);
                reclamationB.setVisible(false);
                addBoutique.setVisible(false);
                reclamation.setVisible(false);//text area
                stock.setVisible(false);
                  list = bs.lireBoutiques();
            } else if (AccueilController.userConnected.getType() == TypeUser.Artisan) {
                separateur.setVisible(false);
                idB.setVisible(false);
                evaluation.setDisable(true);
                warning.setVisible(false);
                validation.setVisible(false);
                reclamationB.setVisible(false);
                reclamation.setVisible(false);//text area
                addBoutique.setVisible(true);
                 stock.setVisible(true);
                   list = bs.lireBoutique(AccueilController.userConnected);
            } else if (AccueilController.userConnected.getType() == TypeUser.Client) {
                separateur.setVisible(false);
                idB.setVisible(false);
                evaluation.setDisable(false);
                warning.setVisible(true);
                validation.setVisible(true);
                reclamationB.setVisible(true);
                reclamation.setVisible(true);//text area
                addBoutique.setVisible(false);
                 stock.setVisible(false);
                   list = bs.lireBoutiques();
            }
        } else {//visiteur
            separateur.setVisible(false);
            idB.setVisible(false);
            evaluation.setDisable(true);
            warning.setVisible(false);
            validation.setVisible(false);
            reclamationB.setVisible(false);
            reclamation.setVisible(false);//text area
            addBoutique.setVisible(false);
              list = bs.lireBoutiques();
                      
        }

        evaluation.setRating(0);
        filter.textProperty().addListener((observable, oldValue, newValue) -> updateItems(newValue));
        UneBoutiqueArtisanController.contenu = list;
        UneBoutiqueArtisanController.mc = this;
        
        gridPane.getChildren().clear();
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

            NavigatorData.getInstance().setLat(B.getLat());
            NavigatorData.getInstance().setLong(B.getLong());
            NavigatorData.getInstance().setAdr(B.getAdresse());

            FXMLLoader loader = new FXMLLoader(getClass().getResource("BoutiqueMap.fxml"));
            Stage s = Utils.getAnotherStage(loader, "La position de la boutique sur la carte");
            s.initStyle(StageStyle.DECORATED);
            s.setResizable(true);
            s.show();
        }
    }

    @FXML
    private void afficherProduit(ActionEvent event) throws IOException {
        if(boutiqueSelected!=null)
        {
            MenuProduitsController.boutique=boutiqueSelected;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuProduits.fxml"));
            Utils.getAnotherStage(loader, "Menu produits ").show();

        }
        
    }

    @FXML
    private void ajouterBoutique(MouseEvent event) {
        BoutiqueController.mc = this;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Boutique.fxml"));
        Utils.getAnotherStage(loader, "Ajout d'une boutique ").show();
    }

    @FXML
    private void evaluer(MouseEvent event) {
        Evaluation e = new Evaluation(AccueilController.userConnected, boutiqueSelected, (float) evaluation.getRating());
        int idEvaluation = es.getIdEvaluation(AccueilController.userConnected, boutiqueSelected);
        if (idEvaluation != 0) {
            e.setId(idEvaluation);
            es.modifierEvaluation(e);
            warning.setText("Votre Evaluation a été modifiée");
            warning.setVisible(true);
        } else {
            es.ajouterEvaluation(e);
            warning.setVisible(true);
            warning.setText("Votre Evaluation a été enregistrée");
        }
    }

    @FXML
    private void afficherReclamationArea(ActionEvent event) {
        reclamationB.setVisible(false);
        reclamation.setVisible(true);
        validation.setVisible(true);
        warning.setVisible(false);
    }

    @FXML
    private void reclamer(ActionEvent event) {
        reclamation.setVisible(false);
        validation.setVisible(false);
        reclamationB.setVisible(true);
        if (reclamation.getText().equals("")) {
            warning.setVisible(true);
            warning.setText("Veuillez taper votre réclamation");
        } else {
            warning.setVisible(false);
            Reclamation r = new Reclamation(AccueilController.userConnected, boutiqueSelected, reclamation.getText());
            rs.ajouterReclamation(r);
            reclamation.setText("");
            warning.setText("Votre reclamation a été enregistrée");
            warning.setVisible(true);

        }
    }

    @FXML
    private void gererStock(MouseEvent event) {
          if(boutiqueSelected!=null)
        {
            MenuProduitsController.boutique=boutiqueSelected;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ListStock.fxml"));
            Utils.getAnotherStage(loader, "Menu produits ").show();

        }
    }

}
