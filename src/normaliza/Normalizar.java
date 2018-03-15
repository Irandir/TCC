package normaliza;

import java.util.ArrayList;

public class Normalizar {
	
/*	public static double[][] normalizandoBase(double [][]base) {
		
		Valor valor = new Valor(base);
		double[][]baseNormalizada = new double[base.length][base[0].length];
		for (int i = 0; i < base.length; i++) {
			for (int j = 0; j < base[0].length; j++) {
				baseNormalizada[i][j] = ((double) (base[i][j] - valor.getMin()))
						/ (valor.getMax() - valor.getMin());
			}
			
		}
		return baseNormalizada;
	}
	
	public static double[] normalizando(double [] array) {
		double[]respostaTreinoNormalizada = new double[array.length];
		Valor valor1 = new Valor(array);
		for (int i = 0; i < array.length; i++) {
			respostaTreinoNormalizada[i] = ((double) (array[i] - valor1.getMin()))
					/ (valor1.getMax() - valor1.getMin());
		}
		return respostaTreinoNormalizada;
	}
	public static ArrayList<Double> normalizando(ArrayList<Double>  array) {
		ArrayList<Double>respostaTreinoNormalizada = new ArrayList<Double>();
		Valor valor1 = new Valor(array);
		for (int i = 0; i < array.size(); i++) {
			respostaTreinoNormalizada.add(((double) (array.get(i) - valor1.getMin()))
					/ (valor1.getMax() - valor1.getMin()));
		}
		return respostaTreinoNormalizada;
	}
	public static double desnormalizandoResposta(double[] respostas,double x) {
		Valor valor1 = new Valor(respostas);
		return x * (valor1.getMax() - valor1.getMin()) + valor1.getMin();

	}*/
	
	public static double[][] normalizandoBase2(double[] respostas,double[][] base) {

		Valor valor =new Valor(base);
		Valor valor2 =new Valor(respostas);
		double max = 0;
		double min = 0;
		if(valor.getMax()<=valor2.getMax()){
			max = valor2.getMax();
		}else{
			max = valor.getMax();
		}if(valor.getMin()<=valor2.getMin()){
			min = valor.getMin();
		}else{
			min = valor2.getMin();
		}
		double[][]baseNormalizada = new double[base.length][base[0].length];
		for (int i = 0; i < base.length; i++) {
			for (int j = 0; j < base[0].length; j++) {
				baseNormalizada[i][j] = ((double) (base[i][j] - min))
						/ (max - min);
			}
			
		}
		return baseNormalizada;

	}
	public static double[] normalizandoResposta(double[] respostas,double[][] base) {
		double[]respostaTreinoNormalizada = new double[respostas.length];
		Valor valor =new Valor(base);
		Valor valor2 =new Valor(respostas);
		double max = 0;
		double min = 0;
		if(valor.getMax()<=valor2.getMax()){
			max = valor2.getMax();
		}else{
			max = valor.getMax();
		}if(valor.getMin()<=valor2.getMin()){
			min = valor.getMin();
		}else{
			min = valor2.getMin();
		}
		for (int i = 0; i < respostas.length; i++) {
			respostaTreinoNormalizada[i] = ((double) (respostas[i] - min))
					/ (max - min);
		}
		return respostaTreinoNormalizada;
	}
	public static double desnormalizando(double[] respostas,double[][] base,double x) {
		Valor valor =new Valor(base);
		Valor valor2 =new Valor(respostas);
		double max = 0;
		double min = 0;
		if(valor.getMax()<=valor2.getMax()){
			max = valor2.getMax();
		}else{
			max = valor.getMax();
		}if(valor.getMin()<=valor2.getMin()){
			min = valor.getMin();
		}else{
			min = valor2.getMin();
		}
		return x * (max - min) + min;

	}
}
