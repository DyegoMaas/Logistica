package componentes;

import java.awt.Button;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;

import servicos.IServico;

public class ServicoPanel extends JPanel {

	private IServico servico;
	private String tituloServico;

	public ServicoPanel(String tituloServico, IServico servico) {
		this.tituloServico = tituloServico;
		this.servico = servico;
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
				ServicoPanel.this.servico.interromperOuExecutar();
				if (ServicoPanel.this.servico.isExecutando()) {
					botaoPararContinuar.setLabel("Parar");
				} else {
					botaoPararContinuar.setLabel("Continuar");
				}
			}
		});
		textFieldIntervaloGeracaoPedidos.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				ServicoPanel.this.servico.definirIntervaloExecucao(Integer.valueOf(textFieldIntervaloGeracaoPedidos.getText()));
			}
		});
		JLabel labelTitulo = new JLabel(tituloServico);
		labelTitulo.setSize(100, 20);
		this.add(labelTitulo);
		this.add(botaoPararContinuar);
		this.add(textFieldIntervaloGeracaoPedidos);
		this.setSize(100, 60);
	}
}