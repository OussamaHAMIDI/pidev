/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Services.BoutiqueService;
import Utils.NavigatorData;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Azza
 */
public class MenuController implements Initializable {
    Boutique boutique = new Boutique();
    private Pane content;
    @FXML
    private Label idB;
    @FXML
    private Label dateB;
    @FXML
    private Label adresseB;
    @FXML
    private Label NomB;
    @FXML
    private JFXButton MapB;
    @FXML
    private JFXButton produitB;
    @FXML
    private ScrollPane boutiques;
    @FXML
    private JFXTextField filter;

    /**
     * Initializes the controller class.
     */
    private void addToGrid(List<Parent> list, GridPane gridPane) {
        int totalItems = list.size();
        int totalLignes = (totalItems % 2 == 0) ? totalItems / 2 : (totalItems + 1) / 2;
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
//            for (int col = 0; col < 2; col++) {
            if (list.size() > 0) {
                gridPane.add(list.get(0), 0, ligne);
                list.remove(0);
            } else {
                return;
            }
//            }
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

  

    @FXML
    private void MapBoutique(ActionEvent event) throws IOException {
//         Boutique B=new Boutique();
//            B=boutiques.
//            BoutiqueService bt=new BoutiqueService();
//            B=bt.chercherBoutiqueParID(B.getId());
//            
//          
//
//            
//            NavigatorData.getInstance().setLat(B.getLat());
//              NavigatorData.getInstance().setLong(B.getLong());
//              NavigatorData.getInstance().setAdr(B.getAdresse());
//            Stage stage=new Stage();
//            Parent root = FXMLLoader.load(getClass().getResource("BoutiqueMap.fxml"));
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();

    }

    @FXML
    private void afficherProduit(ActionEvent event) {
    }

    @FXML
    private void ajouterBoutique(MouseEvent event) {
    }

    
}
