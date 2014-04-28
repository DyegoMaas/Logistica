package geracao.cidades;

import java.util.Random;

import estrutura.Cidade;
import estrutura.DirecaoAresta;
import estrutura.Garagem;
import estrutura.Imovel;
import estrutura.Interseccao;
import estrutura.LadoImovel;
import estrutura.Logradouro;
import estrutura.Residencia;
import estrutura.TipoImovel;

public class GeradorCidadeQuadrada implements IGeradorCidade {
	
	private Interseccao[][] interseccoes;
	
	@Override
	public Cidade gerar(String nome, int tamanho) throws Exception{
		Cidade cidade = new Cidade(nome);

		interseccoes = new Interseccao[tamanho][tamanho];
		
		for (int x = 0; x < tamanho; x++) {
			for (int y = 0; y < tamanho; y++) {
				Interseccao novaInterseccao = criarInterseccao(x, y);
				
				Logradouro logradouroY = null;
				Interseccao interseccaoAnteriorY = null;
				int ultimoY = y -1;
				if(ultimoY >= 0){
					interseccaoAnteriorY = interseccoes[x][ultimoY];					
					
					logradouroY = criarLogradouro(x, y, novaInterseccao, interseccaoAnteriorY);
					cidade.addLogradouro(logradouroY);
				}
				
				Logradouro logradouroX = null;
				Interseccao interseccaoAnteriorX = null;
				int ultimoX = x -1;
				if(ultimoX >= 0){
					interseccaoAnteriorX = interseccoes[ultimoX][y];
					logradouroX = criarLogradouro(x, y, novaInterseccao, interseccaoAnteriorX);
					cidade.addLogradouro(logradouroX);
				}
				
				//quarteirão
				if(estouEmUmQuarteirao(ultimoY, ultimoX)){
					if(devoCriarUmaGaragem(x, y,tamanho)){
						int localizacaoGaragem = obterLocalizacaoGaragem();
						switch(localizacaoGaragem){
							case 0:
								criarImovel(logradouroX, novaInterseccao, TipoImovel.GARAGEM);
							case 1:
								criarImovel(logradouroY, novaInterseccao, TipoImovel.GARAGEM);
							case 2: {
								Logradouro logradouroAdjacenteY = obterLogradouroAdjacente(novaInterseccao, interseccaoAnteriorY, interseccaoAnteriorX);
								Interseccao interseccaoAnteriorXY = obterOutraInterseccao(logradouroAdjacenteY, interseccaoAnteriorY);
								criarImovel(logradouroAdjacenteY, interseccaoAnteriorXY, TipoImovel.GARAGEM);
							}
							case 3: {
								Logradouro logradouroAdjacenteX = obterLogradouroAdjacente(novaInterseccao, interseccaoAnteriorX, interseccaoAnteriorY);
								criarImovel(logradouroAdjacenteX, interseccaoAnteriorX, TipoImovel.GARAGEM);
							}
						}
					} else {
						criarImovel(logradouroX, novaInterseccao, TipoImovel.RESIDENCIA);
						criarImovel(logradouroX, novaInterseccao, TipoImovel.RESIDENCIA);
						criarImovel(logradouroY, interseccaoAnteriorY, TipoImovel.RESIDENCIA);
						criarImovel(logradouroY, interseccaoAnteriorY, TipoImovel.RESIDENCIA);
						
						Logradouro logradouroAdjacenteX = obterLogradouroAdjacente(novaInterseccao, interseccaoAnteriorX, interseccaoAnteriorY);
						criarImovel(logradouroAdjacenteX, interseccaoAnteriorX, TipoImovel.RESIDENCIA);
						criarImovel(logradouroAdjacenteX, interseccaoAnteriorX, TipoImovel.RESIDENCIA);
						
						Logradouro logradouroAdjacenteY = obterLogradouroAdjacente(novaInterseccao, interseccaoAnteriorY, interseccaoAnteriorX);
						Interseccao interseccaoAnteriorXY = obterOutraInterseccao(logradouroAdjacenteY, interseccaoAnteriorY);
						criarImovel(logradouroAdjacenteY, interseccaoAnteriorXY, TipoImovel.RESIDENCIA);
						criarImovel(logradouroAdjacenteY, interseccaoAnteriorXY, TipoImovel.RESIDENCIA);
					}
				}
			}
		}

		return cidade;
	}

	private Interseccao obterOutraInterseccao(Logradouro logradouro, Interseccao interseccao) {
		if(interseccao == logradouro.getInterseccaoA())
			return logradouro.getInterseccaoB();
		return logradouro.getInterseccaoA();
	}

	private void criarImovel(Logradouro logradouro, Interseccao interseccaoReferencia, TipoImovel tipoImovel){
		LadoImovel ladoInterno = obterLadoInterno(logradouro, interseccaoReferencia);
		Imovel imovel = tipoImovel == TipoImovel.GARAGEM 
				? new Garagem(ladoInterno, numeroCaminhoesAleatorio()) 
				: new Residencia(ladoInterno); 
				
		logradouro.addImovel(imovel);
	}	

	private LadoImovel obterLadoInterno(Logradouro logradouro, Interseccao interseccaoReferencia) {
		if(logradouro.getInterseccaoA() == interseccaoReferencia)
			return LadoImovel.DIREITA;
		return LadoImovel.ESQUERDA;
	}
	
	private int obterLocalizacaoGaragem() {
		Random random = new Random();
		return random.nextInt(4);
	}

	private boolean devoCriarUmaGaragem(int x, int y, int tamanho) {
		int raiz = (int) Math.sqrt(tamanho);
		return x % raiz == 0 || y % raiz == 0;
	}

	private boolean estouEmUmQuarteirao(int ultimoY, int ultimoX) {
		return ultimoX >= 0 && ultimoY >= 0;
	}
	
	private Logradouro obterLogradouroAdjacente(Interseccao interseccaoAtual, Interseccao umaInterseccao, Interseccao outraInterseccao) throws Exception {
		for (Logradouro umLogradouro : umaInterseccao.getLogradouros()) {
			for (Logradouro outroLogradouro : outraInterseccao.getLogradouros()) {
				if(quandoLogradouroNaoForRelacionadoInterseccaoAtual(interseccaoAtual, umLogradouro) && quandoLogradouroNaoForRelacionadoInterseccaoAtual(interseccaoAtual, outroLogradouro)){
					temInterseccaoEmComum(umLogradouro, outroLogradouro);
					return umLogradouro;
				}
			}
		}
		throw new Exception("Não existe Interseccao relacionada entre " + umaInterseccao.toString() + " e " + outraInterseccao.toString());
	}

	private boolean temInterseccaoEmComum(Logradouro umLogradouro, Logradouro outroLogradouro) {
		if(umLogradouro.getInterseccaoA() == outroLogradouro.getInterseccaoA())
			return true;
		if(umLogradouro.getInterseccaoB() == outroLogradouro.getInterseccaoB())
			return true;
		return false;
	}

	public boolean quandoLogradouroNaoForRelacionadoInterseccaoAtual(Interseccao interseccaoAtual, Logradouro logradouro){
		return logradouro.getInterseccaoA() != interseccaoAtual && logradouro.getInterseccaoB() != interseccaoAtual;
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
	
	private int numeroCaminhoesAleatorio() {
		return new Random().nextInt(4);
	}

	private String gerarNome(int x, int y) {
		return "Rua " + x + " - " + y;
	}
}
