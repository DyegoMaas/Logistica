package servicos;

public interface IServico {
	void definirIntervaloExecucao(int milisegundos);
	void interromper();
	void executar();
	void interromperOuExecutar();
}
