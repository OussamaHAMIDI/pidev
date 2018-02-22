/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Services.BoutiqueService;
import Utils.NavigatorData;
import com.jfoenix.controls.JFXTextField;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
    @FXML
     private JFXTextField filter;
    @FXML
    private Button MapB;
    @FXML
    private Button fb;

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
       
    }   

         }    
         public static Boutique boutique=new Boutique();

    @FXML
    private void ajouterBoutique(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        boutique=tableboutique.getSelectionModel().getSelectedItem();
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("Boutique.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    @FXML
    private void modifierBoutique(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
            boutique=tableboutique.getSelectionModel().getSelectedItem();
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("InterfaceModifierBoutique.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            
    }
    

    @FXML
    private void supprimerBoutique(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation suppression");
        alert.setHeaderText("Voulez-vous vraiment supprimer la boutique ?");
        alert.setContentText("");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            
            Boutique B=new Boutique();
            B=tableboutique.getSelectionModel().getSelectedItem();
            BoutiqueService bt=new BoutiqueService();
            bt.supprimerBoutique(B);
    }
    
}

    @FXML
    private void MapBoutique(ActionEvent event) throws IOException {
        Boutique B=new Boutique();
            B=tableboutique.getSelectionModel().getSelectedItem();
            BoutiqueService bt=new BoutiqueService();
            B=bt.chercherBoutiqueParID(B.getId());
            
          

            
            NavigatorData.getInstance().setLat(B.getLat());
              NavigatorData.getInstance().setLong(B.getLong());
              NavigatorData.getInstance().setAdr(B.getAdresse());
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("BoutiqueMap.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            
    }

    @FXML
    private void partage(ActionEvent event) {
          Boutique B=new Boutique();
            B=tableboutique.getSelectionModel().getSelectedItem();
            BoutiqueService bt=new BoutiqueService();
            B=bt.chercherBoutiqueParID(B.getId());
       
               String accessToken = "EAACEdEose0cBALnh8FXxkOGAmDxrhvukgahtLksbuUITg8vydx3NFcBjAQnZBcXNFjmTIyFMIisUrB2gKFwSbzjYnnuMkOLySiQJRWe2kkXxlwZAbmYlkvZBKduoPXPqrhwWsgdfn9ivXJY4SweyM0ZBH2XZBZANgJNHLH3LaLMibaUhKejQdlA5H9taDsrQYZD";
       Scanner s = new Scanner(System.in);
        FacebookClient fbClient= new DefaultFacebookClient(accessToken);
         FacebookType response = fbClient.publish("me/feed", FacebookType.class,
                           Parameter.with("message", "Boutique"+B.getNom()+" at"+B.getAdresse()),
                           Parameter.with("link", "http://127.168.0.1/"));
         System.out.println("fb.com/"+response.getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("success");
        alert.setContentText("Votre annonce à été publié");
        alert.showAndWait();
        
    }
}
