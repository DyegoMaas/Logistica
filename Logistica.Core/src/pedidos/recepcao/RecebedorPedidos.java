package pedidos.recepcao;

import pedidos.IPedido;

public class RecebedorPedidos implements IRecebedorPedidos{

	private IDelegadorPedidos delegadorPedidos;

	public RecebedorPedidos(IDelegadorPedidos delegadorPedidos){
		this.delegadorPedidos = delegadorPedidos;		
	}
	
	@Override
	public void receberPedido(IPedido pedido) {
		delegadorPedidos.delegar(pedido);
	}

}
