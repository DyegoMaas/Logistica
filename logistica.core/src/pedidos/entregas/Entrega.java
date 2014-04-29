package pedidos.entregas;

import java.util.List;

import pedidos.IPedido;

public class Entrega {

	private List<IPedido> pedidos;

	public Entrega(List<IPedido> pedidos) {
		this.pedidos = pedidos;
	}

	public List<IPedido> getPedidos() {
		return pedidos;
	}
}