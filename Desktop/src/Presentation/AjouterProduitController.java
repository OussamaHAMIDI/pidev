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
public class AjouterProduitController implements Initializable {
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
    private Button ajouter;
    @FXML
    private Button annuler;
    @FXML
    private Text statut;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        statut.setText ("No file selected");
        
    }    
    @FXML
    public void ajouterProduit ()
    {
        ProduitService ps=new ProduitService();
        ps.ajouterProduit(new Produit(0, reference.getText(), libelle.getText(), description.getText(),Float.parseFloat(prix.getText()), taille.getText(), couleur.getText(), texture.getText(), Float.parseFloat(poids.getText()), null, LocalDateTime.MAX, null));
    }
    public void clearFields ()
    {
        reference.setText("");
        libelle.setText("");
        description.setText("");
        prix.setText("");
        taille.setText("");
        couleur.setText("");
        texture.setText("");
        poids.setText("");
        
    }
}
