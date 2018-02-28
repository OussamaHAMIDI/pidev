/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Presentation.PanierController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

/**
 * FXML Controller class
 *
 * @author monta
 */
public class PaypalController implements Initializable {

    public static PanierController pc;
        
     VBox vb = new VBox();

    MyBrowser myBrowser;
     
    Label labelFromJavascript;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         myBrowser = new MyBrowser();
    }    
    
    
      public class MyBrowser extends Region{
         
        HBox toolbar;
        VBox toolbox;
         
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
         
        public MyBrowser(){
            
            //final URL urlHello = getClass().getResource("http://127.0.0.1/Paypal/hello.html");
            webEngine.load("http://127.0.0.1/Paypal/first.php?idpanier="+pc.panierId());
            //webEngine.load("http://127.0.0.1/Paypal/done.html");
            webEngine.getLoadWorker().stateProperty().addListener(
                    new ChangeListener<Worker.State>(){
                         
                        @Override
                        public void changed(ObservableValue<? extends Worker.State> ov, Worker.State oldState, Worker.State newState) {
                            if(newState == Worker.State.SUCCEEDED){
                                JSObject window = (JSObject)webEngine.executeScript("window");
                                window.setMember("app", new JavaApplication());
                            }
                        }
                    });
             
             
            JSObject window = (JSObject)webEngine.executeScript("window");
            window.setMember("app", new JavaApplication());

            toolbox = new VBox();
            labelFromJavascript = new Label();
            toolbox.getChildren().addAll(labelFromJavascript);
            labelFromJavascript.setText("Wait");
             
            getChildren().add(toolbox);
            getChildren().add(webView);
             
        }
         
        @Override
        protected void layoutChildren(){
            double w = getWidth();
            double h = getHeight();
            double toolboxHeight = toolbox.prefHeight(w);
            layoutInArea(webView, 0, 0, w, h-toolboxHeight, 0, HPos.CENTER, VPos.CENTER);
            layoutInArea(toolbox, 0, h-toolboxHeight, w, toolboxHeight, 0, HPos.CENTER, VPos.CENTER);
        }
         
    }
     
    public class JavaApplication {
        public void callFromJavascript(String msg) {
            if(msg.equals("done"))
            {
                //CallMainController bich tirja3 lil accueil
                pc.viderPanier();
            }
            else
            {
                //CallMainController bich tirja3 lil panier
                pc.retourPanier();
            }
            labelFromJavascript.setText("Click from Javascript: " + msg);
            
        }
    }
}
