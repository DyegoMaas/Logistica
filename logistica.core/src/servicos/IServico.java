package servicos;

public interface IServico {
	int getIntervaloExecucao();
	void definirIntervaloExecucao(int milisegundos);
	void interromper() throws InterruptedException;
	void executar();
	void interromperOuExecutar() throws InterruptedException;
	boolean isExecutando();
	StatusExecucaoServico getStatusExecucao();
}
