/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Produit;
import Services.ProduitService;
import java.net.URL;
import java.util.List;
import java.util.Observer;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author oussamahamidi
 */
public class ListProduitsController implements Initializable {
    @FXML
    private TableView<Produit> tableStock;
    @FXML
    private TableColumn<Produit, String> columnRef;
    @FXML
    private TableColumn<Produit, String> columnLib;
    @FXML
    private TableColumn<Produit, String> columnDesc;
    @FXML
    private TableColumn<Produit, Float> columnPrix;
    @FXML
    private Button btnReload;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Produit> data;
        data=FXCollections.observableArrayList();
        ProduitService ps= new ProduitService();
        Boutique bou = new Boutique();
        List<Produit> LP=ps.listerProduitsBoutique(bou.getId());
        System.out.println(LP.toString());
        LP.stream().forEach((j)->{
        data.add(j);
        });
        try{
            columnRef.setCellValueFactory(new PropertyValueFactory<>("reference"));
            columnLib.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            columnDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
            columnPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        tableStock.setItems(data);
        tableStock.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->showProduitDetails(newValue));
    }    

    @FXML
    private void loadFromDB(ActionEvent event) {
    }

    private void showProduitDetails(Produit newValue) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
