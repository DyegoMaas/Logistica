package pedidos.distribuicao;

import java.util.List;

import pedidos.IPedido;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import estrutura.Endereco;
import estrutura.Garagem;

public class CentroDistribuicao {
	
	private FilaPedidos filaPedidos;
	private List<Garagem> garagens;

	public CentroDistribuicao(FilaPedidos filaPedidos, List<Garagem> garagens){
		this.filaPedidos = filaPedidos;
		this.garagens = garagens; 
	}
	
	public boolean ehResponsavel(Endereco endereco){
		return false;
	}
	
	public void distribuir(IPedido pedido){
		throw new NotImplementedException();
	}
}
