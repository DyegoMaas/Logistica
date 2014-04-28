package pedidos.geracao;

import java.util.Random;

import estrutura.Endereco;
import pedidos.IPedido;
import pedidos.Pedido;
import pedidos.recepcao.IRecebedorPedidos;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GeradorPedidos extends Thread{
	
	private boolean gerarPedidos = true;
	private IRecebedorPedidos recebedorPedidos;
	
	public GeradorPedidos(IRecebedorPedidos recebedorPedidos){
		this.recebedorPedidos = recebedorPedidos;		
	}

	@Override
	public void run(){
		while(deveGerarPedidos()) {
			recebedorPedidos.receberPedido(novoPedido());
			
			try {
				sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void interromperGeracaoPedidos(){
		gerarPedidos = false;
	}
	
	private IPedido novoPedido() {		
		return new Pedido(numeroPacotesAleatorio(), enderecoAleatorio());
	}	

	private Endereco enderecoAleatorio() {
		throw new NotImplementedException();
	}

	private int numeroPacotesAleatorio() {
		return new Random().nextInt(5) + 1;
	}

	private boolean deveGerarPedidos() {
		return gerarPedidos;
	}
	
}
