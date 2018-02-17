/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Panier;
import Entities.ProduitPanier;
import Services.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.layout.*;

/**
 * FXML Controller class
 *
 * @author monta
 */
public class PanierController implements Initializable {

    @FXML
    private Pane content;

    @FXML
    private Label prixArticles;

    @FXML
    private Label prixLivraison;

    @FXML
    private Label totalTTC;
    @FXML
    private ScrollPane scrollPane;
    
     @FXML
        private AnchorPane anchorContent;
     
    @FXML
    private TitledPane titledPane;
 Panier p = new Panier();
 Double scrollHeight =0.0;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        try {

            PanierService ps = new PanierService();
           
            Boutique b = new Boutique();
            prixArticles.setText("0.0");
            p = ps.rechercherPanierById(1);
            prixLivraison.setText("0.0");
            totalTTC.setText(prixArticles.getText());

            p.getContenu().forEach((x) -> {
                content.getChildren().add(ajouterLigne(x));
            });

            //scrollPane.setFitToHeight(true);
           //scrollPane.resize(100, 1980);
           // scrollPane.setPrefViewportHeight(0);

        } catch (Exception e) {
            System.err.println("Erreur ControllerPanier : " + e.getMessage());
        }

    }

    public Pane ajouterLigne(ProduitPanier produit) {
        Pane PaneExample = new Pane();
        ImageView imageExample = new ImageView();
        Label descriptionExample = new Label();
        Label referenceExample = new Label();
        Label prixUnitaireExample = new Label();
        Label prixTotalExample = new Label();
        Button plusExample = new Button();
        Button moinsExample = new Button();
        TextField quantiteExample = new TextField();
        Button supprimerExample = new Button();
        try {

            scrollPane.resize(445, scrollHeight+280);
            scrollHeight+=280;
            content.resize(455, scrollHeight+280);
            anchorContent.resize(455, scrollHeight+280);
            int number = 0;

            if (content.getChildren() != null) {
                number = content.getChildren().size() - 1;
            }
            //scrollPane.setPrefViewportHeight(number*250.0);
            PaneExample.layoutYProperty().set(number * 130.0 + 150.0);
            PaneExample.layoutXProperty().set(5.0);
            PaneExample.prefHeight(132.0);
            PaneExample.prefWidth(340.0);
            PaneExample.styleProperty().set("-fx-background-color: #fff");

            imageExample = new ImageView();
            imageExample.fitHeightProperty().set(101.0);
            imageExample.fitWidthProperty().set(96.0);
            imageExample.pickOnBoundsProperty().set(true);
            imageExample.preserveRatioProperty().set(true);
            imageExample.layoutXProperty().set(18.0);
            imageExample.layoutYProperty().set(16.0);
            //imageExample.imageProperty().set(produit.getImage());

            PaneExample.getChildren().add(imageExample);

            referenceExample.layoutXProperty().set(114.0);
            referenceExample.layoutYProperty().set(14.0);
            referenceExample.textProperty().set(produit.getReference());

            PaneExample.getChildren().add(referenceExample);

            descriptionExample.prefHeight(49.0);
            descriptionExample.prefWidth(160.0);
            descriptionExample.layoutXProperty().set(114.0);
            descriptionExample.layoutYProperty().set(42.0);
            descriptionExample.textProperty().set(produit.getDescription());

            PaneExample.getChildren().add(descriptionExample);

            prixUnitaireExample.layoutXProperty().set(114.0);
            prixUnitaireExample.layoutYProperty().set(91.0);
            prixUnitaireExample.textProperty().set(String.valueOf(produit.getPrixVente()));

            PaneExample.getChildren().add(prixUnitaireExample);

            prixTotalExample.layoutXProperty().set(201.0);
            prixTotalExample.layoutYProperty().set(91.0);
            prixTotalExample.textProperty().set("TND");

            PaneExample.getChildren().add(prixTotalExample);

            plusExample.layoutXProperty().set(284.0);
            plusExample.layoutYProperty().set(14.0);
            plusExample.textProperty().set("+");
            plusExample.mnemonicParsingProperty().set(false);

            PaneExample.getChildren().add(plusExample);

            moinsExample.layoutXProperty().set(284.0);
            moinsExample.layoutYProperty().set(54.0);
            moinsExample.textProperty().set("-");
            moinsExample.mnemonicParsingProperty().set(false);
            moinsExample.prefHeight(plusExample.getHeight());
            moinsExample.prefWidth(plusExample.getWidth());

            PaneExample.getChildren().add(moinsExample);

            supprimerExample.layoutXProperty().set(337.0);
            supprimerExample.layoutYProperty().set(85.0);
            supprimerExample.textProperty().set("🗑");
            supprimerExample.mnemonicParsingProperty().set(false);

            PaneExample.getChildren().add(supprimerExample);

            quantiteExample.prefHeight(25.0);
            quantiteExample.prefWidth(20.0);
            quantiteExample.maxWidth(80);
            quantiteExample.layoutXProperty().set(317.0);
            quantiteExample.layoutYProperty().set(34.0);
            quantiteExample.textProperty().set("1");
            quantiteExample.setDisable(true);
            
            PaneExample.getChildren().add(quantiteExample);

            moinsExample.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    if (Integer.parseInt(quantiteExample.getText()) != 1) {
                        Integer i = Integer.parseInt(quantiteExample.getText()) - 1;
                        quantiteExample.setText(i.toString());
                        Float f = (Float.parseFloat(prixArticles.getText()) - Float.parseFloat(prixUnitaireExample.getText()));
                        prixArticles.setText(f.toString());
                        f = (Float.parseFloat(totalTTC.getText()) - Float.parseFloat(prixUnitaireExample.getText()));
                        totalTTC.setText(f.toString());
                        produit.setQuantiteVendue(produit.getQuantiteVendue()-1);
                    }
                }
            });
            plusExample.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Integer i = Integer.parseInt(quantiteExample.getText()) + 1;
                    quantiteExample.setText(i.toString());
                    Float f = (Float.parseFloat(prixArticles.getText()) + Float.parseFloat(prixUnitaireExample.getText()));
                    prixArticles.setText(f.toString());
                    f = (Float.parseFloat(totalTTC.getText()) + Float.parseFloat(prixUnitaireExample.getText()));
                    totalTTC.setText(f.toString());
                    produit.setQuantiteVendue(produit.getQuantiteVendue()+1);
                }
            });

            supprimerExample.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    Pane parent = (Pane) PaneExample.getParent();
                    parent.getChildren().remove(PaneExample);
                    Float f = (Float.parseFloat(prixArticles.getText()) - ((Float.parseFloat(quantiteExample.getText()) * Float.parseFloat(prixUnitaireExample.getText()))));
                    prixArticles.setText(f.toString());
                    f = f + (Float.parseFloat(prixLivraison.getText()));
                    totalTTC.setText(f.toString());
                    fixPanier();
                    p.getContenu().remove(produit);

                    
                }
            });
            Float f = (Float.parseFloat(prixArticles.getText()) + ((Float.parseFloat(quantiteExample.getText()) * Float.parseFloat(prixUnitaireExample.getText()))));
            prixArticles.setText(f.toString());
            f = f + (Float.parseFloat(prixLivraison.getText()));
            totalTTC.setText(f.toString());

        } catch (Exception e) {
            System.out.println("Erreur Ajout ligne : " + e.getMessage());
        }
        return PaneExample;
    }

    public void fixPanier() {
        int number = 0;

        if (content.getChildren() != null) {
            number = content.getChildren().size();
        }
        int i = 0;
        for (Node p : content.getChildren()) {

            p.setLayoutY(i * 130.0 + 10);
            i++;
        }
    }
}
