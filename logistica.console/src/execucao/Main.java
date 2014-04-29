package execucao;

import estrutura.Cidade;
import estrutura.Garagem;
import geracao.cidades.GeradorCidadeQuadrada;
import geracao.cidades.IGeradorCidade;

import java.util.ArrayList;
import java.util.List;

import pedidos.distribuicao.CentroDistribuicao;
import pedidos.distribuicao.FilaPedidos;
import pedidos.geracao.GeradorPedidos;
import pedidos.recepcao.DelegadorPedidos;
import pedidos.recepcao.FilaPedidosEntrada;
import pedidos.recepcao.IRecebedorPedidos;
import pedidos.recepcao.RecebedorPedidos;

public class Main {
	public static void main(String[] args) throws Exception{
		IGeradorCidade geradorCidade = new GeradorCidadeQuadrada();
		final int tamanhoCidade = 25;
		Cidade cidade = geradorCidade.gerar("Timbo", tamanhoCidade);
			
		
		FilaPedidosEntrada filaEntrada = new FilaPedidosEntrada();
		IRecebedorPedidos recebedorPedidos = new RecebedorPedidos(filaEntrada);
		List<CentroDistribuicao> centrosDistribuicao = new ArrayList<CentroDistribuicao>();
		
		int i = 0;
		for (Garagem garagem : cidade.getGaragens()) {
			centrosDistribuicao.add(new CentroDistribuicao(++i, new FilaPedidos(), garagem));	
		}
					
		DelegadorPedidos[] delegadores = new DelegadorPedidos[]{
			new DelegadorPedidos(filaEntrada, centrosDistribuicao),
			new DelegadorPedidos(filaEntrada, centrosDistribuicao),
			new DelegadorPedidos(filaEntrada, centrosDistribuicao),
			new DelegadorPedidos(filaEntrada, centrosDistribuicao)
		};
		
		for (DelegadorPedidos delegadorPedidos : delegadores) {
			delegadorPedidos.start();
		}
			
		
		GeradorPedidos geradorPedidos = new GeradorPedidos(recebedorPedidos, cidade);		
		geradorPedidos.start();
	}
}