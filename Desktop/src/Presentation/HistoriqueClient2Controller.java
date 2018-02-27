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
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class HistoriqueClient2Controller implements Initializable {
    @FXML
    private AnchorPane historiques = new AnchorPane();
    @FXML
    private ScrollPane scrollHistoriques = new ScrollPane();
    
    public static ObservableList<Node> historiquesChildren;
    GridPane gridPane = new GridPane();
    GridPane gridPanePP = new GridPane();
    public static List<Panier> listePaniers;
    public static List<ProduitPanier> listeProduitsPanier;
    public static HistoriqueClientItemController hcic;
    public static HistoriqueProduitPanierItemController hppic;
    public static Panier panierSelectionne;
    public static int index ;
    public static int index2;
    private ScrollPane scrollPaneProduits = new ScrollPane();
    List<Parent> list = new ArrayList<Parent>();
    FXMLLoader loader;
    Parent root;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HistoriqueService hs = new HistoriqueService();
        UserService us = new UserService();
        listePaniers = hs.getHistoriqueUser(us.getUserById(29));
        //HistoriqueClientItemController.listePaniers = this.listePaniers;
        HistoriqueClientItemController.listePaniers = listePaniers;
        try {
            for (int i = 0; i < listePaniers.size(); i++) {
                HistoriqueClientItemController.index = i;
                loader = new FXMLLoader(getClass().getResource("HistoriqueClientItem.fxml"));
                root = loader.load();
                list.add(root);
            }
            addToGrid(list, gridPane);
            gridPane.setHgap(0);
            gridPane.setVgap(30);
            scrollHistoriques.setContent(gridPane);
            scrollHistoriques.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            historiquesChildren = historiques.getChildren();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }    
    
    private void addToGrid(List<Parent> list, GridPane gridPane) {
        int totalItems = list.size();
        int nbrItems = gridPane.getChildren().size();
        int nbrRows = (nbrItems % 2 == 0) ? nbrItems / 2 : (nbrItems + 1) / 2;

        if (nbrItems % 2 == 1) {
            if (list.size() > 0) {
                gridPane.add(list.get(0), 0, nbrRows - 1);
                list.remove(0);
            }
        }
        
        for (int ligne = nbrRows; ligne < nbrRows + totalItems; ligne++) {
            if (list.size() > 0) {
                gridPane.add(list.get(0), 0, ligne);
                list.remove(0);
            } else {
                return;
            }
        }

    }
    
    public void  redirectDetails(){
        PanierService ps = new PanierService();
        HistoriqueProduitController.p = listePaniers.get(index2);
        HistoriqueProduitController.index=index2;
        System.out.println("EKHER FORSSAAA ==== " + index2 + listePaniers.get(index2));
        
    }
}
