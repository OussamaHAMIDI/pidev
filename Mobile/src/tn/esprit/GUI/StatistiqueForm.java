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
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
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
public class StatistiqueForm extends Form {

    static Resources res;

    private boolean drawOnMutableImage;
    Font smallFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_SMALL, Font.STYLE_PLAIN);
    Font medFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_MEDIUM, Font.STYLE_PLAIN);
    Font largeFont = Font.createSystemFont(Font.FACE_SYSTEM, Font.SIZE_LARGE, Font.STYLE_PLAIN);

    public StatistiqueForm() {
        super("Statistiques", new BorderLayout());
        this.res = Main.stheme;

        EvaluationService es = new EvaluationService();
        List<Double> li = es.getVente();
        Container barChart = new Container(new BorderLayout());
        String[] titles = new String[]{"Jan", "Fev", "Mar", "Avr", "Mai", "Jun", "Jui", "Aou", "Sep", "Oct", "Nov", "Dec"};
        List<Double> values = new ArrayList<Double>();
        for (Double d : li) {
            double k = d.doubleValue();
            values.add(d);
        }
        int[] colors = new int[]{ColorUtil.MAGENTA, ColorUtil.LTGRAY, ColorUtil.YELLOW, ColorUtil.GREEN, ColorUtil.GRAY,
        ColorUtil.MAGENTA, ColorUtil.LTGRAY, ColorUtil.YELLOW, ColorUtil.GREEN, ColorUtil.GRAY,ColorUtil.GREEN, ColorUtil.GRAY};
        
        XYMultipleSeriesRenderer renderer = buildBarRenderer(colors);
        renderer.setOrientation(Orientation.HORIZONTAL);

        setChartSettings(renderer, "Nombre de vente / mois", "Mois", " Nombre de Vente", 0.5,
                12.5, 0, 30, 0xffffff, 0xffffff);
        renderer.setXLabels(12);
        renderer.setYLabels(12);

        renderer.addXTextLabel(1, "Jan");
        renderer.addXTextLabel(2, "Fev");
        renderer.addXTextLabel(3, "Mar");
        renderer.addXTextLabel(4, "Avr");
        renderer.addXTextLabel(5, "Mai");
        renderer.addXTextLabel(6, "Mai");
        renderer.addXTextLabel(7, "Mai");
        renderer.addXTextLabel(8, "Mai");
        renderer.addXTextLabel(9, "Mai");
        renderer.addXTextLabel(10, "Mai");
        renderer.addXTextLabel(11, "Mai");
        renderer.addXTextLabel(12, "Mai");

        //renderer.addXTextLabel(1, "Oct");
        int length = renderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer seriesRenderer = (XYSeriesRenderer) renderer.getSeriesRendererAt(i);
            seriesRenderer.setDisplayChartValues(true);
        }

        BarChart chart = new BarChart(buildBarDataset(titles, values), renderer,
                Type.DEFAULT);
        ChartComponent c = new ChartComponent(chart);
        renderer.setBackgroundColor(0x000000);
        // c.getStyle().setBgColor(0xffd700);
        barChart.setLayout(new BorderLayout());
        barChart.addComponent(BorderLayout.CENTER, c);
        this.add(CENTER, barChart);

        Command back = new Command("") {
            @Override
            public void actionPerformed(ActionEvent evt) {
                Main.shome.show();
            }
        };
        FontImage.setMaterialIcon(back, FontImage.MATERIAL_ARROW_BACK, "TitleCommand", 5);
        this.addCommand(back);
    }

    protected XYMultipleSeriesRenderer buildBarRenderer(int[] colors) {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(smallFont.getHeight() / 2);
        renderer.setChartTitleTextFont(smallFont);
        renderer.setLabelsTextSize(smallFont.getHeight() / 2);
        renderer.setLegendTextSize(smallFont.getHeight() / 2);
        //renderer.setBackgroundColor(0xffffff);
        renderer.setMargins(new int[]{-1, -1, -1, -1});

        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            renderer.addSeriesRenderer(r);
        }
        return renderer;
    }

    protected void setChartSettings(XYMultipleSeriesRenderer renderer, String title, String xTitle,
            String yTitle, double xMin, double xMax, double yMin, double yMax, int axesColor,
            int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        //renderer.setXLabelsPadding(30);
        renderer.setLabelsColor(labelsColor);
    }

    public boolean isDrawOnMutableImage() {
        return this.drawOnMutableImage;
    }

    protected XYMultipleSeriesDataset buildBarDataset(String[] titles, List<Double> values) {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        int length = titles.length;
        for (int i = 0; i < length; i++) {
            CategorySeries series = new CategorySeries(titles[i]);
            Double v = values.get(i);
//            int seriesLength = v.length;
//            for (int k = 0; k < seriesLength; k++) {
            series.add(v);
//            }
            dataset.addSeries(series.toXYSeries());
        }
        return dataset;
    }
}
