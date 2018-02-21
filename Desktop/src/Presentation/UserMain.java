<<<<<<< HEAD
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
import javafx.stage.StageStyle;

/**
 *
 * @author hamdi
 */
public class UserMain extends Application {

    private double x;
    private double y;
    //navigation nav = new navigation();

    @Override
    public void start(Stage stage) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("AccueilAdmin.fxml"));

            stage.setTitle("Login");
            
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
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            stage.getIcons().add(new Image("Images/souk.png"));
            stage.centerOnScreen();
            stage.setScene(scene);
         
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showExceptionDialog(e);
        }

    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        stage.setTitle("Validation Demo");
//        BorderPane borderPane = new BorderPane();
//
//        borderPane.setCenter(loadLoginScreen());
//        Scene scene = new Scene(borderPane, 700, 500);
//        scene.getStylesheets().add(
//                UserMain.class.getResource("context.css")
//                        .toExternalForm());
//        stage.setScene(scene);
//        stage.show();
//    }
//
// 
//
//    private GridPane loadLoginScreen() {
//
//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));
//
//        Text scenetitle = new Text("Welcome");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        grid.add(scenetitle, 0, 0, 2, 1);
//
//        Label userName = new Label("User Name:");
//        grid.add(userName, 0, 1);
//
//        final TextField userTextField = new TextField();
//        grid.add(userTextField, 1, 1);
//
//        Label pw = new Label("Password:");
//        grid.add(pw, 0, 2);
//
//        final PasswordField pwBox = new PasswordField();
//        grid.add(pwBox, 1, 2);
//
//        Button btn = new Button("Sign in");
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(btn);
//        grid.add(hbBtn, 1, 4);
//
//        final Text actiontarget = new Text();
//        grid.add(actiontarget, 1, 6);
//
//        // Context Menu for error messages
//        final ContextMenu usernameValidator = new ContextMenu();
//        usernameValidator.setAutoHide(false);
//        final ContextMenu passValidator = new ContextMenu();
//        passValidator.setAutoHide(false);
//
//        // Action on button press
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent e) {
//                // Clearing message if any
//                actiontarget.setText("");
//
//                // Checking if the userTextField is empty
//                if (userTextField.getText().equals("")) {
//                    usernameValidator.getItems().clear();
//                    usernameValidator.getItems().add(
//                            new MenuItem("Please enter username"));
//                    usernameValidator.show(userTextField, Side.RIGHT, 10, 0);
//                }
//                // Checking if the pwBox is empty
//                if (pwBox.getText().equals("")) {
//                    passValidator.getItems().clear();
//                    passValidator.getItems().add(
//                            new MenuItem("Please enter Password"));
//                    passValidator.show(pwBox, Side.RIGHT, 10, 0);
//                }
//                // If both of the above textFields have values
//                if (!pwBox.getText().equals("")
//                        && !userTextField.getText().equals("")) {
//                    actiontarget.setFill(Color.GREEN);
//                    actiontarget.setText("Welcome");
//                }
//            }
//        });
//
//        userTextField.focusedProperty().addListener(
//                new ChangeListener<Boolean>() {
//                    @Override
//                    public void changed(
//                            ObservableValue<? extends Boolean> arg0,
//                            Boolean oldPropertyValue, Boolean newPropertyValue) {
//                        if (newPropertyValue) {
//                            // Clearing message if any
//                            actiontarget.setText("");
//                            // Hiding the error message
//                            usernameValidator.hide();
//                        }
//                    }
//                });
//
//        pwBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> arg0,
//                    Boolean oldPropertyValue, Boolean newPropertyValue) {
//                if (newPropertyValue) {
//                    // Clearing message if any
//                    actiontarget.setText("");
//                    // Hiding the error message
//                    passValidator.hide();
//                }
//            }
//        });
//        return grid;
//    }

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
=======
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
import javafx.stage.StageStyle;

/**
 *
 * @author hamdi
 */
public class UserMain extends Application {

    private double x;
    private double y;
    //navigation nav = new navigation();

    @Override
    public void start(Stage stage) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("AccueilAdmin.fxml"));

            //Parent root = FXMLLoader.load(getClass().getResource("Test.fxml"));

            stage.setTitle("Login");
            
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
            scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
            stage.getIcons().add(new Image("Images/souk.png"));
            stage.centerOnScreen();
            stage.setScene(scene);
         
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            showExceptionDialog(e);
        }

    }

