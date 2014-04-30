package pedidos.entregas;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import pedidos.distribuicao.CentroDistribuicao;
import servicos.IServico;
import utils.DelayHelper;

public class GeradorEntregas extends Thread implements IServico {

	private final ReentrantLock lock = new ReentrantLock();
	private Condition podeContinuar = lock.newCondition();
	
	private boolean continuarGerandoEntregas = true;
	private CentroDistribuicao centroDistribuicao;
	private int intervaloExecucao;

	public GeradorEntregas(CentroDistribuicao centroDistribuicao) {
		this.centroDistribuicao = centroDistribuicao;
	}

	@Override
	public void run() {
		while(true){
			lock.lock();
			
			try {
				while(!continuarGerandoEntregas)
					podeContinuar.await();
				
				centroDistribuicao.fazerEntrega();
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
		continuarGerandoEntregas = false;
	}

	private boolean started = false;
	@Override
	public void executar() {
		lock.lock();
		
		continuarGerandoEntregas = true;
		
		podeContinuar.signalAll();		
		lock.unlock();
		
		if(!started){
			start();
			started = true;
		}
	}

	@Override
	public void interromperOuExecutar() throws InterruptedException{
		if(continuarGerandoEntregas){
			interromper();
		}
		else {
			executar();
		}
	}
	
	@Override
	public boolean isExecutando() {
		return continuarGerandoEntregas;
	}

	@Override
	public int getIntervaloExecucao() {
		return intervaloExecucao;
	}
}