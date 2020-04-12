/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hypocampus.controller;
import java.awt.Dimension;
import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

/**
 *
 * @author bruno
 */
public class SprintStat extends JFrame {

    public SprintStat(String applicationTitle, String chartTitle, PieDataset pDataSet) {

        super(applicationTitle);

        JFreeChart graficoPizza3D = createChart(pDataSet, chartTitle);
        
        ChartPanel painelParaOGrafico = new ChartPanel(graficoPizza3D);
        
        painelParaOGrafico.setPreferredSize(new Dimension(800, 640));
        
        this.add(painelParaOGrafico);

    }

    private JFreeChart createChart(PieDataset pPieDataset, String pTituloGrafico) {

        JFreeChart grafico = ChartFactory.createPieChart3D(
                pTituloGrafico,
                pPieDataset,
                true,
                true,
                false
        );
        
        PiePlot3D plot = (PiePlot3D) grafico.getPlot();
        plot.setStartAngle(90);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        
        return grafico;
    }

}
