package pedidos;

public class Pedido implements IPedido {
	private int numeroPacotes;

	public Pedido(int numeroPacotes){
		this.numeroPacotes = numeroPacotes;
	}

	/* (non-Javadoc)
	 * @see pedidos.IPedido#getNumeroPacotes()
	 */
	@Override
	public int getNumeroPacotes() {
		return numeroPacotes;
	}
}
