/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.ProduitPanier;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class HistoriqueProduitPanierItemController implements Initializable {
    
    public static List<ProduitPanier> listeProduitsPanier;
    public static int index;
    private ProduitPanier pp;
    public static HistoriqueClient2Controller hcc;
    
    @FXML
    private ImageView imageProduit;
    @FXML
    private Label quantite;
    @FXML
    private Label reference;
    @FXML
    private Label description;
    @FXML
    private Label prix;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        pp = HistoriqueClient2Controller.listeProduitsPanier.get(index);
        System.out.println("ITEM LIST =============="+pp);
        description.setText(pp.getDescription());
        reference.setText(pp.getReference());
        quantite.setText(((Float)pp.getQuantiteVendue()).toString());
        prix.setText(((Float)pp.getPrixVente()).toString());
        imageProduit.setImage(null);
        
        // TODO
    }    
    
    
}
