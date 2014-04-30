package pedidos.distribuicao;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import estrutura.IFila;
import estrutura.IFilaComPropertyChangeSupport;
import pedidos.IPedido;
import pedidos.entregas.Entrega;

public class FilaPedidos implements IFilaComPropertyChangeSupport {

	private PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private final ReentrantLock lock = new ReentrantLock();
	private final Condition podeGerarUmPedido = lock.newCondition();

	private Queue<IPedido> pedidos = new LinkedList<IPedido>();
	private int contadorNumeroPedidos = 0;
	private int numeroPedidosPorEntrega;

	public FilaPedidos(int numeroPedidosPorEntrega) {
		this.numeroPedidosPorEntrega = numeroPedidosPorEntrega;
	}

	public void addPedido(IPedido novoPedido) {
		lock.lock();

		try {
			pedidos.add(novoPedido);
			changes.firePropertyChange("pedidos", null, pedidos);
			contadorNumeroPedidos++;
			System.out.printf("pedido %s adicionado na fila de entrega\n", novoPedido.getIdPedido());
		} finally {
			podeGerarUmPedido.signalAll();
			lock.unlock();
		}
	}

	public Entrega obterEntrega() throws InterruptedException {
		lock.lock();

		while (!possuiNumeroMinimoPedidos())
			podeGerarUmPedido.await();

		try {
			Entrega entrega = gerarEntrega();
			System.out.printf("Gerada entrega %s\n", entrega.toString());
			return entrega;
		} finally {
			lock.unlock();
		}
	}

	private boolean possuiNumeroMinimoPedidos() {
		return contadorNumeroPedidos >= numeroPedidosPorEntrega;
	}

	private Entrega gerarEntrega() {
		List<IPedido> pedidosEntrega = new ArrayList<IPedido>();
		
		for (int i = 0; i < numeroPedidosPorEntrega; i++) {
			IPedido pedidoPoll = pedidos.poll();
			if(pedidoPoll != null)
				pedidosEntrega.add(pedidoPoll);
		}
		contadorNumeroPedidos -= numeroPedidosPorEntrega;
		return new Entrega(pedidosEntrega);
	}

	@Override
	public synchronized int getNumeroItens() {
		return pedidos.size();
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}
}