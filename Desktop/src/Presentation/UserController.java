/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.User;
import Services.UserService;
import Utils.Enumerations;
import Utils.Enumerations.TypeUser;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * FXML Controller class
 *
 * @author Hamdi
 */
public class UserController implements Initializable {

    
    

    @FXML
    private Label nomPrenom;
    @FXML
    private Label tel;
    @FXML
    private Label typeUser;
    @FXML
    private Label adresse;
    @FXML
    private JFXButton supprimer;
    @FXML
    private ImageView photoUser;
    @FXML
    private Label email;
    @FXML
    private AnchorPane user;
    @FXML
    private Circle circle;
    
    
    private User u;
    static public int index;
    static public List<User> contenu;
    public static GestionUsersController guc;
    

    public void setValues(User u) {
        typeUser.setText(u.getType().toString());
        adresse.setText(u.getAdresse());
        nomPrenom.setText(u.getNom() + " " + u.getPrenom());
        tel.setText(u.getTel());
        email.setText(u.getEmail());
        circle.setStroke(Color.SEAGREEN);
        circle.setFill(Color.SNOW);
        circle.setEffect(new DropShadow(+25d, 0d, +2d, Color.DARKSEAGREEN));
        circle.setFill(new ImagePattern(new Image("Images/user.png")));
        if (u.getPhoto() != null) {
            circle.setFill(new ImagePattern(new UserService().getPhoto(u.getId())));
            photoUser.setVisible(false);
            //photoUser.setImage(new Image(u.getPhoto()));
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        u = GestionUsersController.list.get(index);
        //GestionUsersController.userCont = this;
        setValues(u);
        if(u.getType() == TypeUser.Administrateur){
            supprimer.setVisible(false);
        }

    }

    @FXML
    private void voirUser(MouseEvent event) {
        GestionUsersController.userSelected = u;
        guc.setValues(u,"Informations compte");
        guc.voir();
    }

    @FXML
    private void supprimerUser(MouseEvent event) {
        GestionUsersController.list.remove(u);
        contenu.remove(u);
        //new UserService().supprimerUser(u);
        GridPane parent = (GridPane)user.getParent();
        parent.getChildren().remove(user);
        guc.setValues(new User(0, "", "", Enumerations.EtatUser.Active, TypeUser.Client, "", "", null,
        "Male", "", "", "", null, "", "", ""),"");
        guc.disable(true);
    }

    @FXML
    private void editerUser(MouseEvent event) {
        GestionUsersController.userSelected = u;
        GestionUsersController.uc = this;
        guc.setValues(u,"Modifier compte");
        guc.disable(false);
        
        
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierUser.fxml"));
//        Stage stage = Utils.getAnotherStage(loader, "Modification");
//        stage.initStyle(StageStyle.TRANSPARENT);
//        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            @Override
//            public void handle(WindowEvent we) {
//                user.setEffect(null);
//            }
//        });
//        stage.show();
    }

}
