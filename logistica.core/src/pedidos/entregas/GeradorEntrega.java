package pedidos.entregas;

import java.util.List;

import estrutura.Garagem;
import pedidos.distribuicao.FilaPedidos;

public class GeradorEntrega extends Thread {

	private FilaPedidos filaPedidos;
	private List<Garagem> garagens;

	public GeradorEntrega(FilaPedidos filaPedidos, List<Garagem> garagens){
		this.filaPedidos = filaPedidos;
		this.garagens = garagens;
	}

	@Override
	public void run() {
		
	}
}