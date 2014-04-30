package pedidos.distribuicao;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import pedidos.IPedido;
import pedidos.entregas.Entrega;
import estrutura.IFilaComPropertyChangeSupport;

public class FilaPedidos implements IFilaComPropertyChangeSupport {

	private PropertyChangeSupport changes = new PropertyChangeSupport(this);

	private final ReentrantLock lock = new ReentrantLock();
	private final Condition podeGerarUmPedido = lock.newCondition();

	private Queue<IPedido> pedidos = new LinkedList<IPedido>();
	private int contadorNumeroPacotes = 0;
	private int numeroPacotesPorEntrega;

	public FilaPedidos(int numeroPacotesPorEntrega) {
		this.numeroPacotesPorEntrega = numeroPacotesPorEntrega;
	}

	public void addPedido(IPedido novoPedido) {
		lock.lock();

		try {
			pedidos.add(novoPedido);
			changes.firePropertyChange("pedidos", null, pedidos);
			contadorNumeroPacotes += novoPedido.getNumeroPacotes();
			System.out.printf("pedido %s adicionado na fila de entrega\n", novoPedido.getIdPedido());
		} finally {
			podeGerarUmPedido.signalAll();
			lock.unlock();
		}
	}

	public Entrega obterEntrega() throws InterruptedException {
		lock.lock();

		while (!possuiNumeroMinimoPacotes())
			podeGerarUmPedido.await();

		try {
			Entrega entrega = gerarEntrega();
			System.out.printf("Gerada entrega %s\n", entrega.toString());
			return entrega;
		} finally {
			lock.unlock();
		}
	}

	private boolean possuiNumeroMinimoPacotes() {
		return contadorNumeroPacotes >= numeroPacotesPorEntrega;
	}

	private Entrega gerarEntrega() {
		List<IPedido> pedidosEntrega = new ArrayList<IPedido>();
		
		int pacotesNaEntrega = 0;
		
		while(pacotesNaEntrega < numeroPacotesPorEntrega){
			IPedido pedidoPoll = pedidos.poll();
			if(pedidoPoll != null){
				pacotesNaEntrega += pedidoPoll.getNumeroPacotes();
				pedidosEntrega.add(pedidoPoll);
			}
		}
		
		contadorNumeroPacotes -= pacotesNaEntrega;
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