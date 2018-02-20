/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Panier;
import Entities.ProduitPanier;
import Services.PanierService;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

/**
 * FXML Controller class
 *
 * @author monta
 */
public class PanierController implements Initializable {

    Panier panier = new Panier();
    
    private Pane content;

    @FXML
    private Label prixArticles;

    @FXML
    private Label prixLivraison;

    @FXML
    private Label totalTTC;
    private ScrollPane scrollPane;
    
        private AnchorPane anchorContent;
     
    
        GridPane gridPane = new GridPane();
 Panier p = new Panier();
 Double scrollHeight =0.0;
    @FXML
    private ScrollPane contacts;
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
        List<Parent> list = new ArrayList<Parent>();
        
        try {
            // TODO
            panier.setContenu(new ArrayList<ProduitPanier>());
            ProduitPanier pp = new ProduitPanier(1, 0, 22.321f, 1, "ref-art", "testArticle", "TESSSSSSSTTTTT AOEKFAEPFJAE PKAEO¨GAPGÄKGAEP¨GKAEP¨GKAE¨PG", 22.321f, "m", "bleu", "coton", 0.0f, new Boutique(), LocalDateTime.now(), null);
             ProduitPanier pp2 = new ProduitPanier(1, 0, 22.321f, 22, "ref-a22222rt", "tes22222tArticle", "TESSSSSSSTTTTT AOEKFAEPFJAE PKAEO¨GAPGÄKGAEP¨GKAEP¨GKAE¨PG", 22.321f, "m", "bleu", "coton", 0.0f, new Boutique(), LocalDateTime.now(), null);
            panier.getContenu().add(pp);
            panier.getContenu().add(pp2);
            panier.getContenu().add(pp);
            panier.getContenu().add(pp);
            panier.getContenu().add(pp2);
            panier.getContenu().add(pp);
            panier.getContenu().add(pp);
            panier.getContenu().add(pp);
//
            Float total =0.0f;
            ProduitPanierController.contenu = panier.getContenu();
            
            for (int i = 0; i < panier.getContenu().size(); i++) {
                ProduitPanierController.index = i;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ProduitPanier.fxml"));
                Parent root = loader.load();
                list.add(root);
                total += panier.getContenu().get(i).getPrix()*panier.getContenu().get(i).getQuantiteVendue();
            }
            prixArticles.setText(total.toString());
            totalTTC.setText(total.toString());
            addToGrid(list, gridPane);
            gridPane.setHgap(45);
            gridPane.setVgap(20);
            contacts.setPannable(true);
            contacts.setContent(gridPane);
            ProduitPanierController.pc = this;
        } catch (IOException ex) {
            Logger.getLogger(TestController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void modifierContenu(ProduitPanier produit)
    {
        panier.getContenu().remove(produit);
        panier.getContenu().add(produit);

    }

    public void modifierTotaux(float prix)
    {
        Float v = (Float.parseFloat(prixArticles.getText())+prix);
        prixArticles.setText(v.toString());
        v = (Float.parseFloat(totalTTC.getText())+prix);
        totalTTC.setText(v.toString());
    }
    

}
