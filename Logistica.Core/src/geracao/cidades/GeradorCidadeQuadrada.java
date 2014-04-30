package geracao.cidades;

import java.util.ArrayList;
import java.util.Random;

import estrutura.Cidade;
import estrutura.DirecaoAresta;
import estrutura.Garagem;
import estrutura.IRegiaoAbrangencia;
import estrutura.Imovel;
import estrutura.Interseccao;
import estrutura.LadoImovel;
import estrutura.Logradouro;
import estrutura.RegiaoAbrangencia;
import estrutura.Residencia;
import estrutura.TipoImovel;

public class GeradorCidadeQuadrada implements IGeradorCidade {
	
	private Interseccao[][] interseccoes;
	private int contadorGaragens = 0;
	ArrayList<String> logradourosAbrangidos = new ArrayList<String>();
	
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
					logradourosAbrangidos.add(logradouroY.getNome());					
					cidade.addLogradouro(logradouroY);
				}
				
				Logradouro logradouroX = null;
				Interseccao interseccaoAnteriorX = null;
				int ultimoX = x -1;
				if(ultimoX >= 0){
					interseccaoAnteriorX = interseccoes[ultimoX][y];
					
					logradouroX = criarLogradouro(x, y, novaInterseccao, interseccaoAnteriorX);
					logradourosAbrangidos.add(logradouroX.getNome());
					cidade.addLogradouro(logradouroX);
				}
				
				//quarteirão
				if(estouEmUmQuarteirao(ultimoY, ultimoX)){
					if(devoCriarUmaGaragem(x, y, tamanho)){
						int localizacaoGaragem = obterLocalizacaoGaragem();
						switch(localizacaoGaragem){
							case 0:
								criarImovel(cidade, logradouroX, novaInterseccao, TipoImovel.GARAGEM);
							case 1:
								criarImovel(cidade, logradouroY, novaInterseccao, TipoImovel.GARAGEM);
							case 2: {
								Logradouro logradouroAdjacenteY = obterLogradouroAdjacente(novaInterseccao, interseccaoAnteriorY, interseccaoAnteriorX);
								Interseccao interseccaoAnteriorXY = obterOutraInterseccao(logradouroAdjacenteY, interseccaoAnteriorY);
								criarImovel(cidade, logradouroAdjacenteY, interseccaoAnteriorXY, TipoImovel.GARAGEM);
							}
							case 3: {
								Logradouro logradouroAdjacenteX = obterLogradouroAdjacente(novaInterseccao, interseccaoAnteriorX, interseccaoAnteriorY);
								criarImovel(cidade, logradouroAdjacenteX, interseccaoAnteriorX, TipoImovel.GARAGEM);
							}
						}
					} else {
						criarImovel(cidade, logradouroX, novaInterseccao, TipoImovel.RESIDENCIA);
						criarImovel(cidade, logradouroX, novaInterseccao, TipoImovel.RESIDENCIA);
						criarImovel(cidade, logradouroY, interseccaoAnteriorY, TipoImovel.RESIDENCIA);
						criarImovel(cidade, logradouroY, interseccaoAnteriorY, TipoImovel.RESIDENCIA);
						
						Logradouro logradouroAdjacenteX = obterLogradouroAdjacente(novaInterseccao, interseccaoAnteriorX, interseccaoAnteriorY);
						criarImovel(cidade, logradouroAdjacenteX, interseccaoAnteriorX, TipoImovel.RESIDENCIA);
						criarImovel(cidade, logradouroAdjacenteX, interseccaoAnteriorX, TipoImovel.RESIDENCIA);
						
						Logradouro logradouroAdjacenteY = obterLogradouroAdjacente(novaInterseccao, interseccaoAnteriorY, interseccaoAnteriorX);
						Interseccao interseccaoAnteriorXY = obterOutraInterseccao(logradouroAdjacenteY, interseccaoAnteriorY);
						criarImovel(cidade, logradouroAdjacenteY, interseccaoAnteriorXY, TipoImovel.RESIDENCIA);
						criarImovel(cidade, logradouroAdjacenteY, interseccaoAnteriorXY, TipoImovel.RESIDENCIA);
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

	private void criarImovel(Cidade cidade, Logradouro logradouro, Interseccao interseccaoReferencia, TipoImovel tipoImovel){
		LadoImovel ladoInterno = obterLadoInterno(logradouro, interseccaoReferencia);
		
		Imovel imovel = null;
		if(tipoImovel == TipoImovel.GARAGEM){
			Garagem garagem = new Garagem(contadorGaragens++, ladoInterno, numeroCaminhoesAleatorio(), 1, novaRegiaoAbrangencia());
			cidade.addGaragem(garagem);
			imovel = garagem;
		}
		else {			
			Residencia residencia = new Residencia(ladoInterno, numeroDeResidenciaAleatorio());
			cidade.addResidencia(residencia);
			imovel = residencia;
		} 
				
		logradouro.addImovel(imovel);
	}	

	private IRegiaoAbrangencia novaRegiaoAbrangencia() {
		String[] nomesLogradouros = new String[logradourosAbrangidos.size()];
		logradourosAbrangidos.toArray(nomesLogradouros);
		logradourosAbrangidos.clear();
		
		return new RegiaoAbrangencia(nomesLogradouros);
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
//		int raiz = (int) Math.sqrt(tamanho);
//		return x % raiz == 0 || y % raiz == 0;
		int ocorrencia = tamanho / 2;
		boolean ehUltimaQuadra = (x + 1) * (y + 1) == tamanho;
		return ocorrencia == x || ocorrencia == y || ehUltimaQuadra;
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
	
	private int numeroDeResidenciaAleatorio() {
		return new Random().nextInt(3) + 1;
	}

	private String gerarNome(int x, int y) {
		return "Rua " + x + "-" + y;
	}
}
