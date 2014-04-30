package pedidos.recepcao;

import java.util.List;

import pedidos.IPedido;
import pedidos.distribuicao.CentroDistribuicao;
import servicos.IServico;
import utils.DelayHelper;

public class DelegadorPedidos extends Thread implements IServico {

	private List<CentroDistribuicao> centrosDistribuicao;
	private FilaPedidosEntrada filaPedidosEntrada;
	private boolean continuarDelegacao = true;
	private int intervaloExecucao;

	public DelegadorPedidos(FilaPedidosEntrada filaPedidosEntrada, List<CentroDistribuicao> centrosDistribuicao) {
		this.filaPedidosEntrada = filaPedidosEntrada;
		this.centrosDistribuicao = centrosDistribuicao;
	}

	@Override
	public void run() {
		while(true){
			while (continuarDelegacao) {
				try {
					IPedido pedido = filaPedidosEntrada.obterPedido();
	
					for (CentroDistribuicao centroDistribuicao : centrosDistribuicao) {
						if (centroDistribuicao.tentarAdicionar(pedido)) {
							System.out.printf("pedido %s delegado para o centro de distribuicao %d\n", pedido.getIdPedido(), centroDistribuicao.getId());
							break;
						}
					}
				} catch (InterruptedException e) {
					// TODO logar erro
					e.printStackTrace();
				}
	
				DelayHelper.aguardar(intervaloExecucao);
			}
			DelayHelper.aguardar(50);
		}
	}

	@Override
	public void definirIntervaloExecucao(int milisegundos) {
		this.intervaloExecucao = milisegundos;
	}

	@Override
	public void interromper() throws InterruptedException{
		continuarDelegacao = false;
	}

	private boolean started = false;
	@Override
	public void executar() {
		continuarDelegacao = true;
		
		if(!started){
			start();
			started = true;
		}
	}

	@Override
	public void interromperOuExecutar() throws InterruptedException{
		if(continuarDelegacao){
			interromper();
		}
		else {
			executar();
		}
	}

	@Override
	public boolean isExecutando() {
		return continuarDelegacao;
	}

	@Override
	public int getIntervaloExecucao() {
		return intervaloExecucao;
	}
	
}
