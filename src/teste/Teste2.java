package teste;

import java.util.Random;

import normaliza.Normalizar;

public class Teste2 {
	public double[] respObitidaTreino;
	public double erroMedioQuadraticoTreino = 0.0;
	public double erroMedioQuadraticoTeste = 0.0;
	Random random = new Random();
	double bias;
	double[] pesos;

	public void treino(double[][] baseNormalizada, double[] respostaNormalizada, int nTeste, double n, int interacoes) {
		bias = random.nextFloat();
		pesos = new double[baseNormalizada[0].length];
		for (int i = 0; i < pesos.length; i++) {
			pesos[i] = random.nextFloat();
		}
		respObitidaTreino = new double[baseNormalizada.length - nTeste];
		

		double saida = 0;
		int i = 0;
		int j = 0;
		int count = 0;
		double erro = 0;
		while (count < interacoes) {
			saida = 0;
			erro = 0;
			erroMedioQuadraticoTreino = 0;
			for (i = 0; i < baseNormalizada.length - nTeste; i++) {
				// cada instancia da base
				saida = 0;

				for (j = 0; j < baseNormalizada[0].length; j++) {
					saida += baseNormalizada[i][j] * pesos[j];
				}
				saida += bias;

				erro = respostaNormalizada[i] - saida;

				bias = bias + n * erro;

				for (j = 0; j < pesos.length; j++) {
					pesos[j] = pesos[j] + n * erro * baseNormalizada[i][j];
				}

				erroMedioQuadraticoTreino += Math.pow(erro, 2);

			}

			erroMedioQuadraticoTreino = erroMedioQuadraticoTreino / (baseNormalizada.length - nTeste);

			// System.out.println(erroMedioQuadratico);
			count++;
		}
		// treino
		for (i = 0; i < baseNormalizada.length - nTeste; i++) {
			saida = 0;
			for (int k2 = 0; k2 < baseNormalizada[0].length; k2++) {
				saida += baseNormalizada[i][k2] * pesos[k2];
			}
			saida += bias;
			respObitidaTreino[i] = saida;

		}

	}

	public double teste(double[] baseNormalizada) {
		// teste
		double saida = 0;
		for (int k2 = 0; k2 < baseNormalizada.length; k2++) {
			saida += baseNormalizada[k2] * pesos[k2];
		}
		saida += bias;
		return saida;
	}
}
