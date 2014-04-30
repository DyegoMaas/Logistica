package pedidos.recepcao;

import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import pedidos.IPedido;
import pedidos.distribuicao.CentroDistribuicao;
import servicos.IServico;
import utils.DelayHelper;

public class DelegadorPedidos extends Thread implements IServico {

	private final ReentrantLock lock = new ReentrantLock();
	private Condition podeContinuar = lock.newCondition();
	
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
			lock.lock();
			
			try {
				while(!continuarDelegacao)
					podeContinuar.await();
				
				IPedido pedido = filaPedidosEntrada.obterPedido();
				
				for (CentroDistribuicao centroDistribuicao : centrosDistribuicao) {
					if (centroDistribuicao.tentarAdicionar(pedido)) {
						System.out.printf("pedido %s delegado para o centro de distribuicao %d\n", pedido.getIdPedido(), centroDistribuicao.getId());
						break;
					}
				}
				
				DelayHelper.aguardar(intervaloExecucao);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			lock.unlock();
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
		lock.lock();				
		continuarDelegacao = true;		
		podeContinuar.signalAll();		
		lock.unlock();
		
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
