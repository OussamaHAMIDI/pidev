/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Panier;
import Entities.ProduitPanier;
import static Presentation.PaypalController.pc;
import Services.PanierService;
import Services.StockService;
import Utils.Enumerations;
import Utils.SmsSender;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;
import org.apache.pdfbox.pdmodel.PDDocument;

/**
 * FXML Controller class
 *
 * @author monta
 */
public class PanierController implements Initializable {

    Panier panier = new Panier();
    Parent paypal;

    PanierController.MyBrowser myBrowser;

    private Pane content;

    @FXML
    private Label prixArticles;

    @FXML
    private Label prixLivraison;

    @FXML
    private Label totalTTC;
    private ScrollPane scrollPane;

    private AnchorPane anchorContent;

    GridPane gridPane = new GridPane();
    Panier p = new Panier();
    Double scrollHeight = 0.0;
    @FXML
    private ScrollPane contacts;
    ObservableList<String> paiementList = FXCollections.observableArrayList("Espece", "Internet", "Cheque");
    ObservableList<String> typeList = FXCollections.observableArrayList("Sur place", "A domicile", "Par poste");
    @FXML
    private JFXComboBox<String> modeLivraison;
    @FXML
    private JFXComboBox<String> modePaiement;
    @FXML
    private AnchorPane origine;
    @FXML
    private JFXButton commander;

