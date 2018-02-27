/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Presentation;

import Entities.Boutique;
import Entities.Produit;
import Entities.User;
import Services.BoutiqueService;
import Services.EvaluationService;
import Services.StatistiqueService;
import Services.UserService;
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
import javafx.scene.control.ScrollPane;

/**
 * FXML Controller class
 *
 * @author benab
 */
public class StatistiqueArtisanController implements Initializable {
    @FXML
    private LineChart<?, ?> lineChart;
    @FXML
    private PieChart pieChartProd;
    @FXML
    private BarChart<?, ?> barChartp;
    @FXML
    private NumberAxis y;
    @FXML
    private CategoryAxis axex;
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
        // TODO
        scrollStat.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        StatistiqueService ss = new StatistiqueService();
        BoutiqueService bs = new BoutiqueService();
        UserService us = new UserService();
        User u = us.getUserById(29);        
        List<Boutique> lb = bs.lireBoutique(u);
        List<Produit> lp = new ArrayList<Produit>();
        for(Boutique b : lb){
            lp.addAll(bs.lireProduitsParBoutique(b));
            b.setListProduit(lp);
        }
        
        XYChart.Series seriesP = new XYChart.Series();
        seriesP.getData().add(new XYChart.Data("Janvier", ss.getQuantiteProduitsVendusParMois(lb,"-01-")));
        seriesP.getData().add(new XYChart.Data("Février", ss.getQuantiteProduitsVendusParMois(lb,"-02-")));
        seriesP.getData().add(new XYChart.Data("Mars", ss.getQuantiteProduitsVendusParMois(lb,"-03-")));
        seriesP.getData().add(new XYChart.Data("Avril", ss.getQuantiteProduitsVendusParMois(lb,"-04-")));
        seriesP.getData().add(new XYChart.Data("Mai", ss.getQuantiteProduitsVendusParMois(lb,"-05-")));
        seriesP.getData().add(new XYChart.Data("Juin", ss.getQuantiteProduitsVendusParMois(lb,"-06-")));
        seriesP.getData().add(new XYChart.Data("Juillet", ss.getQuantiteProduitsVendusParMois(lb,"-07-")));
        seriesP.getData().add(new XYChart.Data("Août", ss.getQuantiteProduitsVendusParMois(lb,"-08-")));
        seriesP.getData().add(new XYChart.Data("Septembre", ss.getQuantiteProduitsVendusParMois(lb,"-09-")));
        seriesP.getData().add(new XYChart.Data("Octobre", ss.getQuantiteProduitsVendusParMois(lb,"-10-")));
        seriesP.getData().add(new XYChart.Data("Novembre", ss.getQuantiteProduitsVendusParMois(lb,"-11-")));
        seriesP.getData().add(new XYChart.Data("Décembre", ss.getQuantiteProduitsVendusParMois(lb,"-12-")));
        lineChart.getData().addAll(seriesP);
        
        
        EvaluationService es = new EvaluationService();
        List<Produit> topTenProduits = ss.getTopTenProduits(lb);
        System.out.println(topTenProduits);
        XYChart.Series setP = new XYChart.Series<>();
        for (Produit p : topTenProduits) {
            if (es.produitHasNote(p)) {
                setP.getData().add(new XYChart.Data(p.getId() + ":" + p.getReference(), es.getNoteProduit(p)));
            }
        }
        barChartp.getData().addAll(setP);
        
        
        ObservableList<PieChart.Data> pieChartDataProduits = FXCollections.observableArrayList(
                new PieChart.Data("Nombre total de produits : " + ss.getNombreProduits(lb), ss.getNombreProduits(lb)),
                new PieChart.Data("Nombre de produits Vendus : " + ss.getNombreProduitsVendus(lb), ss.getNombreProduitsVendus(lb))
        );
        pieChartProd.setData(pieChartDataProduits);
        pieChartProd.setLegendSide(Side.RIGHT);
        double x = ss.getNombreProduits(lb);
        double m = ss.getNombreProduitsVendus(lb);
        nbTot.setText("Nombre Total : " + ((Double)x).toString()+ "%");
        double prc = (m/x)*100;
        float k = (float) Math.round(prc * 100) / 100;
        pourcentage.setText((String.valueOf(k)) + "% Vendus");
        progress.setProgress(k/100);
    }    
    
}
