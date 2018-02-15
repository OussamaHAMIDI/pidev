/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

/**
 *
 * @author Hamdi
 */
import Entities.User;
import Services.UserService;
import Utils.Enumerations.EtatUser;
import Utils.Utils;
import Utils.mouseDrag;
import Utils.time;
import animasi.FadeInRightTransition;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import tray.notification.NotificationType;


public class HomeAdminController implements Initializable {

    UserService us = new UserService();
    User u;

    @FXML
    private StackPane trans;

    @FXML
    private AnchorPane loadPane;

    @FXML
    private AnchorPane blur;

    @FXML
    private AnchorPane header;

    @FXML
    private Label username;

    @FXML
    private Label emailUser;

    @FXML
    private ImageView imageUser;

    @FXML
    private Label date;

    @FXML
    private Label heure;

    @FXML
    private Label usernameUser;

    @FXML
    private Label idUser;

    @FXML
    private Label levelUser;

    @FXML
    private Label minimize;

    @FXML
    private Label exit;

    @FXML
    private VBox operator;

    @FXML
    private JFXButton btn_home;

    @FXML
    private VBox user;
    @FXML
    private ImageView test;

    @FXML
    void btn_Close(ActionEvent event) {

    }

    @FXML
    void handleExitClicked(MouseEvent event) {

    }

    @FXML
    void handleMinimizeClicked(MouseEvent event) {

    }

    @FXML
    void homeClicked(ActionEvent event) {

    }

    @FXML
    void imageHover(MouseEvent event) {
        imageUser.setCursor(Cursor.HAND);
    }

    @FXML
    void logout(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setHeaderText(null);
        alert.setContentText("Voulez-vous se déconnecter ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Utils.showTrayNotification(NotificationType.CUSTOM, "Déconnexion", null, "Au revoir " + username.getText(), new Image(u.getPhoto()),2000);
            us.modifierEtatUser(Integer.getInteger(idUser.getText()), EtatUser.Disconnected);
           
            Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage2.hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("Login.fxml"));
            try {
                loader.load();
            } catch (Exception e) {
            }

            Parent p = loader.getRoot();
            Stage stage = new Stage();
            Scene pp = new Scene(p);
            pp.setFill(javafx.scene.paint.Color.TRANSPARENT);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setScene(pp);
            stage.setTitle("Home");
            stage.getIcons().add(new Image("Images/souk.png"));
            mouseDrag md = new mouseDrag();
            md.setDragged(p, stage);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {                                             
                            System.exit(0);
                        }
                    });
                }
            });
            stage.show();
        }

    }

    @FXML
    void setBackgroundHome(MouseEvent event) {
        btn_home.setStyle("-fx-background-color: #D2D7D3");
    }

    @FXML
    void setDefault(MouseEvent event) {
        exit.setStyle("-fx-background-color:  #4183D7;");
    }

    @FXML
    void setDefault2(MouseEvent event) {
        minimize.setStyle("-fx-background-color:  #4183D7;");
    }

    @FXML
    void setHover(MouseEvent event) {
        exit.setStyle("-fx-background-color: red;");
    }

    @FXML
    void setHover2(MouseEvent event) {
        minimize.setStyle("-fx-background-color: red;");
    }

    @FXML
    void userClicked(MouseEvent event) {
        try {
            FXMLLoader Loader = new FXMLLoader();
            Loader.setLocation(getClass().getResource("addUser.fxml"));
            blur.setEffect(new GaussianBlur(10));
            new FadeInRightTransition(trans).play();
            AnchorPane pane = Loader.load();
          
            loadPane.getChildren().setAll(pane);
        } catch (IOException ex) {
            Logger.getLogger(HomeAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void bindToTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Calendar time = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                        heure.setText(simpleDateFormat.format(time.getTime()));
                    }
                }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bindToTime();
        date.setText(LocalDate.now().toString());
        time time = new time();
        heure.setText(time.tanggal());
        imageUser.setCursor(Cursor.HAND);

    }

    private void setValueModel() {
        u = us.getUserById(Integer.parseInt(idUser.getText()));
        emailUser.setText(u.getEmail());
        username.setText(u.getUserName());
        levelUser.setText(u.getType().toString());
        imageUser.setImage(new Image(u.getPhoto()));

    }

    public void setValue(int id) {
        idUser.setText(String.valueOf(id));
        setValueModel();
    }

}
