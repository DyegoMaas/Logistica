package pedidos.distribuicao;

import pedidos.IPedido;
import pedidos.entregas.Entrega;
import estrutura.Endereco;
import estrutura.Garagem;

public class CentroDistribuicao {

	private FilaPedidos filaPedidosCentro;
	private Garagem garagem;
	private int idCentroDistribuicao;

	public CentroDistribuicao(int idCentroDistribuicao, FilaPedidos filaPedidosCentro, Garagem garagem) {
		this.idCentroDistribuicao = idCentroDistribuicao;
		this.filaPedidosCentro = filaPedidosCentro;
		this.garagem = garagem;
	}

	public synchronized boolean tentarAdicionar(IPedido pedido) {
		if (souResponsavel(pedido.getEndereco())) {
			filaPedidosCentro.addPedido(pedido);
			return true;
		}
		return false;
	}

	private boolean souResponsavel(Endereco endereco) {
		return garagem.ehResponsavelPorEntregasNoEndereco(endereco);
	}

	private synchronized void distribuir(Entrega entrega) throws Exception {
		garagem.entregar(entrega);
	}

	public void fazerEntrega() throws Exception{
		distribuir(filaPedidosCentro.obterEntrega());
	}
	
	@Override
	public String toString(){
		return String.valueOf(idCentroDistribuicao);
	}

	public int getId() {
		return idCentroDistribuicao;
	}
}
