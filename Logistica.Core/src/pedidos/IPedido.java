package pedidos;

import estrutura.Endereco;

public interface IPedido {

	public abstract int getNumeroPacotes();

	public abstract Endereco getEndereco();

}