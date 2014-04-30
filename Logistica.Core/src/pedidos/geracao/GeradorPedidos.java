package pedidos.geracao;

import java.util.Random;

import pedidos.IPedido;
import pedidos.Pedido;
import pedidos.recepcao.IRecebedorPedidos;
import servicos.IServico;
import utils.DelayHelper;
import estrutura.Cidade;
import estrutura.Endereco;
import estrutura.Imovel;

public class GeradorPedidos extends Thread implements IServico {

	private boolean deveGerarPedidos = true;
	private int intervaloExecucao;
	private IRecebedorPedidos recebedorPedidos;
	private Cidade cidade;

	public GeradorPedidos(IRecebedorPedidos recebedorPedidos, Cidade cidade) {
		this.recebedorPedidos = recebedorPedidos;
		this.cidade = cidade;
	}

	@Override
	public void run() {
		while(true){
			while (deveGerarPedidos) {
				System.out.println("x");
				IPedido novoPedido = gerarPedido();
	
				recebedorPedidos.receberPedido(novoPedido);
				DelayHelper.aguardar(intervaloExecucao);
			}
			DelayHelper.aguardar(50);
		}
	}

	private IPedido gerarPedido() {
		Pedido novoPedido = new Pedido(numeroPacotesAleatorio(), enderecoAleatorio());
		System.out.printf("pedido %s gerado\n", novoPedido.getIdPedido());

		return novoPedido;
	}

	private Endereco enderecoAleatorio() {
		Imovel[] residencias = cidade.getResidencias();

		Random random = new Random();
		Imovel residenciaEscolhida = residencias[random.nextInt(residencias.length)];

		return new Endereco(residenciaEscolhida.getLogradouro().getNome(), residenciaEscolhida.getNumero());
	}

	private int numeroPacotesAleatorio() {
		return new Random().nextInt(5) + 1;
	}

	@Override
	public void definirIntervaloExecucao(int milisegundos) {
		intervaloExecucao = milisegundos;
	}

	@Override
	public void interromper() throws InterruptedException {
		deveGerarPedidos = false;
	}

	private boolean started = false;
	@Override
	public void executar() {
		deveGerarPedidos = true;
		
		if(!started){
			start();
			started = true;
		}
	}

	@Override
	public void interromperOuExecutar() throws InterruptedException {
		if (deveGerarPedidos) {
			interromper();
		} else {
			executar();
		}
	}

	@Override
	public boolean isExecutando() {
		return deveGerarPedidos;
	}

	@Override
	public int getIntervaloExecucao() {
		return intervaloExecucao;
	}
}
