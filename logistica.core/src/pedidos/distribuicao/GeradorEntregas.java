package pedidos.distribuicao;

public class GeradorEntregas extends Thread {
	private FilaPedidos filaPedidos;

	public GeradorEntregas(FilaPedidos filaPedidos){
		this.filaPedidos = filaPedidos;		
	}
	
	@Override
	public void run(){
		
	}
}
