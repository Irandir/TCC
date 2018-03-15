package adaline;

import java.util.Random;

import normaliza.Normalizar;

public class AdalineNaoLinear {
	public double[] respObitidaTreino;
	public double[] respObitidaTeste;
	public double erroMedioQuadraticoTreino = 0.0,erroMedioQuadraticoTeste = 0.0;
	Random random = new Random();

	public void executar(double[][] baseNormalizada, double[] respostaNormalizada, int nTeste,double n,int interacoes) {
		double bias = random.nextFloat();
		double[] pesos = new double[baseNormalizada[0].length];
		for (int i = 0; i < pesos.length; i++) {
			pesos[i] = random.nextFloat();
		}
		respObitidaTreino = new double[baseNormalizada.length-nTeste];
		respObitidaTeste = new double[nTeste];
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
				saida = sigmoide(saida);
				erro = respostaNormalizada[i] - saida;

				bias = bias + n * erro;

				for (j = 0; j < pesos.length; j++) {
					pesos[j] = pesos[j] + baseNormalizada[i][j] * n * erro * saida * (1 - saida);
				}

				erroMedioQuadraticoTreino += Math.pow(erro, 2);

				/*System.out.print("i -->" + i + " y-->" + respostaNormalizada[i]+" x[");
				for (int k = 0; k < baseNormalizada[0].length; k++) {
					System.out.print(baseNormalizada[i][k]+" ");
				}
				System.out.println("]");*/
			}
			
			erroMedioQuadraticoTreino = erroMedioQuadraticoTreino / (baseNormalizada.length - nTeste);

			// System.out.println(erroMedioQuadratico);
			count++;
		}
		//treino
		for (i = 0; i < baseNormalizada.length - nTeste; i++) {
			saida = 0;
			for (int k2 = 0; k2 < baseNormalizada[0].length; k2++) {
				saida += baseNormalizada[i][k2] * pesos[k2];
			}
			saida += bias;
			saida = sigmoide(saida);
			respObitidaTreino[i] = saida;
		}
		
		//teste
		int cont = 0;
		for (int k = (baseNormalizada.length-nTeste); k < respostaNormalizada.length; k++) {
			saida = 0;
			for (int k2 = 0; k2 < baseNormalizada[0].length; k2++) {
				saida += baseNormalizada[k][k2] * pesos[k2];
			}
			saida += bias;
			saida = sigmoide(saida);
			respObitidaTeste[cont] = saida;
			erroMedioQuadraticoTeste += Math.pow((respostaNormalizada[cont]-saida), 2);
			cont++;
			
		}erroMedioQuadraticoTeste = erroMedioQuadraticoTeste / nTeste;
	}
	public static double sigmoide(double v) {
        return 1.0 / (1 + Math.exp(-v));
    }
}
