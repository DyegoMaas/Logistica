package componentes;

import java.awt.GridLayout;
import java.awt.Label;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;

import estrutura.IFila;
import estrutura.IFilaComPropertyChangeSupport;

public class FilaPanel extends JPanel implements PropertyChangeListener {

	private IFila fila;
	private Label labelQuantidadePedidos = null;
	private String tituloFila;

	public FilaPanel(String tituloFila, IFilaComPropertyChangeSupport fila){
		this.tituloFila = tituloFila;
		this.fila = fila;
		fila.addPropertyChangeListener(this);
		this.setLayout(new GridLayout(2, 1));
		Label labelDescricaoQuantidadePedidos = new Label("Quantidade de pedidos:");
		labelDescricaoQuantidadePedidos.setSize(100, 20);
		labelQuantidadePedidos = new Label();
		labelQuantidadePedidos.setSize(100, 20);
		this.add(labelDescricaoQuantidadePedidos);
		this.add(labelQuantidadePedidos);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		labelQuantidadePedidos.setText(String.valueOf(fila.getNumeroItens()));
	}
}