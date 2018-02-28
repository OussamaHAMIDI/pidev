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
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author monta
 */
public class CommandeArtisantController implements Initializable {

   
    @FXML
    private ScrollPane contacts;
 GridPane gridPane = new GridPane();
 Panier p = new Panier();
 Double scrollHeight =0.0;
  Panier panier = new Panier();
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
//            ProduitPanier pp = new ProduitPanier(1, 0, 22.321f, 5, "ref-art", "testArticle", "TESSSSSSSTTTTT AOEKFAEPFJAE PKAEO¨GAPGÄKGAEP¨GKAEP¨GKAE¨PG", 22.321f, "m", "bleu", "coton", 0.0f, new Boutique(), LocalDateTime.now(), null);
//            ProduitPanier pp2 = new ProduitPanier(1, 0, 22.321f, 5, "ref-a22222rt", "tes22222tArticle", "TESSSSSSSTTTTT AOEKFAEPFJAE PKAEO¨GAPGÄKGAEP¨GKAEP¨GKAE¨PG", 22.321f, "m", "bleu", "coton", 0.0f, new Boutique(), LocalDateTime.now(), null);
            PanierService ps = new PanierService();
//            pp.setDescription(ps.rechercherArtisantProduitPanier(pp.getId()));
//            pp2.setDescription(ps.rechercherArtisantProduitPanier(pp2.getId()));
//            panier.getContenu().add(pp2);
//            panier.getContenu().add(pp2);
//            panier.getContenu().add(pp);
//            panier.getContenu().add(pp2);
//            panier.getContenu().add(pp2);
//            panier.getContenu().add(pp);
//            panier.getContenu().add(pp2);
//            panier.getContenu().add(pp2);
//panier = ps.rechercherPanierById(2);
panier.setId(2);
panier.setContenu(ps.rechercherCommandeArtisant(5));
for(ProduitPanier p: panier.getContenu())
{
    p.setDescription(ps.rechercheClientPanier(panier.getId()));
}
//
            Float total =0.0f;
            LigneCommandeArtisantController.contenu = panier.getContenu();
            
            for (int i = 0; i < panier.getContenu().size(); i++) {
                LigneCommandeArtisantController.index = i;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("LigneCommandeArtisant.fxml"));
                Parent root = loader.load();
                list.add(root);
            }
            addToGrid(list, gridPane);
            gridPane.setHgap(0);
            gridPane.setVgap(0);
            contacts.setPannable(true);
            contacts.setContent(gridPane);
            LigneCommandeArtisantController.pc = this;

        } catch (IOException ex) {
            Logger.getLogger(CommandeArtisantController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }    

    
}
