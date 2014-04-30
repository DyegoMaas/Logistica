package execucao;

import estrutura.Cidade;
import estrutura.Garagem;
import geracao.cidades.GeradorCidadeQuadrada;
import geracao.cidades.IGeradorCidade;

import java.util.ArrayList;
import java.util.List;

import pedidos.distribuicao.CentroDistribuicao;
import pedidos.distribuicao.FilaPedidos;
import pedidos.entregas.GeradorEntregas;
import pedidos.geracao.GeradorPedidos;
import pedidos.recepcao.DelegadorPedidos;
import pedidos.recepcao.FilaPedidosEntrada;
import pedidos.recepcao.IRecebedorPedidos;
import pedidos.recepcao.RecebedorPedidos;

public class Main {
	private static final int NUMERO_PEDIDOS_POR_ENTREGA = 5;
	
	public static void main(String[] args) throws Exception{
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
					
		DelegadorPedidos[] delegadores = new DelegadorPedidos[]{
			new DelegadorPedidos(filaEntrada, centrosDistribuicao),
			new DelegadorPedidos(filaEntrada, centrosDistribuicao),
			new DelegadorPedidos(filaEntrada, centrosDistribuicao),
			new DelegadorPedidos(filaEntrada, centrosDistribuicao)
		};		
		
		GeradorPedidos geradorPedidos = new GeradorPedidos(recebedorPedidos, cidade);	
		geradorPedidos.definirIntervaloExecucao(20);
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