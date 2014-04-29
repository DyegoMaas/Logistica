package pedidos.distribuicao;

import java.util.LinkedList;
import java.util.Queue;

import pedidos.IPedido;

public class FilaPedidos {

	private Queue<IPedido> pedidos = new LinkedList<IPedido>();
	
	public synchronized void addPedido(IPedido novoPedido){
		pedidos.add(novoPedido);
		System.out.printf("adicionado pedido de %d pacotes", novoPedido.getNumeroPacotes());

		notifyAll();
	}
	
	public synchronized IPedido obterPedido() throws InterruptedException {
		while(pedidos.size() == 0)
			wait();
		
		IPedido pedido = pedidos.poll();
		System.out.printf("consumido pedido de %d pacotes", pedido.getNumeroPacotes());
		
		return pedido;
	}
}