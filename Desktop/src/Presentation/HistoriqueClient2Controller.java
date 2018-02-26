/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Panier;
import Services.HistoriqueService;
import Services.UserService;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
    private ScrollPane scrollHistoriques;
    
    public static ObservableList<Node> historiquesChildren;
    GridPane gridPane = new GridPane();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        HistoriqueService hs = new HistoriqueService();
        List<Panier> listePaniers = new ArrayList<Panier>();
        UserService us = new UserService();
        listePaniers = hs.getHistoriqueUser(us.getUserById(29));
        List<Parent> list = new ArrayList<Parent>();
        try {
            for (int i = 0; i < listePaniers.size(); i++) {
                HistoriqueClientItemController.panierPasse = listePaniers.get(i);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("HistoriqueClientItem.fxml"));
                Parent root = loader.load();
                list.add(root);
            }
            addToGrid(list, gridPane);
            gridPane.setHgap(0);
            gridPane.setVgap(30);
            scrollHistoriques.setContent(gridPane);
            historiquesChildren = historiques.getChildren();
            
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
