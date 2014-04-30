package pedidos;

import java.util.UUID;

import estrutura.Endereco;

public interface IPedido {

	public abstract UUID getIdPedido();
	
	public abstract int getNumeroPacotes();

	public abstract Endereco getEndereco();

}