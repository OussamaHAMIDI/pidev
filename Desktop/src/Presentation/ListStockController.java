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
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ArrayList;
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
    private TableView<affichage> tableStock;
    @FXML
    private TableColumn<affichage, String> columnRef;
    @FXML
    private TableColumn<affichage, String> columnLib;
    @FXML
    private TableColumn<affichage, Integer> columnQte;

    @FXML
    private JFXTextField test;
    @FXML
    private JFXButton modifier;
    StockService ss= new StockService();
    ProduitService ps = new ProduitService();
     Stock MS = new Stock();
     affichage selected = new affichage();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<affichage> data;
        data=FXCollections.observableArrayList();
        
        Boutique bou = new Boutique();
        bou.setId(94);
        
        MS=ss.getStock(bou.getId());

        List<Integer> ids = MS.getIds();
        List<Integer> quantites = MS.getQuantites();
        List<String> libelles = new ArrayList<String>();
        List<affichage> aff = new ArrayList<affichage>();
        for(Integer i : ids)
        {
            libelles.add(ps.chercherProduitParID(i).getLibelle());
        }
        for(int i=0;i<ids.size();i++)
        {
            aff.add(new affichage(
                    ids.get(i).toString(),
                    libelles.get(i),
                    quantites.get(i)
            ));
        }
//        
//        aff.add(new affichage("1","test 1",3));
//        aff.add(new affichage("2","test 2",6));
//        aff.add(new affichage("3","test 3",69));
        
        aff.forEach(
                (a)->{
                    data.add(a);
                });
       
        
        
        
        //
        /*MS.forEach((j,i)->{
        data.add();
        });*/
        //data.add(aff);
        
        try{
           
            columnRef.setCellValueFactory(new PropertyValueFactory<>("id"));
            columnLib.setCellValueFactory(new PropertyValueFactory<>("libelle"));
            columnQte.setCellValueFactory(new PropertyValueFactory<>("quantite"));
         
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }
        
        tableStock.setItems(data);
        tableStock.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue)->test(newValue));
    }
    private void test(affichage a)
    {
    test.setText("0");
    selected = a;
    }

    @FXML
    private void modifierStock(ActionEvent event) {
        ss.modifierStock(Integer.valueOf(selected.getId()), MS, Integer.valueOf(test.getText()));
        tableStock.getSelectionModel().getSelectedItem().quantite = tableStock.getSelectionModel().getSelectedItem().quantite + Integer.valueOf(test.getText());
        tableStock.refresh();
        
    }
    
    public class affichage {
        public String id;
        public String libelle;
        public Integer quantite;

         public affichage() {

        }
        public affichage(String id, String libelle, int quantite) {
            this.id = id;
            this.libelle = libelle;
            this.quantite = quantite;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLibelle() {
            return libelle;
        }

        public void setLibelle(String libelle) {
            this.libelle = libelle;
        }

        public Integer getQuantite() {
            return quantite;
        }

        public void setQuantite(Integer quantite) {
            this.quantite = quantite;
        }
        
    }
    
}
