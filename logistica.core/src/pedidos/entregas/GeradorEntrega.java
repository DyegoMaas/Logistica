package pedidos.entregas;

import pedidos.distribuicao.CentroDistribuicao;

public class GeradorEntrega extends Thread {

	private boolean continuarGerandoEntregas = true;
	private CentroDistribuicao centroDistribuicao;

	public GeradorEntrega(CentroDistribuicao centroDistribuicao){
		this.centroDistribuicao = centroDistribuicao;
	}

	@Override
	public void run() {
		while(continuarGerandoEntregas){
			try {
				centroDistribuicao.fazerEntrega();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void interromperGeracaoEntregas(){
		continuarGerandoEntregas = false;
	}
}