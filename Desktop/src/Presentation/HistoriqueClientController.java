/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Panier;
import Entities.Produit;
import Entities.ProduitPanier;
import Services.EvaluationService;
import Services.HistoriqueService;
import Services.PanierService;
import Services.UserService;
import Utils.Enumerations;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class HistoriqueClientController implements Initializable {

    @FXML
    private TableView<Panier> historiqueClient;
    @FXML
    private TableColumn<Panier, Integer> idCommande = new TableColumn<>("id");
    @FXML
    private TableColumn<Panier, LocalDateTime> date = new TableColumn<>("dateCreation");
    @FXML
    private TableColumn<Panier, Float> totalTTC = new TableColumn<>("totalTTC");
    @FXML
    private TableColumn<Panier, String> modePaiement = new TableColumn<>("modePaiement");
    @FXML
    private TableColumn<Panier, String> status = new TableColumn<>("status");
    @FXML
    private TableColumn<Panier, Boolean> details = new TableColumn<>("details");

    @FXML
    private AnchorPane anchorHistorique;
    
    public static List<ProduitPanier> produitsPasse;

    private HistoriqueService hs = new HistoriqueService();
    private PanierService ps = new PanierService();
    private UserService us = new UserService();
    private EvaluationService es = new EvaluationService();

    private ObservableList<Panier> historique = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     *
     *
     */

    Callback<TableColumn<Panier, Boolean>, TableCell<Panier, Boolean>> cellFactory;

    public HistoriqueClientController() {
        this.cellFactory = new Callback<TableColumn<Panier, Boolean>, TableCell<Panier, Boolean>>() {
            @Override
            public TableCell<Panier, Boolean> call(final TableColumn<Panier, Boolean> param) {
                final TableCell<Panier, Boolean> cell;
                cell = new TableCell<Panier, Boolean>() {
                    Image imgDetails = new Image(getClass().getResourceAsStream("/Images/info.png"));
                    final Button btnDetails = new Button();
                    
                    @Override
                    public void updateItem(Boolean check, boolean empty) {
                        super.updateItem(check, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btnDetails.setCursor(Cursor.HAND);
                            btnDetails.setOnAction((ActionEvent e) -> {
                                System.out.println("Bouton Cliqué , rediriger");
                                Panier panierCourant = getTableView().getItems().get(getIndex());
                                produitsPasse = panierCourant.getContenu();
                                
                                /*ModifierUserController auc = loader.getController();
                                
                                Stage stage = Utils.getAnotherStage(loader, "ProduiHistoriqueodification.fxml");
                                stage.show();*/
                                try {
                                    redirect();
                                } catch (IOException ex) {
                                    Logger.getLogger(HistoriqueClientController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            });
                            
                            btnDetails.setStyle("-fx-background-color: transparent;");
                            ImageView iv = new ImageView();
                            iv.setImage(imgDetails);
                            iv.setPreserveRatio(true);
                            iv.setSmooth(true);
                            iv.setCache(true);
                            btnDetails.setGraphic(iv);
                            
                            setGraphic(btnDetails);
                            setAlignment(Pos.CENTER);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // TODO
        setColumnProperties();
        setHistoriqueDetails();
    }

    private void redirect() throws IOException {
        //TODO
        AnchorPane pane = FXMLLoader.load(getClass().getResource("HistoriqueProduit.fxml"));
        anchorHistorique.getChildren().setAll(pane);
    }

    private void setColumnProperties() {

        date.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDateTime>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            @Override
            public String toString(LocalDateTime date) {
                if (date != null) {
                    return formatter.format(date).replace("T", " ");
                } else {
                    return "Jamais";
                }
            }

            @Override
            public LocalDateTime fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDateTime.parse(string, formatter);
                } else {
                    return null;
                }
            }
        }));
        idCommande.setCellValueFactory(new PropertyValueFactory<>("id"));
        totalTTC.setCellValueFactory(new PropertyValueFactory<>("totalTTC"));
        modePaiement.setCellValueFactory(new PropertyValueFactory<>("modePaiement"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        details.setCellFactory(cellFactory);
    }

    private void setHistoriqueDetails() {

        //Evaluation e = es.getEvaluationById(3);
        //LocalDateTime now = e.getDateCreation();
        //System.out.println(now.toString());
        historique.clear();
        //ProduitPanier pp1 = new ProduitPanier();
        //ProduitPanier pp2 = new ProduitPanier();
        //pp1.setReference("hellou");
        //pp2.setPrixVente(2500);
        //List<ProduitPanier> pps = new ArrayList<ProduitPanier>();
        //pps.add(pp1); pps.add(pp2);
        Panier panier1 = new Panier(5, us.getUserById(2), /*now*/ null, null, 201, 7, Enumerations.StatusPanier.Valide.toString(), Enumerations.ModePaiement.Espece.toString(), true, true, null);
        Panier panier2 = new Panier(3, us.getUserById(2), null, null, 201, 7, Enumerations.StatusPanier.Valide.toString(), Enumerations.ModePaiement.Espece.toString(), true, true, null);
        List<Panier> paniers = new ArrayList<Panier>();
        paniers.add(panier1);
        paniers.add(panier2);
        historique.addAll(paniers);

        historiqueClient.setItems(historique);

    }

}
