package geradorCidades;

import java.util.Random;

import estrutura.Cidade;
import estrutura.DirecaoAresta;
import estrutura.Interseccao;
import estrutura.Logradouro;

public class GeradorCidadeQuadrada implements IGeradorCidade {
	
	private Interseccao[][] interseccoes;
	
	@Override
	public Cidade gerar(int tamanho){
		Cidade cidade = new Cidade();
		
		interseccoes = new Interseccao[tamanho][tamanho];		
		
		for (int x = 0; x < tamanho; x++) {			
			for (int y = 0; y < tamanho; y++) {				
				Interseccao novaInterseccao = criarInterseccao(x, y);
								
				int ultimoY = y -1;
				if(ultimoY >= 0){
					Interseccao interseccaoAnterior = interseccoes[x][ultimoY];					
					
					Logradouro logradouro = criarLogradouro(x, y, novaInterseccao, interseccaoAnterior);
					cidade.addLogradouro(logradouro);
				}
				
				int ultimoX = x -1;
				if(ultimoX >= 0){
					Interseccao interseccaoAnterior = interseccoes[ultimoX][y];
					
					Logradouro logradouro = criarLogradouro(x, y, novaInterseccao, interseccaoAnterior);
					cidade.addLogradouro(logradouro);
				}				
			}			
		}
		
		return cidade;
	}

	private Interseccao criarInterseccao(int x, int y) {
		Interseccao interseccao = new Interseccao(x, y);
		this.interseccoes[x][y] = interseccao;
		
		return interseccao;
	}

	private Logradouro criarLogradouro(int x, int y, Interseccao novaInterseccao, Interseccao interseccaoAnterior) {
		DirecaoAresta direcao = direcaoAleatoria();
		if(direcao == DirecaoAresta.UNIDIRECIONAL && sentidoAleatorio()){
			//swap
			Interseccao temp = novaInterseccao;
			novaInterseccao = interseccaoAnterior;
			interseccaoAnterior = temp;
		}
		
		Logradouro logradouro = new Logradouro(novaInterseccao, interseccaoAnterior, direcao, gerarNome(x, y));
		return logradouro;
	}
	
	private boolean sentidoAleatorio() {
		Random random = new Random();
		return random.nextBoolean();
	}

	private DirecaoAresta direcaoAleatoria() {
		Random random = new Random();

		if(random.nextBoolean())
			return DirecaoAresta.BIDIRECIONAL;
		
		return DirecaoAresta.UNIDIRECIONAL;
	}

	private String gerarNome(int x, int y) {
		return "Rua " + x + " - " + y;
	}
}