    /**
     * Initializes the controller class.
     */
    private void addToGrid(List<Parent> list, GridPane gridPane) {
        int totalItems = list.size();
        int totalLignes = (totalItems % 2 == 0) ? totalItems / 2 : (totalItems + 1) / 2;
        int nbrItems = gridPane.getChildren().size();
        int nbrRows = (nbrItems % 2 == 0) ? nbrItems / 2 : (nbrItems + 1) / 2;

        if (nbrItems % 2 == 1) {// impaire
            if (list.size() > 0) {
                gridPane.add(list.get(0), 0, nbrRows - 1);
                list.remove(0);
            }
        }
        //paire
        for (int ligne = nbrRows; ligne < nbrRows + totalItems; ligne++) {
//            for (int col = 0; col < 2; col++) {
            if (list.size() > 0) {
                gridPane.add(list.get(0), 0, ligne);
                list.remove(0);
            } else {
                return;
            }
//            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<Parent> list = new ArrayList<Parent>();

        try {
            // TODO

            // panier.setContenu(new ArrayList<ProduitPanier>());
            PanierService ps = new PanierService();
            panier = ps.rechercherPanierById(3);
            //panier.setContenu(ps.rechercherProduitsPanier(3));
//
            Float total = 0.0f;
            ProduitPanierController.contenu = panier.getContenu();

            for (int i = 0; i < panier.getContenu().size(); i++) {
                ProduitPanierController.index = i;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ProduitPanier.fxml"));
                Parent root = loader.load();
                list.add(root);
                total += panier.getContenu().get(i).getPrix() * panier.getContenu().get(i).getQuantiteVendue();
            }
            prixArticles.setText(total.toString());
            totalTTC.setText(total.toString());
            addToGrid(list, gridPane);
            gridPane.setHgap(0);
            gridPane.setVgap(0);
            contacts.setPannable(true);
            contacts.setContent(gridPane);
            ProduitPanierController.pc = this;
            modeLivraison.setItems(typeList);
            modeLivraison.setValue("Sur place");
            modePaiement.setItems(paiementList);
            modePaiement.setValue("Espece");
        } catch (IOException ex) {
            Logger.getLogger(PanierController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void modifierContenu(ProduitPanier produit, ProduitPanier old) {
        panier.getContenu().set(panier.getContenu().indexOf(old), produit);

    }

    public void modifierTotaux(float prix) {
        Float v = (Float.parseFloat(prixArticles.getText()) + prix);
        prixArticles.setText(v.toString());

        v = (Float.parseFloat(totalTTC.getText()) + prix);
        totalTTC.setText(v.toString());
        panier.setTotalTTC(v);
    }

    @FXML
    private void modifierMode(ActionEvent event) {
        Float value = Float.parseFloat(totalTTC.getText());
        switch (modeLivraison.getValue()) {
            case "Sur place":
                panier.setModeLivraison(Enumerations.ModeLivraison.Surplace);
                if (prixLivraison.getText().equals("12.0")) {
                    prixLivraison.setText("0.0");
                    value -= 12.0f;
                    totalTTC.setText(value.toString());
                    panier.setFraisLivraison(0.0);
                    panier.setTotalTTC(value);
                }
                break;
            case "A domicile":
                panier.setModeLivraison(Enumerations.ModeLivraison.Domicile);
                if (prixLivraison.getText().equals("0")) {
                    prixLivraison.setText("12.0");
                    value += 12.0f;
                    totalTTC.setText(value.toString());
                    panier.setFraisLivraison(12.0);
                    panier.setTotalTTC(value);
                }
                break;
            case "Par poste":
                if (prixLivraison.getText().equals("0")) {
                    prixLivraison.setText("12.0");
                    value += 12.0f;
                    totalTTC.setText(value.toString());
                    panier.setFraisLivraison(12.0);
                    panier.setTotalTTC(value);
                }
                panier.setModeLivraison(Enumerations.ModeLivraison.Poste);
                break;
        }
    }

    @FXML
    private void payerPanier(MouseEvent event) throws IOException {
        PanierService ps = new PanierService();
        if (verif()) {
            StockService ss = new StockService();

            ps.miseAJourPanier(panier);
            for (ProduitPanier p : panier.getContenu()) {
                ss.modifierStock(p.getId(), (int) p.getQuantiteVendue() * -1);
                ps.modifierProduitPanier(p, panier.getId());
            }

            Float v = (Float.parseFloat(totalTTC.getText()));
            panier.setTotalTTC(v);
            v = (Float.parseFloat(prixLivraison.getText()));
            panier.setFraisLivraison(v);
            switch (modePaiement.getValue()) {
                case "Espece":
                    //EnvoyerMail

                    break;
                case "Chaque":
                    //Envoyer mail

                    break;
                case "Internet":
                    myBrowser = new PanierController.MyBrowser(this);
                    myBrowser.setMinSize(1140, 720);
                    origine.getChildren().add(0, myBrowser);
                    myBrowser.toFront();
                    paypal = myBrowser;
                    break;
            }
            Utils.Utils.sendMail(panier.getUser().getEmail(), panier.genererMailBody(),"","Client");
            List<ProduitPanier> temp = panier.getContenu();
            Map<Boutique, List<ProduitPanier>> map = temp.stream().collect(Collectors.groupingBy(ProduitPanier::getBoutique));
            map.forEach((b, p) -> {
                Panier tmp = new Panier();
                tmp.setContenu(p);
              //  System.out.println(b.getUser().getEmail());
                Utils.Utils.sendMail(b.getUser().getEmail(), tmp.genererMailBody(), panier.getUser().getNom() + " " + panier.getUser().getPrenom(),"Artisan");
            });
            panier.generatePDF();
            viderPanier();
            SmsSender sms = new SmsSender();
            //sms.sendSms("", "");
        }
    }

    public boolean verif() {
        StockService ss = new StockService();
        for (ProduitPanier p : panier.getContenu()) {
            int etat = ss.stockProduit(p.getId());
            if (etat < p.getQuantiteVendue()) {
                Utils.Utils.showAlert(Alert.AlertType.ERROR, "Stock indisponible", "Stock indisponible", "Le produit " + p.getLibelle() + " n'a que " + etat + " en stock");
                return false;
            }
        }
        return true;
    }

    public void retourPanier() {
        origine.getChildren().remove(paypal);
    }

    public int panierId() {
        return panier.getId();
    }

    public void viderPanier() {

        gridPane.getChildren().removeAll(gridPane.getChildren());
        prixArticles.setText("0.0");
        totalTTC.setText("0.0");
        modeLivraison.setValue("Sur place");
        modePaiement.setItems(paiementList);
        modePaiement.setValue("Espece");
        origine.getChildren().remove(paypal);
        panier = new Panier();
    }

    VBox vb = new VBox();

    Label labelFromJavascript;

    public class MyBrowser extends Region {

        HBox toolbar;
        VBox toolbox;
        PanierController pc;
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        public MyBrowser(PanierController ppc) {
            pc = ppc;
            final URL urlHello = getClass().getResource("http://127.0.0.1/Paypal/hello.html");
            webEngine.load("http://127.0.0.1/Paypal/first.php?idpanier=2");
            //webEngine.load("http://127.0.0.1/Paypal/first.php?idpanier=" + pc.panierId());
            //webEngine.load("http://127.0.0.1/Paypal/failed.html");
            //webEngine.load("http://127.0.0.1/Paypal/done.html");
            webEngine.getLoadWorker().stateProperty().addListener(
                    new ChangeListener<Worker.State>() {

                @Override
                public void changed(ObservableValue<? extends Worker.State> ov, Worker.State oldState, Worker.State newState) {
                    if (newState == Worker.State.SUCCEEDED) {
                        JSObject window = (JSObject) webEngine.executeScript("window");
                        window.setMember("app", new JavaApplication(pc));
                    }
                }
            });

            JSObject window = (JSObject) webEngine.executeScript("window");
            window.setMember("app", new JavaApplication(pc));

            toolbox = new VBox();
            labelFromJavascript = new Label();
            toolbox.getChildren().addAll(labelFromJavascript);
            labelFromJavascript.setText("Wait");

            getChildren().add(toolbox);
            getChildren().add(webView);

        }

        @Override
        protected void layoutChildren() {
            double w = getWidth();
            double h = getHeight();
            double toolboxHeight = toolbox.prefHeight(w);
            layoutInArea(webView, 0, 0, w, h - toolboxHeight, 0, HPos.CENTER, VPos.CENTER);
            layoutInArea(toolbox, 0, h - toolboxHeight, w, toolboxHeight, 0, HPos.CENTER, VPos.CENTER);
        }

    }

    public class JavaApplication {

        public JavaApplication(PanierController pcc) {
            pc = pcc;
        }
        PanierController pc;

        public void callFromJavascript(String msg) {
            if (msg.equals("done")) {
                //CallMainController bich tirja3 lil accueil
                pc.viderPanier();
            } else {
                //CallMainController bich tirja3 lil panier
                pc.retourPanier();
            }
            labelFromJavascript.setText("Click from Javascript: " + msg);

        }
    }
}
