/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Services.BoutiqueService;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Azza
 */
public class ModifierAdresseBoutiqueController implements Initializable {

    @FXML
    private JFXTextField idBoutique;
    @FXML
    private JFXTextField adresseBoutique;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void modifierAdresse(ActionEvent event) {
        BoutiqueService bs = new BoutiqueService();
        Boutique boutique = new Boutique();
        boutique.setId(Integer.parseInt(idBoutique.getText()));
        boutique.setAdresse(adresseBoutique.getText());
        bs.modifierAdresseBoutique(boutique, adresseBoutique.getText());
    }
     @FXML
    private void annulerAd(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("InterfaceModifierBoutique.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }
}