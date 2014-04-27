package estrutura;

public class Logradouro {
	
	private Interseccao interseccaoA;
	private Interseccao interseccaoB;
	private DirecaoAresta direcao;
	private String nome;

	public Logradouro(Interseccao interseccaoA, Interseccao interseccaoB, DirecaoAresta direcao, String nome){
		this.interseccaoA = interseccaoA;
		this.interseccaoB = interseccaoB;
		this.direcao = direcao;
		this.nome = nome;
		
		interseccaoA.addLogradouro(this);
		interseccaoB.addLogradouro(this);
	}

	public Interseccao getInterseccaoA() {
		return interseccaoA;
	}

	public Interseccao getInterseccaoB() {
		return interseccaoB;
	}

	public DirecaoAresta getDirecao() {
		return direcao;
	}

	public String getNome() {
		return nome;
	}
}
