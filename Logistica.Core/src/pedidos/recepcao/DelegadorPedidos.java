package pedidos.recepcao;

import java.util.List;

import pedidos.IPedido;
import pedidos.distribuicao.CentroDistribuicao;
import pedidos.distribuicao.FilaPedidos;

public class DelegadorPedidos extends Thread implements IDelegadorPedidos{

	private List<CentroDistribuicao> centrosDistribuicao;
	private FilaPedidosEntrada filaPedidosEntrada;
	private boolean continuarDelegacao = true;

	public DelegadorPedidos(FilaPedidosEntrada filaPedidosEntrada, List<CentroDistribuicao> centrosDistribuicao){
		this.filaPedidosEntrada = filaPedidosEntrada;
		this.centrosDistribuicao = centrosDistribuicao;		
	}
	
	@Override
	public void run(){
		while(continuarDelegacao){
			try {
				IPedido pedido = filaPedidosEntrada.obterPedido();
				
				for (CentroDistribuicao centroDistribuicao : centrosDistribuicao) {
					if(centroDistribuicao.tentarAdicionar(pedido)){
						break;
					}
				}
			} catch (InterruptedException e) {
				// TODO logar erro
				e.printStackTrace();
			}
			
			aguardar(50);
		}
	}
	
	public void interromperDelegacao(){
		continuarDelegacao = false;
	}

	private void aguardar(int duracao) {
		try {
			Thread.sleep(duracao);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
