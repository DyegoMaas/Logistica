package pedidos.distribuicao;

public class GeradorEntregas extends Thread {
	private FilaPedidos filaPedidos;

	/**
	 * A fila de pedidos eh a mesma do centro de distribuicao
	 * @param filaPedidos
	 */
	public GeradorEntregas(FilaPedidos filaPedidos){
		this.filaPedidos = filaPedidos;		
	}
	
	@Override
	public void run(){
		
	}
}
