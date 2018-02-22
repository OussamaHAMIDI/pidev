/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Services.BoutiqueService;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Azza
 */
public class InterfaceBoutiqueController implements Initializable {

    @FXML
    private TableView<Boutique> tableboutique;
    @FXML
    private TableColumn<Boutique, String> idboutique;
    @FXML
    private TableColumn<Boutique, String> nomboutique;
    @FXML
    private Label nomBoutique;
    @FXML
    private Label userBoutique;
    @FXML
    private Label adresseBoutique;
    @FXML
    private Label dateBoutique;
    @FXML
    private Button ajoutB;
    @FXML
    private Button modifierB;
    @FXML
    private Button supprimerB;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Boutique> data ;
       data = FXCollections.observableArrayList();
        BoutiqueService gs=new BoutiqueService();
        List<Boutique> LC = gs.lireBoutiques();
        System.out.println(LC.toString());
         LC.stream().forEach((j) -> {
            data.add(j);
        });
     
    try{
            idboutique.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomboutique.setCellValueFactory(new PropertyValueFactory<>("nom"));
}catch(Exception e) {
        System.out.println(e.getMessage());
}
        
        
            tableboutique.setItems(data);
        tableboutique.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> showBoutiqueDetails(newValue));
    }   
    
         private void showBoutiqueDetails(Boutique a) {
     
    if (a != null) {
        dateBoutique.setText(a.getDateCreation().toString().replace("T", " "));
        nomBoutique.setText(a.getNom());
        userBoutique.setText(a.getIDUser()+"");
        adresseBoutique.setText(a.getAdresse());
     
        
    } else {
        
        userBoutique.setText("");
        nomBoutique.setText("");
        dateBoutique.setText("");
        adresseBoutique.setText("");
       
    }    }    

    @FXML
    private void ajouterBoutique(ActionEvent event) {
    }

    @FXML
    private void modifierBoutique(ActionEvent event) {
    }

    @FXML
    private void supprimerBoutique(ActionEvent event) {
    }
    
}
