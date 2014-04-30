package pedidos.recepcao;

import pedidos.IPedido;

public class RecebedorPedidos implements IRecebedorPedidos{

	private FilaPedidosEntrada filaPedidos;

	public RecebedorPedidos(FilaPedidosEntrada filaPedidos){
		this.filaPedidos = filaPedidos;
	}
	
	@Override
	public void receberPedido(IPedido pedido) {
		filaPedidos.addPedido(pedido);
		System.out.printf("pedido %s recebido\n", pedido.getIdPedido());
	}

}
