package com.sb.goldandsilver.fragments.graph;

import android.content.Context;
import android.graphics.Color;

import com.sb.goldandsilver.mvctext.GsTextItem;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by namudak on 2015-10-15.
 */
public class GsLineChart extends AbstractChart{

    public GsLineChart() {}

    @Override
    public GraphicalView getGraphView(Context context, List list) {

        List<GsTextItem> gsList= (ArrayList<GsTextItem>)list;

        // x labels(yyyy-mm-dd)
        String[] strYmd = new String[gsList.size()];

        // Series Set
        XYSeries goldSeries = new XYSeries("LBMA Gold price");
        XYSeries silverSeries = new XYSeries("LBMA Silver price");
        for( int i = 0; i<gsList.size();i++) {
            strYmd[i] = gsList.get(i).getTime();
            goldSeries.add(i, Float.parseFloat(gsList.get(i).getGoldAm()));
            silverSeries.add(i, 60.0 * Float.parseFloat(gsList.get(i).getSilver()));
        }

        // Creating a dataset to hold series
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        // Add series to the dataset
        dataset.addSeries(goldSeries);
        dataset.addSeries(silverSeries);

        // Now we create Gold Renderer
        XYSeriesRenderer goldRenderer = new XYSeriesRenderer();
        goldRenderer.setLineWidth(2);
        goldRenderer.setColor(Color.YELLOW);
        goldRenderer.setDisplayBoundingPoints(true);
        goldRenderer.setPointStyle(PointStyle.POINT);
        goldRenderer.setPointStrokeWidth(3);

        // Now we create Silver Renderer
        XYSeriesRenderer silverRenderer = new XYSeriesRenderer();
        silverRenderer.setLineWidth(2);
        silverRenderer.setColor(Color.GRAY);
        silverRenderer.setDisplayBoundingPoints(true);
        silverRenderer.setPointStyle(PointStyle.POINT);
        silverRenderer.setPointStrokeWidth(3);

        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
        multiRenderer.setApplyBackgroundColor(true);
        multiRenderer.setBackgroundColor(Color.BLACK);
        multiRenderer.setShowGrid(true);
        multiRenderer.setXLabels(0);
        multiRenderer.setChartTitle("Gold & Silver Chart");
        multiRenderer.setXTitle("Year 2015");
        multiRenderer.setYTitle("Amount in US Dollars");
        //multiRenderer.setZoomButtonsVisible(true);
        for( int i= 0;i<strYmd.length;i++){
            if (i % 30 == 0) {
                multiRenderer.addXTextLabel(i, strYmd[i].substring(4, 8));
            }
        }

        // Adding gold and silver renderer to multiRenderer
        multiRenderer.addSeriesRenderer(goldRenderer);
        multiRenderer.addSeriesRenderer(silverRenderer);

        // Create view
        GraphicalView chartView = ChartFactory.getLineChartView(
                context,
                dataset,
                multiRenderer);

        return chartView;
    }

    @Override
    public GraphicalView getGraphView(Context context) {
        return null;
    }
    
    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getDesc() {
        return null;
    }
}
