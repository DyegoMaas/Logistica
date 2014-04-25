package estrutura;

public class Vertice {

	private Aresta norte;
	private Aresta sul;
	private Aresta leste;
	private Aresta oeste;
	
	//ou
	//private Aresta[] arestas = new Aresta[4]; //???

	public Vertice(Aresta norte, Aresta sul, Aresta leste, Aresta oeste) {
		this.norte = norte;
		this.sul = sul;
		this.leste = leste;
		this.oeste = oeste;
	}

	public Aresta getNorte() {
		return norte;
	}

	public Aresta getSul() {
		return sul;
	}

	public Aresta getLeste() {
		return leste;
	}

	public Aresta getOeste() {
		return oeste;
	}

}
