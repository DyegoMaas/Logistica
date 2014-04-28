package cidades;

import java.util.UUID;

import estrutura.Cidade;

public class RepositorioCidadeJson implements IRepositorioCidade {

	@Override
	public Cidade obterCidade(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void salvarCidade(Cidade cidade) throws Exception {
		if(cidade.getId() == null)
			throw new Exception("Propriedade id não especificada.");
		
	}
}