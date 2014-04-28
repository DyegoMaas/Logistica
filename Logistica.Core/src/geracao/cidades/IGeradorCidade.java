package geracao.cidades;

import estrutura.Cidade;

public interface IGeradorCidade {

	Cidade gerar(String nome, int tamanho) throws Exception;

}