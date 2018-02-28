/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Azza
 */
public class UneBoutiqueController implements Initializable {
    Boutique boutiqueB;
    Boutique bou;
    static public int index;
    static public List<Boutique> contenu;
    static public MenuController mc;
    
    @FXML
    private AnchorPane boutique;
    @FXML
    private Label userB;
    @FXML
    private Label adresseB;
    @FXML
    private Label nomB;
    @FXML
    private Label dateB;
    @FXML
    private JFXButton supprimerB;
    @FXML
    private JFXButton modifierB;

    /**
     * Initializes the controller class.
     */
    public AnchorPane getThis()
    {
        return boutique;
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        boutiqueB = contenu.get(index);
        bou= boutiqueB;
        dateB.setText(boutiqueB.getDateCreation().toString().replace("T", " "));
        nomB.setText(boutiqueB.getNom());
        userB.setText(boutiqueB.getIDUser()+"");
        adresseB.setText(boutiqueB.getAdresse());
        
    }    

    

    @FXML
    private void supprimerBoutique(ActionEvent event)  {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer la boutique ?");
        alert.setContentText("");
        
        GridPane parent = (GridPane)boutique.getParent();
        parent.getChildren().remove(boutique);
    }


    @FXML
    private void modifierBoutique(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("ModifierBoutique.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
    @FXML
    private void test(MouseEvent event) {
        System.out.println(boutiqueB.toString() + index);
    }
    
    
}
