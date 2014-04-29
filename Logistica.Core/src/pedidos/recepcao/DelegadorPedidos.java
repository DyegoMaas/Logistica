package pedidos.recepcao;

import java.util.List;

import pedidos.IPedido;
import pedidos.distribuicao.CentroDistribuicao;

public class DelegadorPedidos implements IDelegadorPedidos{

	private List<CentroDistribuicao> centrosDistribuicao;

	public DelegadorPedidos(List<CentroDistribuicao> centrosDistribuicao){
		this.centrosDistribuicao = centrosDistribuicao;		
	}
	
	@Override
	public void delegar(IPedido pedido) {		
		for (CentroDistribuicao centroDistribuicao : centrosDistribuicao) {
			if(centroDistribuicao.ehResponsavel(pedido.getEndereco())){
				try {
					centroDistribuicao.distribuir(pedido);
				} catch (Exception e) {
					// TODO logar erro
					e.printStackTrace();
				}
			}
		}	
	}

}
