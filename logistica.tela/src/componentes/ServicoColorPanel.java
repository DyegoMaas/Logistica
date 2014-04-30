package componentes;

import java.awt.Button;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import servicos.IServico;
import servicos.IServicoComPropertyChangeSupport;
import servicos.StatusExecucaoServico;

public class ServicoColorPanel extends JPanel implements PropertyChangeListener {

	private IServico servico;
	private String tituloServico;
	private JLabel labelTitulo;

	public ServicoColorPanel(String tituloServico, IServicoComPropertyChangeSupport servico) {
		this.tituloServico = tituloServico;
		this.servico = servico;
		servico.addPropertyChangeListener(this);
		this.setLayout(new GridLayout(3, 1));
		String textoBotao = "Parar";
		if (!servico.isExecutando())
			textoBotao = "Iniciar";
		final Button botaoPararContinuar = new Button(textoBotao);
		botaoPararContinuar.setSize(100, 20);
		final TextField textFieldIntervaloGeracaoPedidos = new TextField();
		textFieldIntervaloGeracaoPedidos.setText(String.valueOf(servico.getIntervaloExecucao()));
		textFieldIntervaloGeracaoPedidos.setSize(100, 20);
		botaoPararContinuar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					ServicoColorPanel.this.servico.interromperOuExecutar();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (ServicoColorPanel.this.servico.isExecutando()) {
					botaoPararContinuar.setLabel("Parar");
				} else {
					botaoPararContinuar.setLabel("Continuar");
				}
			}
		});
		textFieldIntervaloGeracaoPedidos.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ServicoColorPanel.this.servico.definirIntervaloExecucao(Integer.valueOf(textFieldIntervaloGeracaoPedidos.getText()));
			}
		});
		labelTitulo = new JLabel(tituloServico);
		labelTitulo.setSize(100, 20);
		this.add(labelTitulo);
		this.add(botaoPararContinuar);
		this.add(textFieldIntervaloGeracaoPedidos);
		this.setSize(100, 60);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		StatusExecucaoServico statusServico = servico.getStatusExecucao();
		if (statusServico != null && statusServico == StatusExecucaoServico.EXECUTANDO) {
			labelTitulo.setBackground(Color.GREEN);
			this.setBackground(Color.GREEN);
		} else {
			labelTitulo.setBackground(Color.BLUE);
			this.setBackground(Color.BLUE);
		}
	}
}