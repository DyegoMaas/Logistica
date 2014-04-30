package pedidos.distribuicao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import pedidos.IPedido;
import pedidos.entregas.Entrega;

public class FilaPedidos {

	private Queue<IPedido> pedidos = new LinkedList<IPedido>();
	private int contadorNumeroPacotes = 0;
	private int numeroPedidosPorEntrega;
	
	public FilaPedidos(int numeroPedidosPorEntrega){
		this.numeroPedidosPorEntrega = numeroPedidosPorEntrega;		
	}
	
	public synchronized void addPedido(IPedido novoPedido){
		pedidos.add(novoPedido);
		contadorNumeroPacotes += novoPedido.getNumeroPacotes();
		System.out.printf("pedido %s adicionado na fila de entrega\n", novoPedido.getIdPedido());

		notifyAll();
	}
	
	public synchronized Entrega obterEntrega() throws InterruptedException {
		while(!possuiNumeroMinimoPedidos())
			wait();
		
		Entrega entrega = gerarEntrega();
		System.out.printf("Gerada entrega %s\n", entrega.toString());

		return entrega;
	}

	private boolean possuiNumeroMinimoPedidos() {
		return contadorNumeroPacotes >= numeroPedidosPorEntrega;
	}
	
	private Entrega gerarEntrega(){
		List<IPedido> pedidosEntrega = new ArrayList<IPedido>();
		int contadorPacotesEntrega = 0;
		IPedido pedidoPeek = pedidos.peek();
		contadorPacotesEntrega += pedidoPeek.getNumeroPacotes();
		while(contadorPacotesEntrega < numeroPedidosPorEntrega){
			IPedido pedidoPoll = pedidos.poll();
			pedidosEntrega.add(pedidoPoll);
			contadorNumeroPacotes -= pedidoPoll.getNumeroPacotes();
			pedidoPeek = pedidos.peek();
			contadorPacotesEntrega += pedidoPeek.getNumeroPacotes();
		}
		return new Entrega(pedidosEntrega);
	}
}