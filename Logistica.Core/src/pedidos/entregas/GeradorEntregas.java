package pedidos.entregas;

import pedidos.distribuicao.CentroDistribuicao;
import servicos.IServico;
import utils.DelayHelper;

public class GeradorEntregas extends Thread implements IServico{

	private boolean continuarGerandoEntregas = true;
	private CentroDistribuicao centroDistribuicao;
	private int intervaloExecucao;

	public GeradorEntregas(CentroDistribuicao centroDistribuicao){
		this.centroDistribuicao = centroDistribuicao;
	}

	@Override
	public void run() {
		while(continuarGerandoEntregas){
			try {
				centroDistribuicao.fazerEntrega();
				DelayHelper.aguardar(intervaloExecucao);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void definirIntervaloExecucao(int milisegundos) {
		this.intervaloExecucao = milisegundos;
	}

	@Override
	public void interromper() {
		continuarGerandoEntregas = false;		
	}

	@Override
	public void executar() {
		continuarGerandoEntregas = true;
		start();
	}
}