/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Evaluation;
import Entities.Produit;
import Entities.Reclamation;
import Entities.User;
import Services.EvaluationService;
import Services.ProduitService;
import Services.ReclamationService;
import Services.UserService;
import Utils.Enumerations.TypeUser;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author monta
 */
public class MenuProduitsController implements Initializable {

    Produit produit = new Produit();

    @FXML
    private JFXButton produitB;
    private ScrollPane boutiques;
    @FXML
    private JFXTextField filter;

    public static GridPane gridPane = new GridPane();
    public static List<Produit> list;
    public static ProduitController pc;
    public static Produit produitSelected;

    private FileInputStream photoProfil = null;

    ProduitService ps = new ProduitService();
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
    @FXML
    private Label libelle;
    @FXML
    private Label prix;
    @FXML
    private Label description;
    @FXML
    private Label reference;
    @FXML
    private ScrollPane produits;
    @FXML
    private JFXButton addProduct;

    public void addToGrid(List<Produit> list) {
        int totalItems = list.size();
        int nbrItems = gridPane.getChildren().size();
        int nbrRows = (nbrItems % 2 == 0) ? nbrItems / 2 : (nbrItems + 1) / 2;
        List<Parent> parents = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            try {
                ProduitController.index = i;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Produit.fxml"));
                Parent root = loader.load();
                parents.add(root);
            } catch (IOException ex) {
                Logger.getLogger(MenuProduitsController.class.getName()).log(Level.SEVERE, null, ex);
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

    public void setValues(Produit produitSelected) {
        if (produitSelected != null) {
            if (produitSelected.getPoids() != 0) {
                libelle.setText(produitSelected.getLibelle() + " " + produitSelected.getPoids());
            } else {
                libelle.setText(produitSelected.getLibelle() + " " + produitSelected.getTaille() + " " + produitSelected.getCouleur() + " " + produitSelected.getTexture());
            }

            reference.setText(produitSelected.getReference());
            description.setText(produitSelected.getDescription());
            prix.setText(Float.toString(produitSelected.getPrix()));
            evaluation.setRating(es.getNoteProduit(produitSelected));

            if (es.peutEvaluer(u, produitSelected)) {
                evaluation.setDisable(false);
            } else {
                evaluation.setDisable(true);
            }
            //            if(es.peutEvaluer(u,produitSelected)){
            //                evaluation.setDisable(false);
            //            }else{
            //                evaluation.setDisable(true);
            //            } 
            photo.setImage(ps.getPhoto(produitSelected.getId()));
        }
    }

    public void updateItems(String text) {

        if (!text.isEmpty()) {
            list = list.stream().filter(b -> b.getLibelle().toLowerCase().contains(text.toLowerCase())
                    || b.getReference().toLowerCase().contains(text.toLowerCase())).collect(Collectors.toList());
            gridPane.getChildren().clear();
            addToGrid(list);
        } else {
            list = ps.listerProduits();
            gridPane.getChildren().clear();
            addToGrid(list);
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (AccueilController.userConnected.getType() == TypeUser.Administrateur) {
            addProduct.setVisible(false);
            evaluation.setDisable(true);
            produitB.setVisible(false);
            validation.setVisible(false);
            reclamationB.setVisible(false);
            reclamation.setVisible(false);//text area
        } else if (AccueilController.userConnected.getType() == TypeUser.Artisan) {
            addProduct.setVisible(true);
            evaluation.setDisable(true);
            produitB.setVisible(true);
            validation.setVisible(true);
            reclamationB.setVisible(true);
            reclamation.setVisible(true);//text area
        } else if(AccueilController.userConnected.getType() == TypeUser.Client){
            addProduct.setVisible(false);
            evaluation.setDisable(false);
            produitB.setVisible(true);
            validation.setVisible(true);
            reclamationB.setVisible(true);
            reclamation.setVisible(true);//text area
        }else{//visiteur
             addProduct.setVisible(false);
            evaluation.setDisable(false);
            produitB.setVisible(true);
            validation.setVisible(false);
            reclamationB.setVisible(false);
            reclamation.setVisible(false);//text area
        }

        evaluation.setRating(0);
        evaluation.setDisable(true);
        reclamation.setVisible(false);
        warning.setVisible(false);
        validation.setVisible(false);
        gridPane = new GridPane();
        filter.textProperty().addListener((observable, oldValue, newValue) -> updateItems(newValue));
        list = ps.listerProduits();
        ProduitController.contenu = list;
        ProduitController.mc = this;
        addToGrid(list);
        gridPane.setHgap(25);
        gridPane.setVgap(25);
        produits.setPannable(true);
        produits.setContent(gridPane);
    }

    @FXML
    private void ajouterBoutique(MouseEvent event) {
        AjouterProduitController.mc = this;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterProduit.fxml"));
        Utils.getAnotherStage(loader, "Ajout d'un produits ").show();
    }

    @FXML
    private void evaluer(MouseEvent event) {
        Evaluation e = new Evaluation(u, produitSelected, (float) evaluation.getRating());
        int idEvaluation = es.getIdEvaluation(u, produitSelected);
        if (idEvaluation != 0) {
            e.setId(idEvaluation);
            es.modifierEvaluation(e);
            System.out.println("EXISTE DEJA ====> update");
            warning.setText("Votre Evaluation a été modifiée");
            warning.setVisible(true);
        } else {
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
        if (reclamation.getText().equals("")) {
            warning.setVisible(true);
            warning.setText("Veuillez taper votre réclamation");
        } else {
            warning.setVisible(false);
            Reclamation r = new Reclamation(u, produitSelected, reclamation.getText());
            rs.ajouterReclamation(r);
            reclamation.setText("");
            warning.setText("Votre reclamation a été enregistrée");
            warning.setVisible(true);

        }
    }

    @FXML
    private void ajouterPanier(ActionEvent event) {

    }

}
