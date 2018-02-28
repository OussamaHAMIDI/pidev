/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Evaluation;
import Entities.Produit;
import Entities.User;
import Services.EvaluationService;
import Services.ProduitService;
import Services.UserService;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class EvaluationController implements Initializable {

    @FXML
    private Rating evaluation;
    @FXML
    private Label note;
    @FXML
    private Button evaluer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        EvaluationService es = new EvaluationService();
        UserService us = new UserService();
        User u = us.getUserById(29);
        ProduitService ps = new ProduitService();
        Produit p = ps.chercherProduitParID(29);
        System.out.println(p);
        evaluation.setRating(es.getNoteProduit(p));
        if (es.peutEvaluer(u, p)) {
            evaluation.setDisable(false);
        }
        else {
            evaluation.setDisable(true);
        }
        evaluation.setPartialRating(false);
        evaluation.setMax(5);
        //evaluation.setUpdateOnHover(true);

        evaluation.ratingProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

                Evaluation e = new Evaluation(u, p, newValue.floatValue());
                es.ajouterEvaluation(e);
                note.setText("Votre evaluation : " + newValue.toString());

            }
        });
    }

    @FXML
    private void evaluer(ActionEvent event) {

        Evaluation e = new Evaluation();
        e.setNote((float) evaluation.getRating());
        System.out.println(e.getNote());
    }

}
