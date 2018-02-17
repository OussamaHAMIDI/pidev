/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Panier;
import Entities.Produit;
import Entities.ProduitPanier;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class HistoriqueProduitController implements Initializable {

    
    @FXML
    private AnchorPane anchorProduit;
    @FXML
    private TableView<ProduitPanier> historiqueProduit;
    @FXML
    private TableColumn<ProduitPanier, String> reference = new TableColumn<>("reference");
    @FXML
    private TableColumn<ProduitPanier, String> libelle = new TableColumn<>("libelle");
    @FXML
    private TableColumn<ProduitPanier, Float> prix = new TableColumn<>("prixVente");
    @FXML
    private TableColumn<ProduitPanier, Integer> quantite = new TableColumn<>("quantiteVendue");

    @FXML
    private AnchorPane anchorProduitHistorique;
    
    @FXML
    private Button retour;
    
    private ObservableList<ProduitPanier> produits = FXCollections.observableArrayList();
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        setColumnProperties();
        setProduitsDetails();
        
    }    

    private void setColumnProperties() {
        reference.setCellValueFactory(new PropertyValueFactory<>("reference"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
        quantite.setCellValueFactory(new PropertyValueFactory<>("quantiteVendue"));
    }

    private void setProduitsDetails() {
        produits.clear();
        //ProduitPanier prd = HistoriqueClientController.produitPasse;
        //List<ProduitPanier> pps = new ArrayList<ProduitPanier>();
        //pps.add(prd);
        //System.out.println(prd);
        //produits.addAll(pps);
        //historiqueProduit.setItems(produits);
    }
    
    @FXML
    private void retourner(ActionEvent event) throws IOException {
        
        
        System.out.println("retourner");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("HistoriqueClient.fxml"));
        anchorProduit.getChildren().setAll(pane);
    }
    
}
