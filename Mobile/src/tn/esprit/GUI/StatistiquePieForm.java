/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.GUI;


import com.codename1.charts.views.BarChart;
import com.codename1.charts.views.BarChart.Type;
import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer.Orientation;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.Resources;
import java.util.ArrayList;
import java.util.List;
import tn.esprit.Services.EvaluationService;
import tn.esprit.app.Main;

/**
 *
 * @author Imen BenAbderrahmen
 */
public class StatistiquePieForm extends Form {

    static Resources res;

  

    public StatistiquePieForm() {
        super("Statistiques", new BorderLayout());
        this.res = Main.stheme;
        
        EvaluationService es = new EvaluationService();
        List<Integer> li = es.getVenteArtisan("41");
        System.out.println(li);

        createPieChartForm(this);
        

        Command back = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Main.shome.show();
            }
        };
        FontImage.setMaterialIcon(back, FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);
        this.addCommand(back);
    }

    private DefaultRenderer buildCategoryRenderer(int[] colors) {
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(15);
        renderer.setMargins(new int[]{20, 30, 15, 0});
        for (int color : colors) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(color);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    protected CategorySeries buildCategoryDataset(String title, double[] values) {
        CategorySeries series = new CategorySeries(title);
        int k = 0;
//        for (double value : values) {
            series.add("Produits Vendus" , values[0]);
            series.add("Produits Restants" , values[1]);
//        }
        return series;
    }

    public void createPieChartForm(Form f) {

        
        EvaluationService es = new EvaluationService();
        List<Integer> li = es.getVenteArtisan(Main.userConnected.getId());
        
        // Generate the values
        double[] values = new double[]{li.get(0).doubleValue(), li.get(1).doubleValue()-li.get(0).doubleValue()};
        float pourcentage = (li.get(0)*100)/li.get(1);
        int[] colors = new int[]{0xf4b342, 0x52b29a};
        DefaultRenderer renderer = buildCategoryRenderer(colors);
        renderer.setZoomButtonsVisible(true);
        renderer.setZoomEnabled(true);
        renderer.setChartTitleTextSize(20);
        renderer.setDisplayValues(true);
        renderer.setShowLabels(true);
        SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
        r.setHighlighted(true);

        // Create the chart ... pass the values and renderer to the chart object.
        
        PieChart chart = new PieChart(buildCategoryDataset("Project budget", values), renderer);

        // Wrap the chart in a Component so we can add it to a form
        ChartComponent c = new ChartComponent(chart);

        // Create a form and show it.
        f.setLayout(new BorderLayout());
        f.addComponent(BorderLayout.CENTER, c);
        Label prc = new Label("Pourcentage : "+ pourcentage);
        prc.setUIID("SignUpLabel");
        f.addComponent(BorderLayout.SOUTH, prc);
    }
}
