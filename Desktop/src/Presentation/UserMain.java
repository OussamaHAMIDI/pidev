/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 *
 * @author hamdi
 */
public class UserMain extends Application {

    private double x;
    private double y;

    @Override
    public void start(Stage stage) {


//            FXMLLoader loader = new FXMLLoader(getClass().getResource("Accueil.fxml"));
//            Stage s = Utils.getAnotherStage(loader, "Application souk lemdina");
//            s.initStyle(StageStyle.TRANSPARENT);
//            
//            s.initStyle(StageStyle.DECORATED);
//            
//            s.show();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Accueil.fxml"));

            stage.setTitle("Application souk lemdina");

            root.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    x = event.getSceneX();
                    y = event.getSceneY();
                }
            });

            root.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                }
            });

            Scene scene = new Scene(root);
            //scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            stage.getIcons().add(new Image("Images/souk.png"));
            stage.centerOnScreen();
            stage.setResizable(false);
            stage.setMaximized(false);
            stage.setScene(scene);

            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showExceptionDialog(e);
        }

    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void showExceptionDialog(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Exception Dialog");
        alert.setHeaderText("An error occurred:");
        String content = "Error: ";
        if (null != e) {
            content += e.toString() + "\n\n";
        }
        alert.setContentText(content);
        Exception ex = new Exception(e);
        //Create expandable Exception.
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String exceptionText = sw.toString();
        //Set up TextArea
        TextArea textArea = new TextArea(exceptionText);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefHeight(600);
        textArea.setPrefWidth(800);
        //Définissez Exception extensible dans le volet de dialogue.
        alert.getDialogPane().setExpandableContent(textArea);

        alert.showAndWait();
    }
}
