package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;

import javax.lang.model.element.QualifiedNameable;

import adaline.AdalineNaoLinear;
import adaline.Adaline;
import normaliza.Normalizar;

public class Principal {
	public static ArrayList<Double> dados;

	public static void main(String[] args) {
		dados = new ArrayList<Double>();
		leituraDeArquivo();
		// janela de teste e treino
		int colunas = 3;
		int linhas = 279;
		int tamanhoDaTeste = 30;// ira subtrair da linhas
		double taxaDeAprendizado = 0.02;
		int interacoes = 1000;
		int nErro = 3;
	
		/*	int colunas = 12;
		int linhas = 132;
		int tamanhoDaTeste = 30;// ira subtrair da linhas
		double taxaDeAprendizado = 0.3;*/
		
		
		double base[][] = new double[linhas][colunas];
		double respostas[] = new double[linhas];
		double erroMedioQuadraticoTreino[] = new double[4];
		double erroMedioQuadraticoTeste[] = new double[4];
		double erros[] = new double[linhas];
		int cont = 0;
		double base2[][] = new double[linhas - nErro][colunas + nErro];
		double respostas2[] = new double[linhas - nErro];
		
		// recebendos os dados
		for (int i = 0; i < base.length; i++) {
			for (int j = 0; j < base[0].length; j++) {
				base[i][j] = dados.get(j + i);
			}
			respostas[i] = dados.get(base[0].length + i);
		}
		
		double[][] baseNormalizada = Normalizar.normalizandoBase2(respostas, base);
		double[] respostasNormalizada = Normalizar.normalizandoResposta(respostas, base);
		
		Adaline a = new Adaline();
		a.executar(baseNormalizada, respostasNormalizada, tamanhoDaTeste, taxaDeAprendizado, interacoes);

		double[] respObitida = new double[a.respObitidaTreino.length];
		
		for (int i = 0; i < a.respObitidaTreino.length; i++) {
			respObitida[i] = Normalizar.desnormalizando(respostas, base, a.respObitidaTreino[i]);
			erros[cont] = respostas[cont] - respObitida[i];
			erroMedioQuadraticoTreino[0] += Math.pow(erros[cont],2);
			cont++;
		}
		erroMedioQuadraticoTreino[0] = erroMedioQuadraticoTreino[0] / (baseNormalizada.length - tamanhoDaTeste);
		
		double[] respObitida2 = new double[a.respObitidaTeste.length];
		for (int i = 0; i < a.respObitidaTeste.length; i++) {
			respObitida2[i] = Normalizar.desnormalizando(respostas, base, a.respObitidaTeste[i]);
			erros[cont] = respostas[cont] - respObitida2[i];
			erroMedioQuadraticoTeste[0] += Math.pow(erros[cont],2);
			cont++;
		}
		erroMedioQuadraticoTeste[0] = erroMedioQuadraticoTeste[0] / tamanhoDaTeste;
		
		new Grafico().mostrar(respostas, respObitida, respObitida2, "Adaline");

		// --------------------------AL_PARA_ANL---------------------------------------------------

		cont = 0;
		int j = 0;
		for (int i = nErro; i < base.length; i++) {
			for (j = 0; j < base[0].length; j++) {
				base2[cont][j] = base[i][j];
			}
			for (int k = 0; k < nErro; k++) {
				base2[cont][j + k] = erros[k + cont];
			}
			respostas2[cont] = respostas[i];
			cont++;
		}

		double base2Normalizada[][] = Normalizar.normalizandoBase2(respostas2, base2);
		double respostas2Normalizada[] = Normalizar.normalizandoResposta(respostas2, base2);

		// --------------------------------------------------------------
		AdalineNaoLinear b = new AdalineNaoLinear();
		b.executar(base2Normalizada, respostas2Normalizada, tamanhoDaTeste, 0.5, interacoes);

		double erros2[] = new double[linhas - nErro];
		cont = 0;
		double[] respObitida3 = new double[b.respObitidaTreino.length];
		for (int i = 0; i < b.respObitidaTreino.length; i++) {
			respObitida3[i] = Normalizar.desnormalizando(respostas2, base2, b.respObitidaTreino[i]);
			erros2[cont] = respostas2[cont] - respObitida3[i];
			erroMedioQuadraticoTreino[1] += Math.pow(erros2[cont],2);
			cont++;
		}
		erroMedioQuadraticoTreino[1] = erroMedioQuadraticoTreino[1] / (baseNormalizada.length - tamanhoDaTeste);
		double[] respObitida4 = new double[b.respObitidaTeste.length];
		for (int i = 0; i < b.respObitidaTeste.length; i++) {
			respObitida4[i] = Normalizar.desnormalizando(respostas2, base2, b.respObitidaTeste[i]);
			erros2[cont] = respostas2[cont] - respObitida4[i];
			erroMedioQuadraticoTeste[1] += Math.pow(erros2[cont],2);
			cont++;
		}
		erroMedioQuadraticoTeste[1] = erroMedioQuadraticoTeste[1] / tamanhoDaTeste;
		new Grafico().mostrar(respostas2, respObitida3, respObitida4, "AL_Para_ANL");
		
		// ------------------------------------------------------------------------------------------------------
		AdalineNaoLinear c = new AdalineNaoLinear();
		c.executar(baseNormalizada, respostasNormalizada, tamanhoDaTeste, 0.5, interacoes);

		erros = new double[linhas];
		cont = 0;
		respObitida = new double[c.respObitidaTreino.length];
		for (int i = 0; i < c.respObitidaTreino.length; i++) {
			respObitida[i] = Normalizar.desnormalizando(respostas, base, c.respObitidaTreino[i]);
			erros[cont] = respostas[cont] - respObitida[i];
			erroMedioQuadraticoTreino[2] += Math.pow(erros[cont],2);
			cont++;
		}
		erroMedioQuadraticoTreino[2] = erroMedioQuadraticoTreino[2] / (baseNormalizada.length - tamanhoDaTeste);
		respObitida2 = new double[c.respObitidaTeste.length];
		for (int i = 0; i < c.respObitidaTeste.length; i++) {
			respObitida2[i] = Normalizar.desnormalizando(respostas, base, c.respObitidaTeste[i]);
			erros[cont] = respostas[cont] - respObitida2[i];
			erroMedioQuadraticoTeste[2] += Math.pow(erros[cont],2);
			cont++;
		}
		erroMedioQuadraticoTeste[2] = erroMedioQuadraticoTeste[2] / tamanhoDaTeste;

		new Grafico().mostrar(respostas, respObitida, respObitida2, "Adaline Não linear");

		// --------------------------ANL_PARA_AL---------------------------------------------------
		base2 = new double[linhas - nErro][colunas + nErro];
		cont = 0;
		j = 0;
		respostas2 = new double[linhas - nErro];
		for (int i = nErro; i < base.length; i++) {
			for (j = 0; j < base[0].length; j++) {
				base2[cont][j] = base[i][j];
			}
			for (int k = 0; k < nErro; k++) {
				base2[cont][j + k] = erros[k + cont];
			}
			respostas2[cont] = respostas[i];
			cont++;

		}
		base2Normalizada = Normalizar.normalizandoBase2(respostas2, base2);
		respostas2Normalizada = Normalizar.normalizandoResposta(respostas2, base2);
		Adaline d = new Adaline();
		d.executar(base2Normalizada, respostas2Normalizada, tamanhoDaTeste, taxaDeAprendizado, interacoes);
		
		erros2 = new double[linhas - nErro];
		cont = 0;
		respObitida3 = new double[d.respObitidaTreino.length];
		for (int i = 0; i < d.respObitidaTreino.length; i++) {
			respObitida3[i] = Normalizar.desnormalizando(respostas2, base2, d.respObitidaTreino[i]);
			erros2[cont] = respostas2[cont] - respObitida3[i];
			erroMedioQuadraticoTreino[3] += Math.pow(erros2[cont],2);
			cont++;
		}
		erroMedioQuadraticoTreino[3] = erroMedioQuadraticoTreino[3] / (baseNormalizada.length - tamanhoDaTeste);
		
		respObitida4 = new double[d.respObitidaTeste.length];
		for (int i = 0; i < d.respObitidaTeste.length; i++) {
			respObitida4[i] = Normalizar.desnormalizando(respostas2, base2, d.respObitidaTeste[i]);
			erros2[cont] = respostas2[cont] - respObitida4[i];
			erroMedioQuadraticoTeste[3] += Math.pow(erros2[cont],2);
			cont++;
		}
		erroMedioQuadraticoTeste[3] = erroMedioQuadraticoTeste[3] / tamanhoDaTeste;
		new Grafico().mostrar(respostas2, respObitida3, respObitida4, "ANL_Para_AL");
		System.out.println("erroMedioQuadraticoTreino  "+erroMedioQuadraticoTreino[0]);
		System.out.println("erroMedioQuadraticoTreino  "+erroMedioQuadraticoTreino[1]);
		System.out.println("erroMedioQuadraticoTreino  "+erroMedioQuadraticoTreino[2]);
		System.out.println("erroMedioQuadraticoTreino  "+erroMedioQuadraticoTreino[3]);
		System.out.println("___________________________");
		System.out.println("erroMedioQuadraticoTeste  "+erroMedioQuadraticoTeste[0]);
		System.out.println("erroMedioQuadraticoTeste  "+erroMedioQuadraticoTeste[1]);
		System.out.println("erroMedioQuadraticoTeste  "+erroMedioQuadraticoTeste[2]);
		System.out.println("erroMedioQuadraticoTeste  "+erroMedioQuadraticoTeste[3]);
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
