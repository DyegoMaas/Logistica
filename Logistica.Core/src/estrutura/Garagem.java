package estrutura;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import pedidos.IPedido;
import pedidos.entregas.Entrega;
import utils.DelayHelper;

public class Garagem extends Imovel{

	public final int TIMEOUT_MS = 15;
	
	private IRegiaoAbrangencia regiaoAbrangencia;
	private Semaphore caminhoes;
	private int id;
	
	public Garagem(int id, LadoImovel ladoImovel, int numeroEndereco, int numeroCaminhoes, IRegiaoAbrangencia regiaoAbrangencia){
		super(TipoImovel.GARAGEM, ladoImovel, numeroEndereco);
		this.id = id;
		this.regiaoAbrangencia = regiaoAbrangencia;
		
		caminhoes = new Semaphore(numeroCaminhoes);
	}
	
	public boolean ehResponsavelPorEntregasNoEndereco(Endereco endereco){
		return regiaoAbrangencia.estaNaAreaDeAbrangencia(endereco);
	}

	public void entregar(Entrega entrega) throws InterruptedException{		
		caminhoes.acquire();
		System.out.println("Iniciando entregas na garagem " + id + ".");
		
		efetuarEntrega(entrega);
		
		System.out.println("Finalizada entrega na garagem " + id + ".");
		caminhoes.release();
	}

	private void efetuarEntrega(Entrega entrega) {
		for(IPedido pedido : entrega.getPedidos()){
			DelayHelper.aguardar(tempoViagemAleatorio(pedido.getNumeroPacotes()));
		}
	}

	private int tempoViagemAleatorio(int variacao) {
		Random random = new Random();
		return random.nextInt(150 * variacao);
	}
	
}
