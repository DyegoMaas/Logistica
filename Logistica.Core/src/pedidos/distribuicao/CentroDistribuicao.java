package pedidos.distribuicao;

import java.util.List;

import javax.activity.InvalidActivityException;

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
		return umaDasGaragensAtendeOEndereco(endereco);
	}

	public void distribuir(IPedido pedido) throws Exception{
		Garagem garagem = garagemQueAtendeOEndereco(pedido.getEndereco());
		if(garagem == null)
			throw new Exception("Este centro de distribuicao nao pode efetuar a entrega do pedido " + pedido);
		
		garagem.entregar(pedido);
	}
	
	private boolean umaDasGaragensAtendeOEndereco(Endereco endereco) {
		return garagemQueAtendeOEndereco(endereco) != null;
	}
	
	private Garagem garagemQueAtendeOEndereco(Endereco endereco) {
		for (Garagem garagem : garagens) {
			if(garagem.ehResponsavelPorEntregasNoEndereco(endereco))
				return garagem;
		}
		return null;
	}
}
