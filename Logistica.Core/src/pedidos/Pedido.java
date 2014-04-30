package pedidos;

import java.util.UUID;

import estrutura.Endereco;

public class Pedido implements IPedido {
	private UUID idPedido;
	private int numeroPacotes;
	private Endereco endereco;	

	public Pedido(int numeroPacotes, Endereco endereco){
		this.idPedido = UUID.randomUUID();
		
		this.numeroPacotes = numeroPacotes;
		this.endereco = endereco;
	}

	@Override
	public int getNumeroPacotes() {
		return numeroPacotes;
	}

	@Override
	public Endereco getEndereco() {
		return endereco;
	}

	public UUID getIdPedido() {
		return idPedido;
	}	
}
