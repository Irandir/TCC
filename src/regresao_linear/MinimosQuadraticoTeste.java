package regresao_linear;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Jama.Matrix;
import main.Principal;

public class MinimosQuadraticoTeste {

	static List<Double> dados = new ArrayList<Double>();

	public static void main(String[] args) {
		leituraDeArquivo();
		int linhas = 100;
		int colunas = 15;

		double entradas[][] = new double[colunas][linhas];
		double respostas[][] = new double[1][linhas];

		// recebendos os dados
		int j = 0;
		for (int i = 0; i < entradas.length - 1; i++) {
			for (j = 0; j < entradas[0].length; j++) {
				entradas[i][j] = dados.get(j + i);
			}
			respostas[0][i] = dados.get(entradas[0].length + i);
		}
		for (int i = 0; i < entradas[0].length; i++) {
			entradas[entradas.length - 1][i] = 1;
		}
		Matrix matrixEntradas = new Matrix(entradas);
		Matrix matrixRespostas = new Matrix(respostas);
		double r[][] = calcularMininoQuadratico(matrixEntradas, matrixRespostas);
		for (int i = 0; i < r.length; i++) {
			for (j = 0; j < r[0].length; j++) {
				System.out.print(r[i][j] + " ");
			}
			System.out.println();
		}
		double sum = 0;
		for (int i = 0; i < entradas[0].length; i++) {
			sum = 0;
			for (j = 0; j < r[0].length; j++) {
				sum+= entradas[j][i] * r[0][j];
			}
			System.out.println(sum);
		}

	}

	public static double[][] calcularMininoQuadratico(Matrix entradas, Matrix resposta) {
		double[][] r = (resposta.times(entradas.transpose())).times((entradas.times(entradas.transpose())).inverse())
				.getArray();
		return r;
	}

	public static void leituraDeArquivo() {

		BufferedReader br = null;
		int Linha = 0;

		try {

			String path = Principal.class.getResource("/base/education.txt").getPath();
			br = new BufferedReader(new FileReader(path));
			int aux = 0;
			String linha = null;
			while ((linha = br.readLine()) != null) {
				dados.add(Double.parseDouble(linha));

			}

			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}
}
