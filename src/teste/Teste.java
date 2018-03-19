package teste;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import adaline.Adaline;
import main.Grafico;
import main.Principal;
import normaliza.Normalizar;

public class Teste {
	static ArrayList<Double> dados;
	public static void main(String[] args) {
		dados = new ArrayList<Double>();
		leituraDeArquivo();
		// janela de teste e treino
		int colunas = 15;
		int linhas = 100;
		int tamanhoDaTeste = 30;// ira subtrair da linhas
		double taxaDeAprendizado = 0.3;
		int interacoes = 100;
		int nErro = 5;
		double base[][] = new double[linhas][colunas];
		double respostas[] = new double[linhas];

		// recebendos os dados
		for (int i = 0; i < base.length; i++) {
			for (int j = 0; j < base[0].length; j++) {
				base[i][j] = dados.get(j + i);
			}
			respostas[i] = dados.get(base[0].length + i);
		}
	
		double[][] baseNormalizada = Normalizar.normalizandoBase2(respostas, base);
		double[] respostasNormalizada = Normalizar.normalizandoResposta(respostas, base);
		Teste2 a = new Teste2();
		a.treino(baseNormalizada, respostasNormalizada, tamanhoDaTeste, taxaDeAprendizado, interacoes);

		double erros[] = new double[linhas];
		int cont = 0;
		double[] respObitida = new double[a.respObitidaTreino.length];
		for (int i = 0; i < a.respObitidaTreino.length; i++) {
			respObitida[i] = Normalizar.desnormalizando(respostas, base, a.respObitidaTreino[i]);
			erros[cont] = respostas[cont] - respObitida[i];
			cont++;
		}
		double[] respObitida2 = new double[tamanhoDaTeste];
		double saida = 0;
		double array[] = baseNormalizada[baseNormalizada.length-1];
		for (int i = 0; i < tamanhoDaTeste; i++) {
			saida = a.teste(array);
			respObitida2[i] = Normalizar.desnormalizando(respostas, base, saida);
			erros[cont] = respostas[cont] - respObitida2[i];
			array = ordem(array, saida);
			cont++;
		}

		new Grafico().mostrar(respostas, respObitida, respObitida2, "Adaline");

	}
	
	public static double[] ordem(double [] array,double value){
		double[] ordem = new double[array.length];
		for (int i = 0; i < ordem.length-1; i++) {
			ordem[i] = array[i+1];
		}
		ordem[ordem.length-1] = value;
		return ordem;
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
