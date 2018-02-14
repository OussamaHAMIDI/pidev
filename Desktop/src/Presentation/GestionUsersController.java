package Presentation;

import Entities.User;
import Services.UserService;
import Utils.Enumerations.*;
import Utils.Utils;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

public class GestionUsersController implements Initializable {

    @FXML
    private JFXButton ajouter;

    @FXML
    private JFXButton ajouter1;

    @FXML
    private Label titre;

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
    //********************************************************************************

    private ObservableList<User> users = FXCollections.observableArrayList();

    @FXML
    private JFXTextField filter;
    @FXML
    private TableView<User> table;

    //For MultiThreading
    private Executor exec;

    public String id;
    private UserService us;
    private boolean selected = false;
    private User selectedUser;
    @FXML
    private AnchorPane blur;

    /**
     * ******************************************************************************************
     */
    private void buildUsersTable() {

        // us.getUsers().forEach(System.out::println);
        System.out.println(id);
        System.out.println(us.getUserByUsername("HamdiMegdiche").getEmail());
        System.out.println("affichi amaaan");
        users.add(us.getUserById(Integer.parseInt(id)));
        //us.getUsers().forEach(e -> users.add(e));

        // 0. Initialize  columns.
        idColumn.setCellValueFactory((new PropertyValueFactory<>("id")));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        sexeColumn.setCellValueFactory(new PropertyValueFactory<>("sexe"));
        etatColumn.setCellValueFactory(new PropertyValueFactory<>("etat"));
        lastLoginColumn.setCellValueFactory(new PropertyValueFactory<>("lastLogin"));

        table.getItems().clear();
        table.getItems().addAll(users);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //For multithreading: Create executor that uses daemon threads:
        exec = Executors.newCachedThreadPool((runnable) -> {
            Thread t = new Thread(runnable);
            t.setDaemon(true);
            return t;
        });
        table.getSelectionModel().selectedItemProperty().addListener(
                (
                        ObservableValue<? extends User> observable,
                        User oldValue,
                        User newValue) -> {
                    if (newValue == null) {
                        return;
                    }
                    selectedUser = newValue;
                });

        // buildUsersTable();
        table.refresh();

    }

    @FXML
    private void ajouterClicked(ActionEvent event) {
        blur.setEffect(new GaussianBlur(5));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUser.fxml"));
        Stage stage = Utils.getAnotherStage(loader, "Inscription");
        stage.initStyle(StageStyle.TRANSPARENT);
        AddUserController auc = loader.getController();
        auc.blur = blur; // transfer anchorPane to new stage **************************************
        stage.show();
    }

    @FXML
    private void confirmerClicked(ActionEvent event) {
        buildUsersTable();
    }

    @FXML
    private void modifierClicked(ActionEvent event) {

    }

    @FXML
    private void supprimerClicked(ActionEvent event) {

    }

    @FXML
    private void filterChanged(InputMethodEvent event) {
        Task<List<User>> task = new Task<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                return us.getUsers();
            }
        };
        table.getItems().clear();
        task.setOnFailed(e -> task.getException().printStackTrace());
        task.setOnSucceeded(e -> table.getItems().addAll((List<User>) task.getValue()));
        exec.execute(task);
    }

}
