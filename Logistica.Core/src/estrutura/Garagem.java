package estrutura;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class Garagem extends Imovel{

	public final int TIMEOUT_MS = 100;
	
	Semaphore caminhoes;

	public Garagem(LadoImovel ladoImovel, int numero, int numeroCaminhoes){
		super(TipoImovel.GARAGEM, ladoImovel, numero);
		
		caminhoes = new Semaphore(numeroCaminhoes);
	}
	
	public boolean entregar() throws InterruptedException{
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
