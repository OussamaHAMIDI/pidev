/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Produit;
import Entities.Reclamation;
import Entities.User;
import Services.ProduitService;
import Services.ReclamationService;
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
public class ReclamationAdmin2Controller implements Initializable {

   
    
    
    @FXML
    private AnchorPane reclamations = new AnchorPane();
    
    public static ObservableList<Node> reclamationsChildren;
    
    @FXML
    private ScrollPane scrollReclamation;

    GridPane gridPane = new GridPane();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ReclamationService rs = new ReclamationService();
        List<Reclamation> listeReclamations = new ArrayList<Reclamation>();

        //listeReclamations = rs.getAllReclamations();
        ProduitService ps = new ProduitService();
        UserService us = new UserService();
        User u = us.getUserById(2);
        Produit p = new Produit();
        List<Produit> lp = new ArrayList<Produit>();
        Boutique b = new Boutique(u, "IMENS BOUTIQUE", lp);
        p.setId(2);
        p.setLibelle("LE PRODUIT LFLENI");
        p.setBoutique(b);
        lp.add(p);
        Reclamation rec1 = new Reclamation(u, p, "MAAAAASEEETTT");
        Reclamation rec2 = new Reclamation(u, b, "HLOWWWWWWWWWW");
        listeReclamations.add(rec1);
        listeReclamations.add(rec2);
        listeReclamations.add(rec1);
        listeReclamations.add(rec2);
        listeReclamations.add(rec1);
        listeReclamations.add(rec1);
        listeReclamations.add(rec1);
        listeReclamations.add(rec1);
        listeReclamations.add(rec1);
        listeReclamations.add(rec1);

        List<Parent> list = new ArrayList<Parent>();

        try {
            for (int i = 0; i < listeReclamations.size(); i++) {
                ReclamationItemController.reclamationPassee = listeReclamations.get(i);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ReclamationItem.fxml"));
                Parent root = loader.load();
                list.add(root);
            }
            addToGrid(list, gridPane);
            gridPane.setHgap(0);
            gridPane.setVgap(30);
            scrollReclamation.setContent(gridPane);
            reclamationsChildren = reclamations.getChildren();
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
    }

    private void addToGrid(List<Parent> list, GridPane gridPane) {
        int totalItems = list.size();
        int nbrItems = gridPane.getChildren().size();
        int nbrRows = (nbrItems % 2 == 0) ? nbrItems / 2 : (nbrItems + 1) / 2;

        if (nbrItems % 2 == 1) {// impaire
            if (list.size() > 0) {
                gridPane.add(list.get(0), 0, nbrRows - 1);
                list.remove(0);
            }
        }
        //paire
        for (int ligne = nbrRows; ligne < nbrRows + totalItems; ligne++) {
            if (list.size() > 0) {
                gridPane.add(list.get(0), 0, ligne);
                list.remove(0);
            } else {
                return;
            }
        }

    }
    
    

}
