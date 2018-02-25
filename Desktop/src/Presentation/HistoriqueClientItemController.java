/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Panier;
import Entities.ProduitPanier;
import Services.HistoriqueService;
import Services.UserService;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class HistoriqueClientItemController implements Initializable {
    public static Panier panierPasse;
    
    @FXML
    private AnchorPane reclamation;
    @FXML
    private JFXButton buttonP;
    @FXML
    private Label totalTTC;
    @FXML
    private Label estLivre;
    @FXML
    private Label estPaye;
    @FXML
    private Label dateCreation;
    @FXML
    private Label datePaiement;
    @FXML
    private Label modePaiement;
    @FXML
    private Label modeLivraison;
    @FXML
    private Label fraisLivraison;
    @FXML
    private Label status;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        totalTTC.setText("Total TTC : " + ((Double)panierPasse.getTotalTTC()).toString());
        modePaiement.setText("Mode de Paiement : " + panierPasse.getModePaiement().toString());
        modeLivraison.setText("Mode de Livraison : "+panierPasse.getModeLivraison().toString());
        fraisLivraison.setText("Frais de Livraison : "+((Double)panierPasse.getFraisLivraison()).toString());
        status.setText("Status : "+panierPasse.getStatus().toString());
        dateCreation.setText("Date de Création : " +panierPasse.getDateCreation().toString().replace("T", " "));
        datePaiement.setText("Date de Livraison : "+panierPasse.getDateLivraison().toString().replace("T"," " ));
    }    

    @FXML
    private void redirectHistoriqueProduit(MouseEvent event) throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("HistoriqueProduit.fxml"));
        HistoriqueClient2Controller.historiquesChildren.setAll(pane);
    }
    
}
