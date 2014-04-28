package estrutura;

import java.util.HashSet;

public class Interseccao {

	private HashSet<Logradouro> logradouros;
	private int coordenadaY;
	private int coordenadaX;

	public Interseccao(int coordenadaX, int coordenadaY) {	
		this.coordenadaX = coordenadaX;
		this.coordenadaY = coordenadaY;
		this.logradouros = new HashSet<Logradouro>();
	}
	
	public void addLogradouro(Logradouro logradouro){
		logradouros.add(logradouro);
	}
	
	public Logradouro[] getLogradouros() {
		Logradouro[] array = new Logradouro[logradouros.size()];
		logradouros.toArray(array);
		
		return array;
	}

	public int getCoordenadaY() {
		return coordenadaY;
	}

	public int getCoordenadaX() {
		return coordenadaX;
	}

	@Override
	public String toString() {
		return "Interseccao (" + coordenadaX + ", " + coordenadaY + ")";
	}
}
