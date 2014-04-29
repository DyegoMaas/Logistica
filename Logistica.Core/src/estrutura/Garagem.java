package estrutura;

import java.util.Random;
import java.util.concurrent.Semaphore;

import pedidos.IPedido;

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
	
	public boolean entregar(IPedido pedido) throws InterruptedException{
		if(caminhoes.tryAcquire(TIMEOUT_MS))
		{
			efetuarEntrega();
			
			caminhoes.release();
			return true;
		}
		else
			return false;
	}

	private void efetuarEntrega() throws InterruptedException {
		// TODO logar entrega
		Thread.sleep(tempoViagemAleatorio());
	}

	private int tempoViagemAleatorio() {
		Random random = new Random();
		return random.nextInt(150);
	}	
	
}
