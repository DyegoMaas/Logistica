package pedidos.distribuicao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import estrutura.IFila;
import pedidos.IPedido;
import pedidos.entregas.Entrega;

public class FilaPedidos implements IFila{

	private final ReentrantLock lock = new ReentrantLock();
	private final Condition podeGerarUmPedido = lock.newCondition();
	
	private Queue<IPedido> pedidos = new LinkedList<IPedido>();
	private int contadorNumeroPacotes = 0;
	private int numeroPedidosPorEntrega;
	
	public FilaPedidos(int numeroPedidosPorEntrega){
		this.numeroPedidosPorEntrega = numeroPedidosPorEntrega;		
	}
	
	public void addPedido(IPedido novoPedido){
		lock.lock();		

		try{
			pedidos.add(novoPedido);
			contadorNumeroPacotes += novoPedido.getNumeroPacotes();
			System.out.printf("pedido %s adicionado na fila de entrega\n", novoPedido.getIdPedido());	
		} finally {
			podeGerarUmPedido.signalAll();
			lock.unlock();	
		}		
	}
	
	public Entrega obterEntrega() throws InterruptedException {
		lock.lock();
		
		while(!possuiNumeroMinimoPedidos())
			podeGerarUmPedido.await();
		
		try{
			Entrega entrega = gerarEntrega();
			System.out.printf("Gerada entrega %s\n", entrega.toString());
			return entrega;
		} finally {
			lock.unlock();				
		}			
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

	@Override
	public synchronized int getNumeroItens() {
		return pedidos.size();
	}
}