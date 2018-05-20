/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Produit;
import Entities.Reclamation;
import Entities.User;
import Services.ReclamationService;
import Utils.Enumerations;
import Utils.Enumerations.TypeReclamation;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class ReclamationAdminController implements Initializable {
    
    public static User userPasse;
    public static Produit produitPasse;
    public static Boutique boutiquePasse;
    public Reclamation reclamationSelectionne;
    @FXML
    private AnchorPane reclamationAdmin;
    @FXML
    private TableView<Reclamation> reclamationsTable;
    @FXML
    private TableColumn<Reclamation, String> description = new TableColumn("description");
    @FXML
    private TableColumn<Reclamation, LocalDateTime> date = new TableColumn("date");
    @FXML
    private TableColumn<Reclamation, String> user = new TableColumn("user");
    @FXML
    private TableColumn<Reclamation, Boolean> entite = new TableColumn("entite");

    private ObservableList<Reclamation> reclamations = FXCollections.observableArrayList();
    /**
     * Initializes the controller class.
     */
    Callback<TableColumn<Reclamation, Boolean>, TableCell<Reclamation, Boolean>> cellFactory;
    public ReclamationAdminController() {
        this.cellFactory = new Callback<TableColumn<Reclamation, Boolean>, TableCell<Reclamation, Boolean>>() {
            @Override
            public TableCell<Reclamation, Boolean> call(final TableColumn<Reclamation, Boolean> param) {
                final TableCell<Reclamation, Boolean> cell;
                cell = new TableCell<Reclamation, Boolean>() {
                    final Button btn = new Button();
                    Image img = new Image(getClass().getResourceAsStream("/Images/info.png"));
                    @Override
                    public void updateItem(Boolean check, boolean empty) {
                        super.updateItem(check, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            reclamationSelectionne = getTableView().getItems().get(getIndex());
                            if (reclamationSelectionne.getType()==TypeReclamation.Boutique){
                                img = new Image(getClass().getResourceAsStream("/Images/editer.png"));
                            }
                            else{
                                img = new Image(getClass().getResourceAsStream("/Images/info.png"));
                            }
                            btn.setCursor(Cursor.HAND);
                            btn.setOnAction((ActionEvent e) -> {
                                reclamationSelectionne = getTableView().getItems().get(getIndex());
                                //TableColumn<Reclamation,boolean> colsel = getTableColumn();
                                userPasse = reclamationSelectionne.getUser();
                                if (reclamationSelectionne.getType()==TypeReclamation.Boutique){
                                    try {
                                        redirectBoutique();
                                    } catch (IOException ex) {
                                        Logger.getLogger(ReclamationAdminController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                                if(reclamationSelectionne.getType()==TypeReclamation.Produit){
                                    try {
                                        redirectProduit();
                                    } catch (IOException ex) {
                                        Logger.getLogger(ReclamationAdminController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            });
                            
                            btn.setStyle("-fx-background-color: transparent;");
                            ImageView iv = new ImageView();
                            iv.setImage(img);
                            iv.setPreserveRatio(true);
                            iv.setSmooth(true);
                            iv.setCache(true);
                            btn.setGraphic(iv);
                            setGraphic(btn);
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
        setColumnProperties();
        setReclamationDetails();
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
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        //user.setCellValueFactory(new PropertyValueFactory<>());
        user.setCellValueFactory(
                User -> {
                    SimpleObjectProperty property = new SimpleObjectProperty();
                    property.setValue(User.getValue().getUser().getUserName());
                    return property;
                }
        );
        //user.setCellValueFactory(new PropertyValueFactory<>("user"));
        entite.setCellFactory(cellFactory);
    }

    private void setReclamationDetails() {


        reclamations.clear();
        ReclamationService rs = new ReclamationService();
        System.out.println("LES RECLAMATIONSSSSS ===  " + rs.getAllReclamations());
        //User u = new User();
        //u.setUserName("biisbes");
        //Reclamation rec = new Reclamation(5, u, new Produit(), "khiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiit", LocalDateTime.MIN, TypeReclamation.Boutique);
        reclamations.addAll(rs.getAllReclamations());
        int hight = rs.getAllReclamations().size()* 43;
        reclamationsTable.setPrefHeight(hight);
        reclamationsTable.setItems(reclamations);

    }
    
    private void redirectProduit() throws IOException {
        //TODO
        AnchorPane pane = FXMLLoader.load(getClass().getResource("HistoriqueClient.fxml"));
        reclamationAdmin.getChildren().setAll(pane);
    }
    private void redirectBoutique() throws IOException {
        //TODO
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Reclamation.fxml"));
        reclamationAdmin.getChildren().setAll(pane);
    }
    private void redirectUser() throws IOException {
        //TODO
        AnchorPane pane = FXMLLoader.load(getClass().getResource("Evaluation.fxml"));
        reclamationAdmin.getChildren().setAll(pane);
    }
}
