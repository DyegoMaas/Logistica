package geracao.cidades;

import estrutura.Cidade;

public interface IGeradorCidade {

	public abstract Cidade gerar(int tamanho) throws Exception;

}