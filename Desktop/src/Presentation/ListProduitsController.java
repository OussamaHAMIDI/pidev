/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Produit;
import Entities.ProduitPanier;
import Services.ProduitService;
import Services.StatistiqueService;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author oussamahamidi
 */
public class ListProduitsController implements Initializable {
    @FXML
    private ScrollPane listProduits;
   public static List<Produit> produits=new ArrayList<Produit>();
    GridPane gridPane= new GridPane();
    Double scrollWidth=0.0;
    
    private void addToGrid(List<Parent> list, GridPane gridPane) {
        int totalItems = list.size();
        int totalLignes = (totalItems % 2 == 0) ? totalItems / 2 : (totalItems + 1) / 2;
        int nbrItems = gridPane.getChildren().size();
        int nbrRows = (nbrItems % 2 == 0) ? nbrItems / 2 : (nbrItems + 1) / 2;

        if (nbrItems % 2 == 1) {// impaire
            if (list.size() > 0) {
                gridPane.add(list.get(0), nbrRows - 1,0);
                list.remove(0);
            }
        }
        //paire
        for (int ligne = nbrRows; ligne < nbrRows + totalItems; ligne++) {
//            for (int col = 0; col < 2; col++) {
                if (list.size() > 0) {
                    gridPane.add(list.get(0), ligne,0);
                    list.remove(0);
                } else {
                    return;
                }
//            }
        }

    }
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Parent> list = new ArrayList<Parent>();
          try {
            // TODO
         
//          //Jib liste de produits
              StatistiqueService ss = new StatistiqueService();
             produits.addAll(ss.getTopTenProduits());
            ProduitLController.contenu = produits;
            
            for (int i = 0; i < produits.size(); i++) {
                ProduitLController.index = i;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ProduitL.fxml"));
                Parent root = loader.load();
                list.add(root);
                
            }
            addToGrid(list, gridPane);
            gridPane.setHgap(45);
            gridPane.setVgap(20);
            listProduits.setPannable(true);
            listProduits.setContent(gridPane);
            ProduitController.lp = this;

        } catch (IOException ex) {
            Logger.getLogger(ListProduitsController.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }    

    
    
}
