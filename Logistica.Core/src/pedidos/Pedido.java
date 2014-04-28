package pedidos;

import estrutura.Endereco;

public class Pedido implements IPedido {
	private int numeroPacotes;
	private Endereco endereco;

	public Pedido(int numeroPacotes, Endereco endereco){
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
	
}
