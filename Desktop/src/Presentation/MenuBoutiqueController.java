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
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    public static GridPane gridPane = new GridPane();
    public static List<Boutique> list;
    public static UneBoutiqueArtisanController bc;
    public static Boutique boutiqueSelected;

    private FileInputStream photoProfil = null;

    BoutiqueService bs = new BoutiqueService();
    @FXML
    private ImageView photo;
    @FXML
    private Rating evaluation;
    EvaluationService es = new EvaluationService();
    ReclamationService rs = new ReclamationService();
    UserService us = new UserService();
    User u = us.getUserById(2);
    @FXML
    private JFXButton reclamationB;
    @FXML
    private JFXTextArea reclamation;
    @FXML
    private JFXButton validation;
    @FXML
    private Label warning;

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

    public void setValues(Boutique boutiqueSelectede) {
        if (boutiqueSelectede != null) {
            NomB.setText(boutiqueSelectede.getNom());
            idB.setText(String.valueOf(boutiqueSelectede.getId()));
            adresseB.setText(boutiqueSelectede.getAdresse());
            dateB.setText(boutiqueSelectede.getDateCreation().toString().replace("T", " "));
            evaluation.setRating(es.getNoteBoutique(boutiqueSelectede));
            System.out.println("NOTE ===== "+es.getNoteBoutique(boutiqueSelectede)+boutiqueSelectede);
            if(es.peutEvaluer(u,boutiqueSelectede)){
                evaluation.setDisable(false);
            }else{
                evaluation.setDisable(true);
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
        evaluation.setRating(0);
        evaluation.setDisable(true);
        reclamation.setVisible(false);
        warning.setVisible(false);
        validation.setVisible(false);
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

    @FXML
    private void evaluer(MouseEvent event) {
        Evaluation e = new Evaluation(u, boutiqueSelected, (float)evaluation.getRating());
        int idEvaluation = es.getIdEvaluation(u,boutiqueSelected);
        if(idEvaluation !=0){
            e.setId(idEvaluation);
            es.modifierEvaluation(e);
            System.out.println("EXISTE DEJA ====> update");
            warning.setText("Votre Evaluation a été modifiée");
            warning.setVisible(true);
        }
        else{
            es.ajouterEvaluation(e); 
            System.out.println("new new Add");
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
        if(reclamation.getText().equals("")){
            warning.setVisible(true);
            warning.setText("Veuillez taper votre réclamation");
        } else {
            warning.setVisible(false);
            Reclamation r = new Reclamation(u,boutiqueSelected,reclamation.getText());
            rs.ajouterReclamation(r);
            reclamation.setText("");
            warning.setText("Votre reclamation a été enregistrée");
            warning.setVisible(true);
            
        }
    }

}
