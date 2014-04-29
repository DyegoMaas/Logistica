package pedidos.recepcao;

import java.util.List;

import pedidos.IPedido;
import pedidos.distribuicao.CentroDistribuicao;
import servicos.IServico;
import utils.DelayHelper;

public class DelegadorPedidos extends Thread implements IDelegadorPedidos, IServico{

	private List<CentroDistribuicao> centrosDistribuicao;
	private FilaPedidosEntrada filaPedidosEntrada;
	private boolean continuarDelegacao = true;
	private int intervaloExecucao;

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
						System.out.printf("pedido %d delegado para o centro de distribuicao %d\n", pedido.getNumeroPacotes(), centroDistribuicao.getId());
						break;
					}
				}
			} catch (InterruptedException e) {
				// TODO logar erro
				e.printStackTrace();
			}
			
			DelayHelper.aguardar(intervaloExecucao);
		}
	}
	
	public void interromperDelegacao(){
		continuarDelegacao = false;
	}

	@Override
	public void definirIntervaloExecucao(int milisegundos) {
		this.intervaloExecucao = milisegundos;
	}

	@Override
	public void interromper() {
		continuarDelegacao = false;
	}

	@Override
	public void retomar() {
		continuarDelegacao = true;
		start();		
	}
	
}
