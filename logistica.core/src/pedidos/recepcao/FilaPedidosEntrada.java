package pedidos.recepcao;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.Queue;

import pedidos.IPedido;
import estrutura.IFilaComPropertyChangeSupport;

public class FilaPedidosEntrada implements IFilaComPropertyChangeSupport {

	private PropertyChangeSupport changes = new PropertyChangeSupport(this);
	private Queue<IPedido> pedidos = new LinkedList<IPedido>();

	public synchronized void addPedido(IPedido novoPedido) {
		pedidos.add(novoPedido);
		changes.firePropertyChange("pedidos", null, pedidos);

		System.out.printf("pedido %s adicionado na fila de entrada\n", novoPedido.getIdPedido());

		notifyAll();
	}

	public synchronized IPedido obterPedido() throws InterruptedException {
		while (pedidos.size() == 0)
			wait();

		IPedido pedido = pedidos.poll();
		changes.firePropertyChange("pedidos", null, pedidos);
		System.out.printf("pedido %s obtido da fila de entrada\n", pedido.getIdPedido());

		return pedido;
	}

	@Override
	public synchronized int getNumeroItens() {
		return pedidos.size();
	}

	public void addPropertyChangeListener(PropertyChangeListener l) {
		changes.addPropertyChangeListener(l);
	}

	public void removePropertyChangeListener(PropertyChangeListener l) {
		changes.removePropertyChangeListener(l);
	}
}