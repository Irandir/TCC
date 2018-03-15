package main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;

import adaline.AdalineNaoLinear;
import adaline.Adaline;
import normaliza.Normalizar;

public class Principal {
	static ArrayList<Double> dados;

	public static void main(String[] args) {
		dados = new ArrayList<Double>();
		leituraDeArquivo();
		// janela de teste e treino
		int colunas = 12;
		int linhas = 100;
		int tamanhoDaTeste = 30;// ira subtrair da linhas
		double taxaDeAprendizado = 0.3;
		int interacoes = 100;
		int nErro = 3;
		double base[][] = new double[linhas][colunas];
		double respostas[] = new double[linhas];

		// recebendos os dados
		for (int i = 0; i < base.length; i++) {
			for (int j = 0; j < base[0].length; j++) {
				base[i][j] = dados.get(j + i);
			}
			respostas[i] = dados.get(base[0].length + i);
		}
		/*
		 * for (int i = 0; i < respostas.length; i++) {
		 * System.out.print("f(u) -->["); for (int j = 0; j < base[0].length;
		 * j++) { System.out.print(base[i][j]+", "); }
		 * System.out.println("] y --> "+respostas[i]); } System.out.println();
		 */
		double[][] baseNormalizada = Normalizar.normalizandoBase2(respostas, base);
		double[] respostasNormalizada = Normalizar.normalizandoResposta(respostas, base);
		Adaline a = new Adaline();
		a.executar(baseNormalizada, respostasNormalizada, tamanhoDaTeste, taxaDeAprendizado, interacoes);

		double erros[] = new double[linhas];
		int cont = 0;
		double[] respObitida = new double[a.respObitidaTreino.length];
		for (int i = 0; i < a.respObitidaTreino.length; i++) {
			respObitida[i] = Normalizar.desnormalizando(respostas, base, a.respObitidaTreino[i]);
			erros[cont] = respostas[cont] - respObitida[i];
			// System.out.println(respostas[cont]+" "+respObitida[i]+"
			// e-->"+erros[cont]);
			cont++;
		}
		double[] respObitida2 = new double[a.respObitidaTeste.length];
		for (int i = 0; i < a.respObitidaTeste.length; i++) {
			respObitida2[i] = Normalizar.desnormalizando(respostas, base, a.respObitidaTeste[i]);
			erros[cont] = respostas[cont] - respObitida2[i];
			// System.out.println(respostas[cont]+" "+respObitida2[i]+"
			// e-->"+erros[cont]);
			cont++;
		}

		new Grafico().mostrar(respostas, respObitida, respObitida2, "Adaline");

		// --------------------------AL_PARA_ANL---------------------------------------------------
		double base2[][] = new double[linhas - nErro][colunas + nErro];
		cont = 0;
		int j = 0;
		double respostas2[] = new double[linhas - nErro];
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

		/*
		 * for (int i = 0; i < base2.length; i++) { for (int k = 0; k <
		 * base2[0].length; k++) { System.out.print(base2[i][k]+" "); }
		 * System.out.println(" y -->"+respostas2[i]); } System.out.println();
		 */
		double base2Normalizada[][] = Normalizar.normalizandoBase2(respostas2, base2);
		double respostas2Normalizada[] = Normalizar.normalizandoResposta(respostas2, base2);
		/*
		 * for (int i = 0; i < base2Normalizada.length; i++) { for (int k = 0; k
		 * < base2Normalizada[0].length; k++) {
		 * System.out.print(base2Normalizada[i][k]+" "); }
		 * System.out.println(" y -->"+respostas2Normalizada[i]); }
		 * System.out.println();
		 */
		// --------------------------------------------------------------
		AdalineNaoLinear b = new AdalineNaoLinear();
		b.executar(base2Normalizada, respostas2Normalizada, tamanhoDaTeste, taxaDeAprendizado, interacoes);

		double erros2[] = new double[linhas - nErro];
		cont = 0;
		double[] respObitida3 = new double[b.respObitidaTreino.length];
		for (int i = 0; i < b.respObitidaTreino.length; i++) {
			respObitida3[i] = Normalizar.desnormalizando(respostas2, base2, b.respObitidaTreino[i]);
			erros2[cont] = respostas2[cont] - respObitida3[i];
			// System.out.println(respostas2[cont]+" "+respObitida3[i]+"
			// e-->"+erros2[cont]);
			cont++;
		}
		double[] respObitida4 = new double[b.respObitidaTeste.length];
		for (int i = 0; i < b.respObitidaTeste.length; i++) {
			respObitida4[i] = Normalizar.desnormalizando(respostas2, base2, b.respObitidaTeste[i]);
			erros2[cont] = respostas2[cont] - respObitida4[i];
			// System.out.println(respostas2[cont]+" "+respObitida4[i]+"
			// e-->"+erros2[cont]);
			cont++;
		}
		new Grafico().mostrar(respostas2, respObitida3, respObitida4, "AL_Para_ANL");

		// ------------------------------------------------------------------------------------------------------
		AdalineNaoLinear c = new AdalineNaoLinear();
		c.executar(baseNormalizada, respostasNormalizada, tamanhoDaTeste, taxaDeAprendizado, interacoes);

		erros = new double[linhas];
		cont = 0;
		respObitida = new double[c.respObitidaTreino.length];
		for (int i = 0; i < c.respObitidaTreino.length; i++) {
			respObitida[i] = Normalizar.desnormalizando(respostas, base, c.respObitidaTreino[i]);
			erros[cont] = respostas[cont] - respObitida[i];
			// System.out.println(respostas[cont]+" "+respObitida[i]+"
			// e-->"+erros[cont]);
			cont++;
		}
		respObitida2 = new double[c.respObitidaTeste.length];
		for (int i = 0; i < c.respObitidaTeste.length; i++) {
			respObitida2[i] = Normalizar.desnormalizando(respostas, base, c.respObitidaTeste[i]);
			erros[cont] = respostas[cont] - respObitida2[i];
			// System.out.println(respostas[cont]+" "+respObitida2[i]+"
			// e-->"+erros[cont]);
			cont++;
		}

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
			System.out.println(respostas2[cont]+" "+respObitida3[i]+"e-->"+erros2[cont]);
			cont++;
		}
		respObitida4 = new double[d.respObitidaTeste.length];
		for (int i = 0; i < d.respObitidaTeste.length; i++) {
			respObitida4[i] = Normalizar.desnormalizando(respostas2, base2, d.respObitidaTeste[i]);
			erros2[cont] = respostas2[cont] - respObitida4[i];
			System.out.println(respostas2[cont]+" "+respObitida4[i]+"e-->"+erros2[cont]);
			cont++;
		}
		new Grafico().mostrar(respostas2, respObitida3, respObitida4, "ANL_Para_AL");
		System.out.println(a.erroMedioQuadraticoTreino);
		System.out.println(b.erroMedioQuadraticoTreino);
		System.out.println(c.erroMedioQuadraticoTreino);
		System.out.println(d.erroMedioQuadraticoTreino);
		System.out.println(a.erroMedioQuadraticoTeste);
		System.out.println(b.erroMedioQuadraticoTeste);
		System.out.println(c.erroMedioQuadraticoTeste);
		System.out.println(d.erroMedioQuadraticoTeste);
	}

	public static void leituraDeArquivo() {

		BufferedReader br = null;
		int Linha = 0;

		try {

			String path = Principal.class.getResource("/base/airlines.txt").getPath();
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
