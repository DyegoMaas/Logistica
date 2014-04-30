package execucao;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
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
import componentes.FilaPanel;
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

		JPanel listaServicos = new JPanel();
		listaServicos.setLayout(new GridLayout(2, 1));
		JPanel panelBotoesDelegadores = new JPanel();

		JFrame frame = new JFrame();
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setSize(500, 100);
		frame.add(panel);
		panel.add(new ServicoPanel("Gerador pedidos", geradorPedidos));
		panel.add(new FilaPanel("Fila pedidos entrada", filaEntrada));
		JPanel panelDelegadores = new JPanel();
		panelDelegadores.setLayout(new BoxLayout(panelDelegadores, BoxLayout.Y_AXIS));

		for (DelegadorPedidos delegadorPedidos : delegadores) {
			panelDelegadores.add(new ServicoPanel("Delegador", delegadorPedidos));
		}

		JScrollPane scrollPane = new JScrollPane(panelDelegadores);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		panel.add(scrollPane);

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
