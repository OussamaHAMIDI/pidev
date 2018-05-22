/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Produit;
import Services.StatistiqueService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author oussamahamidi
 */
public class AffichageProdController implements Initializable {
    @FXML
    private ScrollPane scrollProd;
    @FXML
    private ScrollPane scrollBout;
    
    public static GridPane gridPaneProduit = null ;
    public static GridPane gridPaneBoutique = null ;
    StatistiqueService ss = new StatistiqueService();
    public static List<Produit> p = null;
    public static List<Boutique> b = null;
    
    /**
     * Initializes the controller class.
     */
    public void addToGridProduit(List<Produit> list) {
        int totalItems = list.size();
        int nbrItems = gridPaneProduit.getChildren().size();
        int nbrRows = (nbrItems % 2 == 0) ? nbrItems / 2 : (nbrItems + 1) / 2;
        List<Parent> parents = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            try {
                ProduitController.testProd=true;
                ProduitController.index = i;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Produit.fxml"));
                Parent root = loader.load();
                ProduitController.testProd=false;
                parents.add(root);
            } catch (IOException ex) {
                Logger.getLogger(GestionUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (nbrItems % 2 == 1) {// impaire
            if (parents.size() > 0) {
                gridPaneProduit.add(parents.get(0), 1, nbrRows - 1);
                parents.remove(0);
            }
        }
        //paire
        for (int ligne = nbrRows; ligne < nbrRows + totalItems; ligne++) {
            //for (int col = 0; col < 2; col++) {
            if (parents.size() > 0) {
                gridPaneProduit.add(parents.get(0), 0, ligne);
                parents.remove(0);
            } else {
                return;
            }
            // }
        }

    }
    public void addToGridBoutique(List<Boutique> list) {
        int totalItems = list.size();
        int nbrItems = gridPaneBoutique.getChildren().size();
        int nbrRows = (nbrItems % 2 == 0) ? nbrItems / 2 : (nbrItems + 1) / 2;
        List<Parent> parents = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            try {
                UneBoutiqueArtisanController.index = i;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("UneBoutiqueArtisan.fxml"));
                Parent root = loader.load();
                parents.add(root);
            } catch (IOException ex) {
                Logger.getLogger(GestionUsersController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (nbrItems % 2 == 1) {// impaire
            if (parents.size() > 0) {
                gridPaneBoutique.add(parents.get(0), 1, nbrRows - 1);
                parents.remove(0);
            }
        }
        //paire
        for (int ligne = nbrRows; ligne < nbrRows + totalItems; ligne++) {
            //for (int col = 0; col < 2; col++) {
            if (parents.size() > 0) {
                gridPaneBoutique.add(parents.get(0), 0, ligne);
                parents.remove(0);
            } else {
                return;
            }
            // }
        }

    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        b=ss.getTopTenBoutiques();
        System.out.println("TOP TEN BOUTIQUE ======" +b);
        p=ss.getTopTenProduits();
        gridPaneBoutique=new GridPane();
        gridPaneProduit=new GridPane();
        ProduitController.contenu = p;
        addToGridProduit(p);
        gridPaneProduit.setHgap(25);
        gridPaneProduit.setVgap(25);
        scrollProd.setPannable(true);
        scrollProd.setContent(gridPaneProduit);
        
        UneBoutiqueArtisanController.contenu = b;
        addToGridBoutique(b);
        gridPaneBoutique.setHgap(25);
        gridPaneBoutique.setVgap(25);
        scrollBout.setPannable(true);
       scrollBout.setContent(gridPaneBoutique);
    }    
    
}
