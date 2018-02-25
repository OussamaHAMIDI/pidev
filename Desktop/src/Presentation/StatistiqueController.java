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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class StatistiqueController implements Initializable {

    @FXML
    private PieChart pieChart;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        StatistiqueService ss = new StatistiqueService();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Nombre d'Administrateurs : " + ss.getNombreAdministrateurs(), ss.getNombreAdministrateurs()),
                new PieChart.Data("Nombre de Clients : " + ss.getNombreClients(), ss.getNombreClients()),
                new PieChart.Data("Nombre d'Artisans : " + ss.getNombreArtisans(), ss.getNombreArtisans())
        );
        pieChart.setData(pieChartData);

        ObservableList<PieChart.Data> pieChartDataProduits = FXCollections.observableArrayList(
                new PieChart.Data("Nombre total de produits : " + ss.getNombreProduits(), ss.getNombreProduits()),
                new PieChart.Data("Nombre de produits Vendus : " + ss.getNombreProduitsVendus(), ss.getNombreProduitsVendus())
        );
        pieChartProd.setData(pieChartDataProduits);

        EvaluationService es = new EvaluationService();
        List<Produit> topTenProduits = ss.getTopTenProduits();
        System.out.println(topTenProduits);
        XYChart.Series setP = new XYChart.Series<>();
        for (Produit p : topTenProduits) {
            if (es.produitHasNote(p)) {
                setP.getData().add(new XYChart.Data("Produit : " + p.getId(), es.getNoteProduit(p)));
            }
        }
        barChartp.getData().addAll(setP);

        List<Boutique> topTenBoutiques = ss.getTopTenBoutiques();
        System.out.println(topTenBoutiques);
        XYChart.Series setB = new XYChart.Series<>();
        for (Boutique b : topTenBoutiques) {
            if (es.boutiqueHasNote(b)) {
                setB.getData().add(new XYChart.Data("Boutique : " + b.getId(), es.getNoteBoutique(b)));
            }
        }
        barChartb.getData().addAll(setB);

        XYChart.Series seriesP = new XYChart.Series();
        seriesP.getData().add(new XYChart.Data("Janvier", ss.getQuantiteProduitsVendusParMois("-01-")));
        seriesP.getData().add(new XYChart.Data("Février", ss.getQuantiteProduitsVendusParMois("-02-")));
        seriesP.getData().add(new XYChart.Data("Mars", ss.getQuantiteProduitsVendusParMois("-03-")));
        seriesP.getData().add(new XYChart.Data("Avril", ss.getQuantiteProduitsVendusParMois("-04-")));
        seriesP.getData().add(new XYChart.Data("Mai", ss.getQuantiteProduitsVendusParMois("-05-")));
        seriesP.getData().add(new XYChart.Data("Juin", ss.getQuantiteProduitsVendusParMois("-06-")));
        seriesP.getData().add(new XYChart.Data("Juillet", ss.getQuantiteProduitsVendusParMois("-07-")));
        seriesP.getData().add(new XYChart.Data("Août", ss.getQuantiteProduitsVendusParMois("-08-")));
        seriesP.getData().add(new XYChart.Data("Septembre", ss.getQuantiteProduitsVendusParMois("-09-")));
        seriesP.getData().add(new XYChart.Data("Octobre", ss.getQuantiteProduitsVendusParMois("-10-")));
        seriesP.getData().add(new XYChart.Data("Novembre", ss.getQuantiteProduitsVendusParMois("-11-")));
        seriesP.getData().add(new XYChart.Data("Décembre", ss.getQuantiteProduitsVendusParMois("-12-")));
        lineChart.getData().addAll(seriesP);
       

    }

}
