package servicos;

public interface IServico {
	int getIntervaloExecucao();
	void definirIntervaloExecucao(int milisegundos);
	void interromper();
	void executar();
	void interromperOuExecutar();
	boolean isExecutando();
}
