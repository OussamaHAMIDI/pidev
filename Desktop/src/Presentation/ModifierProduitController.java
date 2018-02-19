/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Produit;
import Services.ProduitService;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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
    private Text statut;
    @FXML
    private Button modifier;
    @FXML
    private Button supprimer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Produit pr=new Produit();
        reference.setText(pr.getReference());
        libelle.setText(pr.getLibelle());
        description.setText(pr.getDescription());
        prix.setText(Float.toString(pr.getPrix()));
        taille.setText(pr.getTaille());
        texture.setText(pr.getTexture());
        poids.setText(Float.toString(pr.getPoids()));
        
        
    }    

    @FXML
    private void modifierProduit(ActionEvent event) {
        ProduitService ps=new ProduitService();
        ps.modifierProduit(new Produit(0, reference.getText(), libelle.getText(), description.getText(),Float.parseFloat(prix.getText()), taille.getText(), couleur.getText(), texture.getText(), Float.parseFloat(poids.getText()), null, LocalDateTime.MAX, null));
    }

    @FXML
    private void supprimerProduit(ActionEvent event) {
        ProduitService ps=new ProduitService();
        Produit pr=new Produit();
        ps.supprimerProduit(pr.getId());
    }
    
}
