package servicos;

//ISSO EH UTIL???
public class ConfiguradorServicos {
	public final int INTERVALO_DELEGACAO_PEDIDOS = 50;
	public final int INTERVALO_GERACAO_PEDIDOS = 50;
	
	public void configurarDelegadorPedidos(IServico servico){
		servico.definirIntervaloExecucao(INTERVALO_DELEGACAO_PEDIDOS);
	}
	
	public void configurarGeradorPedidos(IServico servico){
		servico.definirIntervaloExecucao(INTERVALO_GERACAO_PEDIDOS);
	}
}
