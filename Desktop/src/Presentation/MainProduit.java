/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import java.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author oussamahamidi
 */
public class MainProduit extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
<<<<<<< HEAD
        Parent root = FXMLLoader.load(getClass().getResource(("MenuProduits.fxml")));
=======
        Parent root = FXMLLoader.load(getClass().getResource(("AjouterProduit.fxml")));
>>>>>>> 864fbc9089767a627c77d40e77ea71ed5e0e580b
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
