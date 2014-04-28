package cidades;

import java.util.UUID;

import estrutura.Cidade;

public interface IRepositorioCidade {

	Cidade obterCidade(UUID id);
	void salvarCidade(Cidade cidade) throws Exception;
}
