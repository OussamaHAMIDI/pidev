/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.User;
import Services.BoutiqueService;
import Services.ProduitService;
import com.jfoenix.controls.JFXDatePicker;
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
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import jfxtras.scene.control.LocalDateTextField;
import jfxtras.scene.control.LocalDateTimeTextField;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author Azza
 */
public class BoutiqueController implements Initializable , MapComponentInitializedListener, DirectionsServiceCallback {
    private GeocodingService geocodingService;
    protected DirectionsPane directionsPane;
List<String> l = new ArrayList();
protected DirectionsService directionsService;
String[] s;
protected StringProperty from = new SimpleStringProperty();
GoogleMapView mapView = new GoogleMapView();
    @FXML
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
        
        BoutiqueService bs = new BoutiqueService();
        Boutique boutique = new Boutique();
        boutique.setIDUser(Integer.parseInt(btIdUser.getText()));
        boutique.setNom(btNom.getText());
        
        boutique.setLong(Double.parseDouble(longi.getText()));
        boutique.setLat(Double.parseDouble(lat.getText()));
        
        
        boutique.setDateCreation(btDate.getLocalDateTime());
        boutique.setAdresse(btAdresse.getText());
//        System.out.println("-------------------"+boutique.toString());
        
        bs.ajouterBoutique(boutique);
    }

    @FXML
    private void retourAction(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        
            Stage stage=new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("InterfaceBoutique.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
    }

    @FXML
    private void fromOnkeyTypedEvent(KeyEvent event) {
        try{
        geocodingService = new GeocodingService();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
   geocodingService.geocode(btAdresse.getText(), (GeocodingResult[] results, GeocoderStatus status) -> {
        l.clear();
        //int i;                 
  for(int i =0;i<results.length;i++){
        s=new String[results.length];
      s[i] = results[i].getFormattedAddress();
    
         l.add(results[i].getFormattedAddress());
        
         
  }
     
            
       for (GeocodingResult result : results) {
           
       
              TextFields.bindAutoCompletion(btAdresse, s);
              longi.setText(result.getGeometry().getLocation().getLatitude()+"");
             lat.setText(result.getGeometry().getLocation().getLongitude()+"");

            }
       
        TextFields.bindAutoCompletion(btAdresse, t-> {
 
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

  
}
