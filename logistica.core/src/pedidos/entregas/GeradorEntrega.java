package pedidos.entregas;

import pedidos.distribuicao.CentroDistribuicao;
import servicos.IServico;
import utils.DelayHelper;

public class GeradorEntrega extends Thread implements IServico{

	private boolean continuarGerandoEntregas = true;
	private CentroDistribuicao centroDistribuicao;
	private int intervaloExecucao;

	public GeradorEntrega(CentroDistribuicao centroDistribuicao){
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
	public void retomar() {
		continuarGerandoEntregas = true;
		start();
	}
}