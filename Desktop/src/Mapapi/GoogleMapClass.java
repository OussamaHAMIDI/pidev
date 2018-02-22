/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Mapapi;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.TravelModes;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;


/**
 *
 * @author khmai
 */
public class GoogleMapClass implements MapComponentInitializedListener, DirectionsServiceCallback {
    
    public DirectionsService directionsService;
    public DirectionsPane directionsPane;
    
      public GoogleMap map;
    
    public GeocodingService geocodingService;
    public GoogleMapView googlemapV;

    
     public MarkerOptions markerOptions;
     
         private static GoogleMapClass googleMapClass;
         
         

     
     
    
    public void configDirections( String from,String to){
        
        DirectionsRequest request = new DirectionsRequest(from, to, TravelModes.DRIVING);
        directionsService.getRoute(request, this, new DirectionsRenderer(true, googlemapV.getMap(), directionsPane));
                        System.out.println("google map class request "+request);

        
    }

    public GoogleMapClass(GoogleMapView googlemapV, String from, String to) {
        this.googlemapV = googlemapV;
       /* this.from = from;
        this.to = to;*/
        
    }
    
    public void addinitlist(){
                googlemapV.addMapInializedListener(this);
                System.out.println("google class map "+googlemapV);

    }

    public GoogleMapClass(GoogleMapView googlemapV) {
        this.googlemapV = googlemapV;
                googlemapV.addMapInializedListener(this);

        
    }
    
    

    public GoogleMapClass() {
    }
    
    public void initMap(GoogleMapView mapV){
         googlemapV = mapV;
    }
    
    public GoogleMapView initmap(GoogleMapView mapV){
        
        return mapV;
    }

    @Override
    public void mapInitialized() {
        
MapOptions options = new MapOptions();

        options.center(new LatLong(47.606189, -122.335842))
                .zoomControl(true)
                .zoom(12)
                .overviewMapControl(false)
                .mapType(MapTypeIdEnum.ROADMAP);
        GoogleMap map = googlemapV.createMap(options);
        System.out.println("egoogle map class mapinit "+map);
        directionsService = new DirectionsService();
        directionsPane = googlemapV.getDirec();
    }

    @Override
    public void directionsReceived(DirectionsResult dr, DirectionStatus ds) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    public static GoogleMapClass getInstance() {
        if (googleMapClass == null) {
            googleMapClass = new GoogleMapClass();
        }
        return googleMapClass;
    }
    
}
