/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.User;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Hamdi
 */
public class GestionUsersController2 implements Initializable {

    @FXML
    private AnchorPane blur;
    @FXML
    private JFXTextField filter;

   
    @FXML
    private ScrollPane users;
    
    GridPane gridPane = new GridPane();
    
    public static List<User> list;

    /**
     * Initializes the controller class.
     */
    private void addToGrid(List<Parent> list, GridPane gridPane) {
        int totalItems = list.size();
        int totalLignes = (totalItems % 2 == 0) ? totalItems / 2 : (totalItems + 1) / 2;
        int nbrItems = gridPane.getChildren().size();
        int nbrRows = (nbrItems % 2 == 0) ? nbrItems / 2 : (nbrItems + 1) / 2;

        if (nbrItems % 2 == 1) {// impaire
            if (list.size() > 0) {
                gridPane.add(list.get(0), 1, nbrRows - 1);
                list.remove(0);
            }
        }
        //paire
        for (int ligne = nbrRows; ligne < nbrRows + totalLignes; ligne++) {
            for (int col = 0; col < 2; col++) {
                if (list.size() > 0) {
                    gridPane.add(list.get(0), col, ligne);
                    list.remove(0);
                } else {
                    return;
                }
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Parent> parents = new ArrayList<>();
        
        try {

            for (int i = 0; i < list.size(); i++) {
                UserController.u = list.get(i);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("User.fxml"));
                Parent root = loader.load();
                parents.add(root);
            }

            addToGrid(parents, gridPane);

            gridPane.setHgap(45);
            gridPane.setVgap(20);

            users.setPannable(true);
            users.setContent(gridPane);
        } catch (IOException ex) {
            Logger.getLogger(TestController.class.getName()).log(Level.SEVERE, null, ex);
        }

        users.vvalueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.doubleValue() == users.getVmax()) {
                    System.out.println("AT BOTTOM");
                    // load more items
                    List<Parent> list2 = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        try {
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("User.fxml"));
                            Parent root = loader.load();
                            list2.add(root);
                        } catch (IOException ex) {
                            Logger.getLogger(TestController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    addToGrid(list2, gridPane);
                }
            }
        }
        );
    }
  
    
}
