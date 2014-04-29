package pedidos.recepcao;

import pedidos.IPedido;
import pedidos.distribuicao.FilaPedidos;

public class RecebedorPedidos implements IRecebedorPedidos{

	private FilaPedidosEntrada filaPedidos;

	public RecebedorPedidos(FilaPedidosEntrada filaPedidos){
		this.filaPedidos = filaPedidos;
	}
	
	@Override
	public void receberPedido(IPedido pedido) {
		filaPedidos.addPedido(pedido);
		System.out.printf("pedido %d recebido\n", pedido.getNumeroPacotes());
	}

}
