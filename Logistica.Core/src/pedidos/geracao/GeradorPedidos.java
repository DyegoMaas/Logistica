package pedidos.geracao;

import java.util.Random;

import estrutura.Cidade;
import estrutura.Endereco;
import estrutura.Imovel;
import estrutura.Logradouro;
import estrutura.Residencia;
import pedidos.IPedido;
import pedidos.Pedido;
import pedidos.recepcao.IRecebedorPedidos;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class GeradorPedidos extends Thread {
	
	private boolean gerarPedidos = true;
	private IRecebedorPedidos recebedorPedidos;
	private Cidade cidade;
	
	public GeradorPedidos(IRecebedorPedidos recebedorPedidos, Cidade cidade){
		this.recebedorPedidos = recebedorPedidos;
		this.cidade = cidade;		
	}

	@Override
	public void run(){
		while(deveGerarPedidos()) {			
			recebedorPedidos.receberPedido(novoPedido());
			
			try {
				Thread.sleep(50);
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
		Imovel[] residencias = cidade.getResidencias();
		
		Random random = new Random();
		Imovel residenciaEscolhida = residencias[random.nextInt(residencias.length)];
				
		return new Endereco(residenciaEscolhida.getLogradouro().getNome(), residenciaEscolhida.getNumero());
	}

	private int numeroPacotesAleatorio() {
		return new Random().nextInt(5) + 1;
	}

	private boolean deveGerarPedidos() {
		return gerarPedidos;
	}
	
}
