/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Panier;
import Entities.ProduitPanier;
import Services.HistoriqueService;
import Services.PanierService;
import Services.UserService;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class HistoriqueClientItemController implements Initializable {
    //public static Panier panierPasse;
    
    public static List<Panier> listePaniers;
    public static int index;
    private Panier p = new Panier();
    public static HistoriqueClient2Controller hcc = new HistoriqueClient2Controller();
    private int indexSelectionne;
    private List<ProduitPanier> lpp = new ArrayList<ProduitPanier>();
    
    
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listePaniers = HistoriqueClient2Controller.listePaniers;
        indexSelectionne=index;
        p = HistoriqueClient2Controller.listePaniers.get(index);
        System.out.println("P FEL INITIALIZE === " +p);
        totalTTC.setText("Total TTC : " + ((Double)p.getTotalTTC()).toString());
        modePaiement.setText("Mode de Paiement : " + p.getModePaiement().toString());
        modeLivraison.setText("Mode de Livraison : "+p.getModeLivraison().toString());
        fraisLivraison.setText("Frais de Livraison : "+((Double)p.getFraisLivraison()).toString());
        dateCreation.setText("Date de Création : " +p.getDateCreation().toString().replace("T", " "));
        datePaiement.setText("Date de Livraison : "+p.getDateLivraison().toString().replace("T"," " ));
    }    


    @FXML
    private void voirProtuis(ActionEvent event) throws IOException {
        PanierService ps = new PanierService();
        lpp = ps.rechercherProduitsPanier(this.getThis().getId());
        HistoriqueClient2Controller.listeProduitsPanier = lpp;
        HistoriqueClient2Controller.index2=indexSelectionne;
        HistoriqueProduitController.listePassee = lpp;
        hcc.redirectDetails();
        HistoriqueProduitController.listePassee=lpp;
        AnchorPane pane = FXMLLoader.load(getClass().getResource("HistoriqueProduit.fxml"));
        HistoriqueClient2Controller.historiquesChildren.setAll(pane);
        System.out.println("blablabla");
    }
    
    private Panier getThis(){
        return this.p;
    }
    
    private int getThisIndex(){
        return this.index;
    }
    
}
