/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Panier;
import Entities.Produit;
import Entities.ProduitPanier;
import Services.EvaluationService;
import Services.HistoriqueService;
import Services.PanierService;
import Services.UserService;
import Utils.Enumerations;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class HistoriqueProduitController implements Initializable {

    @FXML
    private TableView<ProduitPanier> historiqueProduit;
    @FXML
    private TableColumn<ProduitPanier, String> reference = new TableColumn<>("reference");
    @FXML
    private TableColumn<ProduitPanier, String> libelle = new TableColumn<>("libelle");
    @FXML
    private TableColumn<ProduitPanier, Float> prixVente = new TableColumn<>("prixVente");
    @FXML
    private TableColumn<ProduitPanier, Integer> quantiteVendue = new TableColumn<>("quantiteVendue");
    //@FXML
    //private TableColumn<ProduitPanier, Boolean> details = new TableColumn<>("details");

    public static List<ProduitPanier> listePassee = new ArrayList<ProduitPanier>();
    public static Panier p;
    public static int index;
    private List<ProduitPanier> produitsPanier;
    @FXML
    private AnchorPane anchorProduit;

    private ObservableList<ProduitPanier> produits = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        
        System.out.println("PRODUIT CONTROLLERRRR P  statique ====" + p);
        PanierService ps = new PanierService();
        produitsPanier = ps.rechercherProduitsPanier(p.getId());
        System.out.println(produitsPanier);
        setColumnProperties();
        setProduitDetails(produitsPanier);
    }

    private void redirect() throws IOException {
        //TODO
        AnchorPane pane = FXMLLoader.load(getClass().getResource("HistoriqueClient.fxml"));
        anchorProduit.getChildren().setAll(pane);
    }

    private void setColumnProperties() {
        
        
        reference.setCellValueFactory(new PropertyValueFactory<>("reference"));
        libelle.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        prixVente.setCellValueFactory(new PropertyValueFactory<>("prixVente"));
        quantiteVendue.setCellValueFactory(new PropertyValueFactory<>("quantiteVendue"));
    }

    private void setProduitDetails(List<ProduitPanier> pp) {
        System.out.println(listePassee);
        produits.clear();
        produits.addAll(pp);
        historiqueProduit.setItems(produits);

    }
    
    @FXML
    private void retourner(ActionEvent event) throws IOException {
        System.out.println("retourner");
        AnchorPane pane = FXMLLoader.load(getClass().getResource("HistoriqueClient2.fxml"));
        anchorProduit.getChildren().setAll(pane);
        System.out.println("blablabla");
    }

}