//    @Override
//    public void start(Stage stage) throws Exception {
//        stage.setTitle("Validation Demo");
//        BorderPane borderPane = new BorderPane();
//
//        borderPane.setCenter(loadLoginScreen());
//        Scene scene = new Scene(borderPane, 700, 500);
//        scene.getStylesheets().add(
//                UserMain.class.getResource("context.css")
//                        .toExternalForm());
//        stage.setScene(scene);
//        stage.show();
//    }
//
// 
//
//    private GridPane loadLoginScreen() {
//
//        GridPane grid = new GridPane();
//        grid.setAlignment(Pos.CENTER);
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(25, 25, 25, 25));
//
//        Text scenetitle = new Text("Welcome");
//        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//        grid.add(scenetitle, 0, 0, 2, 1);
//
//        Label userName = new Label("User Name:");
//        grid.add(userName, 0, 1);
//
//        final TextField userTextField = new TextField();
//        grid.add(userTextField, 1, 1);
//
//        Label pw = new Label("Password:");
//        grid.add(pw, 0, 2);
//
//        final PasswordField pwBox = new PasswordField();
//        grid.add(pwBox, 1, 2);
//
//        Button btn = new Button("Sign in");
//        HBox hbBtn = new HBox(10);
//        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
//        hbBtn.getChildren().add(btn);
//        grid.add(hbBtn, 1, 4);
//
//        final Text actiontarget = new Text();
//        grid.add(actiontarget, 1, 6);
//
//        // Context Menu for error messages
//        final ContextMenu usernameValidator = new ContextMenu();
//        usernameValidator.setAutoHide(false);
//        final ContextMenu passValidator = new ContextMenu();
//        passValidator.setAutoHide(false);
//
//        // Action on button press
//        btn.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent e) {
//                // Clearing message if any
//                actiontarget.setText("");
//
//                // Checking if the userTextField is empty
//                if (userTextField.getText().equals("")) {
//                    usernameValidator.getItems().clear();
//                    usernameValidator.getItems().add(
//                            new MenuItem("Please enter username"));
//                    usernameValidator.show(userTextField, Side.RIGHT, 10, 0);
//                }
//                // Checking if the pwBox is empty
//                if (pwBox.getText().equals("")) {
//                    passValidator.getItems().clear();
//                    passValidator.getItems().add(
//                            new MenuItem("Please enter Password"));
//                    passValidator.show(pwBox, Side.RIGHT, 10, 0);
//                }
//                // If both of the above textFields have values
//                if (!pwBox.getText().equals("")
//                        && !userTextField.getText().equals("")) {
//                    actiontarget.setFill(Color.GREEN);
//                    actiontarget.setText("Welcome");
//                }
//            }
//        });
//
//        userTextField.focusedProperty().addListener(
//                new ChangeListener<Boolean>() {
//                    @Override
//                    public void changed(
//                            ObservableValue<? extends Boolean> arg0,
//                            Boolean oldPropertyValue, Boolean newPropertyValue) {
//                        if (newPropertyValue) {
//                            // Clearing message if any
//                            actiontarget.setText("");
//                            // Hiding the error message
//                            usernameValidator.hide();
//                        }
//                    }
//                });
//
//        pwBox.focusedProperty().addListener(new ChangeListener<Boolean>() {
//            @Override
//            public void changed(ObservableValue<? extends Boolean> arg0,
//                    Boolean oldPropertyValue, Boolean newPropertyValue) {
//                if (newPropertyValue) {
//                    // Clearing message if any
//                    actiontarget.setText("");
//                    // Hiding the error message
//                    passValidator.hide();
//                }
//            }
//        });
//        return grid;
//    }

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
>>>>>>> a5ce230d1d08a0a50304308bcb6a7ec628b643e2
