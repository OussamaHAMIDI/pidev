package Presentation;

import Entities.Panier;
import Entities.User;
import Services.UserService;
import Utils.Enumerations.EtatUser;
import Utils.Enumerations.TypeUser;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class GestionUsersController1 implements Initializable {

    @FXML
    private JFXButton ajouter;

    //********************************************************************************
    @FXML
    private TableColumn<User, Integer> idColumn = new TableColumn<>("id");
    @FXML
    private TableColumn<User, String> nomColumn = new TableColumn<>("nom");
    @FXML
    private TableColumn<User, String> prenomColumn = new TableColumn<>("prenom");
    @FXML
    private TableColumn<User, String> typeColumn = new TableColumn<>("type");
    @FXML
    private TableColumn<User, String> sexeColumn = new TableColumn<>("sexe");
    @FXML
    private TableColumn<User, EtatUser> etatColumn = new TableColumn<>("etat");
    @FXML
    private TableColumn<User, LocalDateTime> lastLoginColumn = new TableColumn<>("lastLogin");
    @FXML
    private TableColumn<User, Boolean> editer = new TableColumn<>("editer");
    //********************************************************************************
    @FXML
    public JFXTextField filter;
    @FXML
    private TableView<User> table;
    @FXML
    private AnchorPane blur;

    private UserService us = new UserService();

    private User selectedUser;
    public static User user;

    public void buildUsersTable() {
        ObservableList<User> users = FXCollections.observableArrayList();
        us.getUsers().forEach(e -> users.add(e));
        table.setItems(users);
        table.refresh();
    }

    private void updateItems(String text) {
        ObservableList<User> users = FXCollections.observableArrayList();
        if (!text.isEmpty()) {
            users.addAll(us.getUsers().stream().filter(u -> u.getPrenom().toLowerCase().contains(text.toLowerCase())
                    || u.getNom().toLowerCase().contains(text.toLowerCase())
                    || u.getType().toString().toLowerCase().startsWith(text.toLowerCase())).collect(Collectors.toList()));
            table.setItems(users);
            table.refresh();
        } else {
            buildUsersTable();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>> cellFactory = new Callback<TableColumn<User, Boolean>, TableCell<User, Boolean>>() {
            @Override
            public TableCell<User, Boolean> call(final TableColumn<User, Boolean> param) {
                final TableCell<User, Boolean> cell = new TableCell<User, Boolean>() {
                    Image imgDetails = new Image(getClass().getResourceAsStream("/Images/editer.png"));
                    final Button btnDetails = new Button();

                    @Override
                    public void updateItem(Boolean check, boolean empty) {
                        super.updateItem(check, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btnDetails.setCursor(Cursor.HAND);

                            btnDetails.setOnAction(e -> {
                                User user = getTableView().getItems().get(getIndex());
                                modifier(user);
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

        lastLoginColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<LocalDateTime>() {
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

        etatColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<EtatUser>() {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            @Override
            public String toString(EtatUser etat) {
                if (etat != null) {
                    if (etat.equals(EtatUser.Pending)) {
                        return "En attente";
                    }
                    if (etat.equals(EtatUser.Deleted)) {
                        return "Supprimé";
                    }
                    return etat.toString();
                } else {
                    return "Jamais";
                }
            }

            @Override
            public EtatUser fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    if (string.equals("En attente")) {
                        return EtatUser.Pending;
                    }
                    if (string.equals("Supprimé")) {
                        return EtatUser.Deleted;
                    }
                    return EtatUser.valueOf(string);
                } else {
                    return null;
                }
            }
        }));

        table.setRowFactory(new Callback<TableView<User>, TableRow<User>>() {
            @Override
            public TableRow<User> call(TableView<User> tv) {
                final TableRow<User> row = new TableRow<>();
                row.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        final int index = row.getIndex();
                        if (index >= 0 && index < table.getItems().size() && table.getSelectionModel().isSelected(index)) {
                            table.getSelectionModel().clearSelection();
                            event.consume();
                        }
                    }
                });
                return row;
            }
        });

        // 0. Initialize  columns.
        idColumn.setCellValueFactory((new PropertyValueFactory<>("id")));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        sexeColumn.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
        lastLoginColumn.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));
        editer.setCellFactory(cellFactory);

//        table.setOnMousePressed(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
//                    
//                    System.out.println(table.getSelectionModel().getSelectedItem());
//                    table.getSelectionModel().clearSelection();
//                }
//            }
//        });
        filter.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.toLowerCase().matches("[a-z]+ [a-z]+ [a-z]+ [a-z]*")) {
                filter.setText(oldValue);
            }

//            if (oldValue.isEmpty() && newValue.equals(" ")) {
//                filter.setText("");
//            } else {
//                filter.setText(newValue.replace("  ", " "));
//                if (newValue.toLowerCase().matches("[a-z]+ [a-z]+ [a-z]+ [a-z]*")) {
//                    filter.setText(oldValue);
//                }
//            }
            updateItems(filter.getText());

        });

        buildUsersTable();
    }

    @FXML
    private void ajouterClicked(ActionEvent event) {
        blur.setEffect(new GaussianBlur(5));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUser.fxml"));
        Stage stage = Utils.getAnotherStage(loader, "Inscription");
        stage.initStyle(StageStyle.TRANSPARENT);
        AddUserController auc = loader.getController();
        auc.blur = blur; // transfer anchorPane to new stage **************************************
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent we) {
                buildUsersTable();
            }
        });
        stage.show();
    }

    @FXML
    private void supprimerClicked(ActionEvent event) {
        User u = table.getSelectionModel().getSelectedItem();
        if (u != null) {
            us.supprimerUser(u);
            buildUsersTable();
        }

    }

    private void modifier(User u) {

        if (u != null) {
            blur.setEffect(new GaussianBlur(5));
            ModifierUserController.blur = blur;
            ModifierUserController.userSelected = u;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifierUser.fxml"));
            Stage stage = Utils.getAnotherStage(loader, "Modification");
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent we) {
                    blur.setEffect(null);
                }
            });
            stage.show();
            buildUsersTable();
        }

    }

}
