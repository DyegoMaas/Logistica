package pedidos.entregas;

import pedidos.distribuicao.CentroDistribuicao;
import servicos.IServico;
import utils.DelayHelper;

public class GeradorEntregas extends Thread implements IServico {

	private boolean continuarGerandoEntregas = true;
	private CentroDistribuicao centroDistribuicao;
	private int intervaloExecucao;

	public GeradorEntregas(CentroDistribuicao centroDistribuicao) {
		this.centroDistribuicao = centroDistribuicao;
	}

	@Override
	public void run() {
		while(true){
			while (continuarGerandoEntregas) {
				try {
					centroDistribuicao.fazerEntrega();
					DelayHelper.aguardar(intervaloExecucao);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
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
		continuarGerandoEntregas = false;
	}

	private boolean started = false;
	@Override
	public void executar() {
		continuarGerandoEntregas = true;
		
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