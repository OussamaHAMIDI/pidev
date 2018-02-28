/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Produit;
import Services.EvaluationService;
import Services.ProduitService;
import Services.StatistiqueService;
import com.jfoenix.controls.JFXProgressBar;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.ScrollPane;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class StatistiqueController implements Initializable {

    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis axex;
    @FXML
    private PieChart pieChartProd;
    @FXML
    private BarChart<?, ?> barChartp;
    @FXML
    private BarChart<?, ?> barChartb;
    @FXML
    private NumberAxis y1;
    @FXML
    private CategoryAxis axex1;
    @FXML
    private PieChart pieChartU;
    @FXML
    private ScrollPane scrollStat;
    @FXML
    private Label nbTot;
    @FXML
    private ProgressBar progress;
    @FXML
    private Label pourcentage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        System.out.println("blablabla");
        scrollStat.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        StatistiqueService ss = new StatistiqueService();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Nombre d'Administrateurs : " + ss.getNombreAdministrateurs(), ss.getNombreAdministrateurs()),
                new PieChart.Data("Nombre de Clients : " + ss.getNombreClients(), ss.getNombreClients()),
                new PieChart.Data("Nombre d'Artisans : " + ss.getNombreArtisans(), ss.getNombreArtisans())
        );
        pieChartU.setData(pieChartData);
        pieChartU.setLegendSide(Side.LEFT);
        ObservableList<PieChart.Data> pieChartDataProduits = FXCollections.observableArrayList(
                new PieChart.Data("Nombre total de produits : " + ss.getNombreProduits(), ss.getNombreProduits()),
                new PieChart.Data("Nombre de produits Vendus : " + ss.getNombreProduitsVendus(), ss.getNombreProduitsVendus())
        );
        pieChartProd.setData(pieChartDataProduits);
        pieChartProd.setLegendSide(Side.RIGHT);


        EvaluationService es = new EvaluationService();
        List<Produit> topTenProduits = ss.getTopTenProduits();
        System.out.println(topTenProduits);
        XYChart.Series setP = new XYChart.Series<>();
        for (Produit p : topTenProduits) {
            if (es.produitHasNote(p)) {
                setP.getData().add(new XYChart.Data(/*p.getId() + ":"+*/p.getReference(), es.getNoteProduit(p)));
            }
        }
        barChartp.getData().addAll(setP);

        List<Boutique> topTenBoutiques = ss.getTopTenBoutiques();
        System.out.println(topTenBoutiques);
        XYChart.Series setB = new XYChart.Series<>();
        for (Boutique b : topTenBoutiques) {
            if (es.boutiqueHasNote(b)) {
                setB.getData().add(new XYChart.Data(b.getId() +":" + b.getNom(), es.getNoteBoutique(b)));
            }
        }
        barChartb.getData().addAll(setB);

        XYChart.Series seriesP = new XYChart.Series();
        seriesP.getData().add(new XYChart.Data("Jan", ss.getQuantiteProduitsVendusParMois("-01-")));
        seriesP.getData().add(new XYChart.Data("Fév", ss.getQuantiteProduitsVendusParMois("-02-")));
        seriesP.getData().add(new XYChart.Data("Mar", ss.getQuantiteProduitsVendusParMois("-03-")));
        seriesP.getData().add(new XYChart.Data("Avr", ss.getQuantiteProduitsVendusParMois("-04-")));
        seriesP.getData().add(new XYChart.Data("Mai", ss.getQuantiteProduitsVendusParMois("-05-")));
        seriesP.getData().add(new XYChart.Data("Jun", ss.getQuantiteProduitsVendusParMois("-06-")));
        seriesP.getData().add(new XYChart.Data("Jui", ss.getQuantiteProduitsVendusParMois("-07-")));
        seriesP.getData().add(new XYChart.Data("Aoû", ss.getQuantiteProduitsVendusParMois("-08-")));
        seriesP.getData().add(new XYChart.Data("Sep", ss.getQuantiteProduitsVendusParMois("-09-")));
        seriesP.getData().add(new XYChart.Data("Oct", ss.getQuantiteProduitsVendusParMois("-10-")));
        seriesP.getData().add(new XYChart.Data("Nov", ss.getQuantiteProduitsVendusParMois("-11-")));
        seriesP.getData().add(new XYChart.Data("Déc", ss.getQuantiteProduitsVendusParMois("-12-")));
        
        lineChart.getData().addAll(seriesP);
        double x = ss.getNombreProduits();
        double m = ss.getNombreProduitsVendus();
        nbTot.setText("Nombre Total : " + ((Double)x).toString()+ "%");
        double prc = (m/x)*100;
        float k = (float) Math.round(prc * 100) / 100;
        pourcentage.setText((String.valueOf(k)) + "% Vendus");
        progress.setProgress(k/100);
    }

    

}
