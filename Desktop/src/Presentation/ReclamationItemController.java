/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Reclamation;
import Services.ProduitService;
import Services.ReclamationService;
import Utils.Enumerations;
import Utils.Enumerations.TypeReclamation;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class ReclamationItemController implements Initializable {
    
    public static List<Reclamation> listeReclamations;
    public static int index;
    private Reclamation r;
    public static ReclamationAdmin2Controller rac;
    
    @FXML
    private AnchorPane reclamation;
    @FXML
    private Pane idDescription;
    @FXML
    private Label id;
    @FXML
    private Pane buttons;
    @FXML
    private JFXButton buttonBP;
    @FXML
    private JFXButton buttonU;
    @FXML
    private JFXButton supprimerR;
    @FXML
    private Label date;
    @FXML
    private TextFlow descriptionT;
    @FXML
    private Text description;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        r = ReclamationAdmin2Controller.listeReclamations.get(index);
        description.setText(r.getDescription());
        date.setText(r.getDateCreation().toString().replace("T", " à "));
        id.setText(((Integer)r.getId()).toString());
        if (r.getType()==TypeReclamation.Boutique){
            buttonBP.setText("Voir Boutique");
        }else{
            buttonBP.setText("Voir Produit");
        }
    }    

    private void redirectProduit() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AjouterProduit.fxml"));
        ProduitService ps = new ProduitService();
        AjouterProduitController.voirProd=ps.chercherProduitParID(r.getProduit().getId());
        AjouterProduitController.voir=true;
        Stage s = Utils.getAnotherStage(loader, "voir produit ");
        s.initStyle(StageStyle.UNDECORATED);
        s.show();
        //ReclamationAdmin2Controller.reclamationsChildren.setAll(pane);
        
    }
    private void redirectBoutique() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Evaluation.fxml"));
        ReclamationAdmin2Controller.reclamationsChildren.setAll(pane);
    }
    private void redirectUser() throws IOException {
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Evaluation.fxml"));
        ReclamationAdmin2Controller.reclamationsChildren.setAll(pane);
    }

    @FXML
    private void voirBP(ActionEvent event) {
        if (r.getType()==TypeReclamation.Boutique){
            try {
                redirectBoutique();
            } catch (IOException ex) {
                Logger.getLogger(ReclamationItemController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else
            try {
                redirectProduit();
        } catch (IOException ex) {
            Logger.getLogger(ReclamationItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void VoirU(ActionEvent event) {
        try {
            redirectUser();
        } catch (IOException ex) {
            Logger.getLogger(ReclamationItemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void supprimer(ActionEvent event) {
        ReclamationService rs = new ReclamationService();
        System.out.println(r);
        rs.supprimerReclamation(getThis());
        rac.listeReclamations.remove(r);
        GridPane parent = (GridPane)reclamation.getParent();
        parent.getChildren().remove(reclamation);
    }
    
    private Reclamation getThis(){
        return this.r;
    }
}
