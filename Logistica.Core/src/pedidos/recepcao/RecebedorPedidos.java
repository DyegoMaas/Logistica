package pedidos.recepcao;

import pedidos.IPedido;
import pedidos.distribuicao.FilaPedidos;

public class RecebedorPedidos implements IRecebedorPedidos{

	private FilaPedidos filaPedidos;

	public RecebedorPedidos(FilaPedidos filaPedidos){
		this.filaPedidos = filaPedidos;
	}
	
	@Override
	public void receberPedido(IPedido pedido) {
		filaPedidos.addPedido(pedido);
	}

}
