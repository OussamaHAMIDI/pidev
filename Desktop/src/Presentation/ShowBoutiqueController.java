/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Services.EvaluationService;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Hamdi
 */
public class ShowBoutiqueController implements Initializable {

    @FXML
    private Label titre;
    @FXML
    private JFXTextField nom;
    @FXML
    private JFXTextField date;
    @FXML
    private ImageView photo;
    @FXML
    private ImageView close;
    @FXML
    private Rating evaluation;
    @FXML
    private JFXTextField adresse;

    public static Boutique boutiqueSelected;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (boutiqueSelected != null) {
            nom.setText(boutiqueSelected.getNom());
            adresse.setText(boutiqueSelected.getAdresse());
            date.setText(boutiqueSelected.getDateCreation().toString().replace("T", " "));
            evaluation.setRating(new EvaluationService().getNoteBoutique(boutiqueSelected));

            boutiqueSelected = null;
        }

    }

    @FXML
    private void closeClicked(MouseEvent event) {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

}
