package main;


import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class Grafico extends JFrame {


	public static void mostrar(double[] resposta, double[] saidas, double [] saida2,String nome) {
		DefaultCategoryDataset dados = new DefaultCategoryDataset();
		int k ;
		for (k = 0; k < saidas.length; k++) {
			dados.addValue(saidas[k],"Saida Obitida Treino",k+"");
		}
		for (int i = 0; i < saida2.length; i++) {
			dados.addValue(saida2[i],"Saida Obitida Previsão",(i+k)+"");
		}
		int j;
		
		for (j = 0; j < resposta.length; j++) {
			dados.addValue(resposta[j],"Saida Desejada ",""+j);
		}
	
		JFreeChart grafico = ChartFactory.createLineChart(nome, "Nº", "Saída", dados, PlotOrientation.VERTICAL,
				true, true, true);
		grafico.getCategoryPlot().getRenderer().setSeriesPaint(0, Color.RED );
		grafico.getCategoryPlot().getRenderer().setSeriesPaint(1, Color.ORANGE );
		grafico.getCategoryPlot().getRenderer().setSeriesPaint(2, Color.BLUE );
        
        
		JFrame frame = new JFrame("Minha janela");
		frame.add(new ChartPanel(grafico));

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
	

	}
	

}
