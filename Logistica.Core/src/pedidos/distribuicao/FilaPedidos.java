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
	
	public synchronized void addPedido(IPedido novoPedido){
		pedidos.add(novoPedido);
		contadorNumeroPacotes += novoPedido.getNumeroPacotes();
		System.out.printf("pedido de %d pacotes adicionado na fila\n", novoPedido.getNumeroPacotes());

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
		return contadorNumeroPacotes >= 50;
	}
	
	private Entrega gerarEntrega(){
		List<IPedido> pedidosEntrega = new ArrayList<IPedido>();
		int contadorPacotesEntrega = 0;
		IPedido pedidoPeek = pedidos.peek();
		contadorPacotesEntrega += pedidoPeek.getNumeroPacotes();
		while(contadorPacotesEntrega < 50){
			IPedido pedidoPoll = pedidos.poll();
			pedidosEntrega.add(pedidoPoll);
			contadorNumeroPacotes -= pedidoPoll.getNumeroPacotes();
			pedidoPeek = pedidos.peek();
			contadorPacotesEntrega += pedidoPeek.getNumeroPacotes();
		}
		return new Entrega(pedidosEntrega);
	}
}