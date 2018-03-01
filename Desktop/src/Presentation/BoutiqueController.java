/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Utils.Utils;



import Services.BoutiqueService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.geocoding.GeocoderStatus;
import com.lynden.gmapsfx.service.geocoding.GeocodingResult;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import jfxtras.scene.control.LocalDateTimeTextField;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Azza
 */
public class BoutiqueController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    private GeocodingService geocodingService;
    protected DirectionsPane directionsPane;
    List<String> l = new ArrayList();
    protected DirectionsService directionsService;
    String[] s;
    protected StringProperty from = new SimpleStringProperty();
    GoogleMapView mapView = new GoogleMapView();
    public static MenuBoutiqueController mc;
    private JFXTextField btIdUser;
    @FXML
    private JFXTextField btNom;
    @FXML
    private LocalDateTimeTextField btDate;
    @FXML
    private JFXTextField btAdresse;
    @FXML
    private JFXTextField longi;
    @FXML
    private JFXTextField lat;
    @FXML
    private Button ajouterPhoto;
    @FXML
    private ImageView photo;
    private FileInputStream photoBoutique = null;
    @FXML
    private JFXButton modifierB;
    @FXML
    private ImageView close;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        mapView.addMapInializedListener(this);

        mapView.addMapInializedListener(this);

        from.bindBidirectional(btAdresse.textProperty());

        btAdresse.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (!newPropertyValue) {
                    btAdresse.validate();
                }
            }

        });
    }

    @FXML
    private void validerAction(ActionEvent event) {
         Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        if (btNom.getText().isEmpty())
        {
            alert.setHeaderText("Veuillez saisir le nom de votre boutique");
            alert.show();
        }
        else if (btAdresse.getText().isEmpty())
        {
            alert.setHeaderText("Veuillez saisir l'adresse de votre boutique");
            alert.show();
        }
          else if (btDate.getText().isEmpty())
        {
            alert.setHeaderText("Veuillez saisir la date création de votre boutique");
            alert.show();
//        }else if(btDate.getText().compareTo(LocalDate.now()) <0){
//            Alert alertt = new Alert(Alert.AlertType.ERROR);
//        alertt.setHeaderText(null);
//        alertt.setContentText("Vous devez choisir une date supérieure ou égale à la date d'aujoud'hui");
//        alertt.showAndWait();

        }else{
        BoutiqueService bs = new BoutiqueService();
        Boutique boutique = new Boutique();
//        boutique.setIDUser(Integer.parseInt(btIdUser.getText()));
        boutique.setNom(btNom.getText());

        boutique.setLong(Double.parseDouble(longi.getText()));
        boutique.setLat(Double.parseDouble(lat.getText()));

        boutique.setDateCreation(btDate.getLocalDateTime());
        boutique.setAdresse(btAdresse.getText());
        boutique.setPhoto(photoBoutique);

        /*************************** ds la clause if ***********************/
        bs.ajouterBoutique(boutique, 26);
        MenuBoutiqueController.list.add(boutique);
        //list = bs.lireBoutiques();
        MenuBoutiqueController.gridPane.getChildren().clear();
        mc.addToGrid(bs.lireBoutiques());
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
        /*******************************************************/
        if (AccueilController.userConnected != null) {
            bs.ajouterBoutique(boutique, AccueilController.userConnected.getId());
        }
        }
    }


    @FXML
    private void fromOnkeyTypedEvent(KeyEvent event) {
        try {
            geocodingService = new GeocodingService();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        geocodingService.geocode(btAdresse.getText(), (GeocodingResult[] results, GeocoderStatus status) -> {
            l.clear();
            //int i;                 
            for (int i = 0; i < results.length; i++) {
                s = new String[results.length];
                s[i] = results[i].getFormattedAddress();

                l.add(results[i].getFormattedAddress());

            }

            for (GeocodingResult result : results) {

                TextFields.bindAutoCompletion(btAdresse, s);
                longi.setText(result.getGeometry().getLocation().getLatitude() + "");
                lat.setText(result.getGeometry().getLocation().getLongitude() + "");

            }

            TextFields.bindAutoCompletion(btAdresse, t -> {

                return l;

            });

        });
    }

    public void mapInitialized() {
        geocodingService = new GeocodingService();
        MapOptions options = new MapOptions();

        options.center(new LatLong(34.3055732, 11.255412))
                .zoomControl(true)
                .zoom(6)
                .overviewMapControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);

        GoogleMap map = mapView.createMap(options);
        directionsService = new DirectionsService();
        directionsPane = mapView.getDirec();
    }

    public void directionsReceived(DirectionsResult dr, DirectionStatus ds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void ajoutPhoto(ActionEvent event) throws IOException {
        FileChooser file = new FileChooser(); //pour choisir la photo
        file.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.bmp"));
        file.setTitle("Choisir une photo du produit");

        File selected_photo = file.showOpenDialog((Stage) close.getScene().getWindow());
        if (selected_photo != null) {
            if ((selected_photo.length() / 1024) / 1024 < 2.0) {
                String path = selected_photo.getAbsolutePath();
                BufferedImage bufferedImage = ImageIO.read(selected_photo);
                WritableImage image = SwingFXUtils.toFXImage(bufferedImage, null);
                photo.setImage(image);

                File img = new File(path);
                photoBoutique = new FileInputStream(img);
            } else {
                Utils.showAlert(Alert.AlertType.ERROR, "Erreur", "Taile trop grande !", "Veuillez choisir une photo de profil avec une taille < 2 Mo");
            }
        }

    }

    @FXML
    private void retourAction(MouseEvent event) {
        Stage s = (Stage) ((Node) event.getSource()).getScene().getWindow();
        s.close();
    }

}
