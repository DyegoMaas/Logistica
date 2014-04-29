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
	
	public Garagem(LadoImovel ladoImovel, int numero, int numeroCaminhoes, IRegiaoAbrangencia regiaoAbrangencia){
		super(TipoImovel.GARAGEM, ladoImovel, numero);
		this.regiaoAbrangencia = regiaoAbrangencia;
		
		caminhoes = new Semaphore(numeroCaminhoes);
	}
	
	public boolean ehResponsavelPorEntregasNoEndereco(Endereco endereco){
		return regiaoAbrangencia.estaNaAreaDeAbrangencia(endereco);
	}
	
	public boolean entregar(Entrega entrega) throws InterruptedException{
		if(caminhoes.tryAcquire(TIMEOUT_MS))
		{
			efetuarEntrega(entrega);
			caminhoes.release();
			return true;
		}
		else
			return false;
	}

	private void efetuarEntrega(Entrega entrega) throws InterruptedException {
		System.out.println("Efetuando entregas.");
		for(IPedido pedido : entrega.getPedidos()){
			Thread.sleep(tempoViagemAleatorio(pedido.getNumeroPacotes()));
		}
	}

	private int tempoViagemAleatorio(int variacao) {
		Random random = new Random();
		return random.nextInt(150 * variacao);
	}	
	
}
