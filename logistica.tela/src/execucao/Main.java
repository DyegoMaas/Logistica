package execucao;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import pedidos.distribuicao.CentroDistribuicao;
import pedidos.distribuicao.FilaPedidos;
import pedidos.entregas.GeradorEntregas;
import pedidos.geracao.GeradorPedidos;
import pedidos.recepcao.DelegadorPedidos;
import pedidos.recepcao.FilaPedidosEntrada;
import pedidos.recepcao.IRecebedorPedidos;
import pedidos.recepcao.RecebedorPedidos;
import utils.DelayHelper;
import componentes.FilaPanel;
import componentes.ServicoColorPanel;
import componentes.ServicoPanel;
import estrutura.Cidade;
import estrutura.Garagem;
import geracao.cidades.GeradorCidadeQuadrada;
import geracao.cidades.IGeradorCidade;

public class Main {
	private static final int NUMERO_PEDIDOS_POR_ENTREGA = 5;

	public static void main(String[] args) throws Exception {
		Cidade cidade = gerarCidade(25);
		FilaPedidosEntrada filaEntrada = new FilaPedidosEntrada();
		IRecebedorPedidos recebedorPedidos = new RecebedorPedidos(filaEntrada);

		List<CentroDistribuicao> centrosDistribuicao = new ArrayList<CentroDistribuicao>();
		List<GeradorEntregas> geradoresEntrega = new ArrayList<GeradorEntregas>();

		int i = 0;
		for (Garagem garagem : cidade.getGaragens()) {
			CentroDistribuicao centroDistribuicao = new CentroDistribuicao(++i, new FilaPedidos(NUMERO_PEDIDOS_POR_ENTREGA), garagem);

			centrosDistribuicao.add(centroDistribuicao);
			geradoresEntrega.add(new GeradorEntregas(centroDistribuicao));
		}

		DelegadorPedidos[] delegadores = new DelegadorPedidos[] { new DelegadorPedidos(filaEntrada, centrosDistribuicao), new DelegadorPedidos(filaEntrada, centrosDistribuicao), new DelegadorPedidos(filaEntrada, centrosDistribuicao), new DelegadorPedidos(filaEntrada, centrosDistribuicao) };

		GeradorPedidos geradorPedidos = new GeradorPedidos(recebedorPedidos, cidade);
		geradorPedidos.definirIntervaloExecucao(20);

		JFrame frame = new JFrame();
		JPanel panelGeracaoDelegacao = new JPanel();

		JPanel panelCentrosDistribuicao = new JPanel();
		panelCentrosDistribuicao.setLayout(new BoxLayout(panelCentrosDistribuicao, BoxLayout.Y_AXIS));
		JScrollPane scrollPaneCentroDistribuicao = new JScrollPane(panelCentrosDistribuicao);
		scrollPaneCentroDistribuicao.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		for (CentroDistribuicao centroDistribuicao : centrosDistribuicao) {
			panelCentrosDistribuicao.add(new FilaPanel("Fila Centro" + centroDistribuicao.getId(), centroDistribuicao.getFila()));
		}

		JPanel panelGeradoresEntrega = new JPanel();
		panelGeradoresEntrega.setLayout(new BoxLayout(panelGeradoresEntrega, BoxLayout.Y_AXIS));
		JScrollPane scrollPaneGeradoresEntrega = new JScrollPane(panelGeradoresEntrega);
		scrollPaneGeradoresEntrega.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		for (GeradorEntregas geradorEntregas : geradoresEntrega) {
			panelGeradoresEntrega.add(new ServicoColorPanel("Gerador entrega", geradorEntregas));
		}

		panelGeracaoDelegacao.setLayout(new BoxLayout(panelGeracaoDelegacao, BoxLayout.Y_AXIS));
		panelGeracaoDelegacao.setSize(500, 100);
		panelGeracaoDelegacao.add(new ServicoPanel("Gerador pedidos", geradorPedidos));
		panelGeracaoDelegacao.add(new FilaPanel("Fila pedidos entrada", filaEntrada));
		JPanel panelDelegadores = new JPanel();
		panelDelegadores.setLayout(new BoxLayout(panelDelegadores, BoxLayout.Y_AXIS));

		for (DelegadorPedidos delegadorPedidos : delegadores) {
			panelDelegadores.add(new ServicoColorPanel("Delegador", delegadorPedidos));
		}

		JScrollPane scrollPane = new JScrollPane(panelDelegadores);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		JPanel panelListaServicos = new JPanel();
		panelListaServicos.setLayout(new BoxLayout(panelListaServicos, BoxLayout.Y_AXIS));
		JPanel panelBotoesDelegadores = new JPanel();
		panelBotoesDelegadores.setLayout(new BoxLayout(panelBotoesDelegadores, BoxLayout.X_AXIS));

		JButton botaoAdicionarDelegador = new JButton("+ Delegador");
		botaoAdicionarDelegador.setSize(105, 20);
		botaoAdicionarDelegador.setPreferredSize(new Dimension(105, 20));
		JButton botaoRemoverDelegador = new JButton("- Delegador");
		botaoRemoverDelegador.setSize(105, 20);
		botaoRemoverDelegador.setPreferredSize(new Dimension(105, 20));
		panelBotoesDelegadores.add(botaoAdicionarDelegador);
		panelBotoesDelegadores.add(botaoRemoverDelegador);
		panelBotoesDelegadores.setSize(210, 20);
		panelBotoesDelegadores.setPreferredSize(new Dimension(210, 20));

		panelListaServicos.add(scrollPane);
		panelListaServicos.add(panelBotoesDelegadores);

		panelGeracaoDelegacao.add(panelListaServicos);

		JPanel panelFatorDelay = new JPanel();
		panelFatorDelay.setLayout(new GridLayout(1, 2));

		final TextField textFieldFatorDelay = new TextField();
		textFieldFatorDelay.setText(String.valueOf(DelayHelper.getFator()));
		textFieldFatorDelay.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				DelayHelper.setFator(Integer.valueOf(textFieldFatorDelay.getText()));
			}
		});
		JLabel labelFatorDelay = new JLabel("Fator de delay:");

		labelFatorDelay.setPreferredSize(new Dimension(610, 20));
		labelFatorDelay.setSize(new Dimension(610, 20));

		textFieldFatorDelay.setPreferredSize(new Dimension(610, 20));
		textFieldFatorDelay.setSize(new Dimension(610, 20));

		panelFatorDelay.add(labelFatorDelay);
		panelFatorDelay.add(textFieldFatorDelay);
		panelFatorDelay.setPreferredSize(new Dimension(610, 20));
		panelFatorDelay.setSize(new Dimension(610, 20));

		JPanel panelServicos = new JPanel();
		panelServicos.setLayout(new GridLayout(1, 3));
		panelServicos.setSize(610, 500);
		panelServicos.setPreferredSize(new Dimension(610, 500));
		panelServicos.setMinimumSize(new Dimension(600, 440));
		
		panelServicos.add(panelGeracaoDelegacao);
		panelServicos.add(scrollPaneCentroDistribuicao);
		panelServicos.add(scrollPaneGeradoresEntrega);

		frame.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		frame.add(panelFatorDelay, c);

		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		frame.add(panelServicos, c);

		//frame.setLayout(new GridLayout(2, 1));
		frame.setPreferredSize(new Dimension(610, 500));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		geradorPedidos.executar();

		for (DelegadorPedidos delegadorPedidos : delegadores) {
			delegadorPedidos.definirIntervaloExecucao(50);
			delegadorPedidos.executar();
		}

		for (GeradorEntregas geradorEntregas : geradoresEntrega) {
			geradorEntregas.definirIntervaloExecucao(100);
			geradorEntregas.executar();
		}
	}

	private static Cidade gerarCidade(int tamanho) throws Exception {
		IGeradorCidade geradorCidade = new GeradorCidadeQuadrada();
		return geradorCidade.gerar("Timbo", tamanho);
	}
}
