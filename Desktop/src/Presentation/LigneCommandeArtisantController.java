/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;
    
import Entities.ProduitPanier;
import static Presentation.ProduitPanierController.contenu;
import static Presentation.ProduitPanierController.index;
import Services.PanierService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author monta
 */
public class LigneCommandeArtisantController implements Initializable {

    @FXML
    private AnchorPane contact;
    @FXML
    private Label nom;
    @FXML
    private Label quantite;
    @FXML
    private Label prixTotal;
    @FXML
    private Label nom1;
    
    ProduitPanier produit;
    static public int index;
    static public List<ProduitPanier> contenu;
    
    static public CommandeArtisantController pc;
    @FXML
    private JFXCheckBox estLivree;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       produit = contenu.get(index);

       nom.setText(produit.getDescription());
        nom1.setText(produit.getReference() + " - " + produit.getLibelle());
        quantite.setText(String.valueOf(produit.getQuantiteVendue()));
        prixTotal.setText(String.valueOf(produit.getQuantiteVendue()*produit.getPrixVente()));
        estLivree.setSelected(produit.isLivree());
        if(produit.isLivree())
        {
            estLivree.setDisable(true);
        }
        //nom.setText("NomClient - IDClient");
    }    


    @FXML
    private void test(MouseEvent event) {
        if(!produit.isLivree())
        {
            produit.setLivree(true);
            estLivree.setDisable(true);
            PanierService ps = new PanierService();
            ps.modifierLigneCommande(produit, Integer.parseInt(produit.getTaille()));
        }
    }
    
}
