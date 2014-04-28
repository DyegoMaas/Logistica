package estrutura;

import java.util.HashSet;

public class Logradouro {
	
	private Interseccao interseccaoA;
	private Interseccao interseccaoB;
	private DirecaoAresta direcao;
	private String nome;
	private HashSet<Imovel> imoveis; 

	public Logradouro(Interseccao interseccaoA, Interseccao interseccaoB, DirecaoAresta direcao, String nome){
		this.interseccaoA = interseccaoA;
		this.interseccaoB = interseccaoB;
		this.direcao = direcao;
		this.nome = nome;
		
		interseccaoA.addLogradouro(this);
		interseccaoB.addLogradouro(this);
		imoveis = new HashSet<Imovel>();
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

	public HashSet<Imovel> getImoveis() {
		return imoveis;
	}

	public void addImovel(Imovel imovel) {
		this.imoveis.add(imovel);
		imovel.setLogradouro(this);
	}
}