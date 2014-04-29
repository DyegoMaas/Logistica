package pedidos.distribuicao;

import java.util.List;

import pedidos.IPedido;
import estrutura.Endereco;
import estrutura.Garagem;

public class CentroDistribuicao {

	private FilaPedidos filaPedidosCentro;
	private List<Garagem> garagens;

	public CentroDistribuicao(FilaPedidos filaPedidosCentro, List<Garagem> garagens) {
		this.filaPedidosCentro = filaPedidosCentro;
		this.garagens = garagens;
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

	private void distribuir(IPedido pedido) throws Exception {
		Garagem garagem = garagemQueAtendeOEndereco(pedido.getEndereco());
		if (garagem == null)
			throw new Exception("Este centro de distribuicao nao pode efetuar a entrega do pedido " + pedido);

		garagem.entregar(pedido);
	}

	private boolean umaDasGaragensAtendeOEndereco(Endereco endereco) {
		return garagemQueAtendeOEndereco(endereco) != null;
	}

	private Garagem garagemQueAtendeOEndereco(Endereco endereco) {
		for (Garagem garagem : garagens) {
			if (garagem.ehResponsavelPorEntregasNoEndereco(endereco))
				return garagem;
		}
		return null;
	}
}
