package estrutura;

import java.util.Random;
import java.util.concurrent.Semaphore;

import org.omg.PortableServer.ForwardRequestHelper;

import pedidos.IPedido;
import pedidos.entregas.Entrega;

public class Garagem extends Imovel{

	public final int TIMEOUT_MS = 100;
	
	private IRegiaoAbrangencia regiaoAbrangencia;
	private Semaphore caminhoes;
	private int id;
	
	public Garagem(int id, LadoImovel ladoImovel, int numero, int numeroCaminhoes, IRegiaoAbrangencia regiaoAbrangencia){
		super(TipoImovel.GARAGEM, ladoImovel, numero);
		this.id = id;
		this.regiaoAbrangencia = regiaoAbrangencia;
		
		caminhoes = new Semaphore(numeroCaminhoes);
	}
	
	public boolean ehResponsavelPorEntregasNoEndereco(Endereco endereco){
		return regiaoAbrangencia.estaNaAreaDeAbrangencia(endereco);
	}

	public void entregar(Entrega entrega) throws InterruptedException{
		System.out.println("Iniciando entregas na garagem " + id + ".");
		caminhoes.acquire();
		efetuarEntrega(entrega);
		caminhoes.release();
		System.out.println("Finalizada entrega na garagem " + id + ".");
	}

	private void efetuarEntrega(Entrega entrega) throws InterruptedException {
		for(IPedido pedido : entrega.getPedidos()){
			Thread.sleep(tempoViagemAleatorio(pedido.getNumeroPacotes()));
		}
	}

	private int tempoViagemAleatorio(int variacao) {
		Random random = new Random();
		return random.nextInt(150 * variacao);
	}	
	
}
