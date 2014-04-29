package pedidos.distribuicao;

import pedidos.IPedido;
import pedidos.entregas.Entrega;
import estrutura.Endereco;
import estrutura.Garagem;

public class CentroDistribuicao {

	private FilaPedidos filaPedidosCentro;
	private Garagem garagem;
	//private List<Garagem> garagens;

	//public CentroDistribuicao(FilaPedidos filaPedidosCentro, List<Garagem> garagens) {
	public CentroDistribuicao(FilaPedidos filaPedidosCentro, Garagem garagem) {
		this.filaPedidosCentro = filaPedidosCentro;
		this.garagem = garagem;
		//this.garagens = garagens;
	}

	public synchronized boolean tentarAdicionar(IPedido pedido) {
		if (souResponsavel(pedido.getEndereco())) {
			filaPedidosCentro.addPedido(pedido);
			return true;
		}
		return false;
	}

	private boolean souResponsavel(Endereco endereco) {
		return umaDasGaragensAtendeOEndereco(endereco);
	}

	private synchronized void distribuir(Entrega entrega) throws Exception {
		garagem.entregar(entrega);
	}

	private boolean umaDasGaragensAtendeOEndereco(Endereco endereco) {
		return garagemQueAtendeOEndereco(endereco) != null;
	}

	public void fazerEntrega() throws Exception{
		distribuir(filaPedidosCentro.obterEntrega());
	}

	private Garagem garagemQueAtendeOEndereco(Endereco endereco) {
		if (garagem.ehResponsavelPorEntregasNoEndereco(endereco))
			return garagem;
		return null;
//		for (Garagem garagem : garagens) {
//			if (garagem.ehResponsavelPorEntregasNoEndereco(endereco))
//				return garagem;
//		}
//		return null;
	}
}
