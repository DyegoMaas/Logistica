package pedidos.recepcao;

import java.util.List;

import pedidos.IPedido;
import pedidos.distribuicao.CentroDistribuicao;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DelegadorPedidos implements IDelegadorPedidos{

	private List<CentroDistribuicao> centrosDistribuicao;

	public DelegadorPedidos(List<CentroDistribuicao> centrosDistribuicao){
		this.centrosDistribuicao = centrosDistribuicao;		
	}
	
	@Override
	public void delegar(IPedido pedido) {
		
		for (CentroDistribuicao centroDistribuicao : centrosDistribuicao) {
			if(centroDistribuicao.ehResponsavel(pedido.getEndereco())){
				centroDistribuicao.distribuir(pedido);
			}
		}
		throw new NotImplementedException(); 		
	}

}
