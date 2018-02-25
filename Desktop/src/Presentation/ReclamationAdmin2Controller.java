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
    
    public static List<Reclamation> listeReclamations;
    public static ObservableList<Node> reclamationsChildren;
    public static ReclamationItemController ric;
    public static Reclamation ReclamationSelectionnee;
    
    @FXML
    private ScrollPane scrollReclamation;

    GridPane gridPane = new GridPane();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ReclamationService rs = new ReclamationService();

        listeReclamations = rs.getAllReclamations();
        List<Parent> list = new ArrayList<Parent>();
        ReclamationItemController.listeReclamations = this.listeReclamations;
        try {
            for (int i = 0; i < listeReclamations.size(); i++) {
                //ReclamationItemController.listeReclamations =  this.listeReclamations;
                ReclamationItemController.index = i;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ReclamationItem.fxml"));
                Parent root = loader.load();
                list.add(root);
            }
            addToGrid(list, gridPane);
            gridPane.setHgap(0);
            gridPane.setVgap(0);
            scrollReclamation.setContent(gridPane);
            ReclamationItemController.rac=this;
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
