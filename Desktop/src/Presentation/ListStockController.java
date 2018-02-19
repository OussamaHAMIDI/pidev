/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Produit;
import Entities.Stock;
import Services.ProduitService;
import Services.StockService;
import java.net.URL;
import java.util.List;
import java.util.Map;
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
public class ListStockController implements Initializable {
    @FXML
    private TableView<StockService> tableStock;
    @FXML
    private TableColumn<StockService, String> columnRef;
    @FXML
    private TableColumn<StockService, String> columnLib;
    @FXML
    private TableColumn<StockService, String> columnPrix;
    @FXML
    private TableColumn<StockService, String> columnQte;
    @FXML
    private Button btnReload;
    @FXML
    private Button btnConfirm;
    @FXML
    private Button incrementer;
    @FXML
    private Button decrementer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<Map<Integer,Integer> > data;
        data=FXCollections.observableArrayList();
        StockService ss= new StockService();
        Boutique bou = new Boutique();
        Map<Integer,Integer> MS=ss.getStock(bou.getId()).getListStock();
        System.out.println(MS.toString());
        /*MS.forEach((j,i)->{
        data.add();
        });*/
        try{
            columnRef.setCellValueFactory(new PropertyValueFactory<>("reference"));
            columnLib.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            columnQte.setCellValueFactory(new PropertyValueFactory<>("quantite"));
            columnPrix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        //tableStock.setItems(data);
        //tableStock.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->showProduitDetails(newValue));
    }    

    @FXML
    private void loadFromDB(ActionEvent event) 
    {   
        
    }
    
}
