package Presentation;

import Services.UserService;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class AccueilAdminController implements Initializable {

    @FXML
    private JFXButton btnHome;

    @FXML
    private JFXButton btnControls;

    @FXML
    private AnchorPane holderPane;
    @FXML
    private JFXButton btnUsers;

    AnchorPane controls, users;

 

    @FXML
    private void switchUsers(ActionEvent event) {
        setNode(users);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            GestionUsersController2.list = new UserService().getUsers();
            users = FXMLLoader.load(getClass().getResource("GestionUsers_1.fxml"));

            //controls = FXMLLoader.load(getClass().getResource("Controls.fxml"));
            setNode(users);
        } catch (IOException ex) {
            Logger.getLogger(AccueilAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    //Set selected node to a content holder

    private void setNode(Node node) {
        holderPane.getChildren().clear();
        holderPane.getChildren().add((Node) node);

        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }

}
