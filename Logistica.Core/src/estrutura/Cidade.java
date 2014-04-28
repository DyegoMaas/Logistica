package estrutura;

import java.util.HashSet;
import java.util.UUID;

public class Cidade {
	
	private HashSet<Interseccao> interseccoes;
	private HashSet<Logradouro> logradouros;
	private HashSet<String> enderecos;
	private HashSet<Imovel> garagens;
	private HashSet<Imovel> residencias;
	private String nome;
	private UUID id;

	public Cidade(String nome){
		this.id = UUID.randomUUID();
		this.nome = nome;
		interseccoes = new HashSet<Interseccao>();
		logradouros = new HashSet<Logradouro>();
		enderecos = new HashSet<String>();
	}
	
	public void addLogradouro(Logradouro logradouro){	
		this.logradouros.add(logradouro);
		this.enderecos.add(logradouro.getNome());
		
		this.interseccoes.add(logradouro.getInterseccaoA());
		this.interseccoes.add(logradouro.getInterseccaoB());		
	}

	public Interseccao[] getInterseccoes() {
		Interseccao[] interseccoes = new Interseccao[this.interseccoes.size()];
		this.interseccoes.toArray(interseccoes);
		
		return interseccoes; 
	}

	public Logradouro[] getLogradouros() {
		Logradouro[] logradouros = new Logradouro[this.logradouros.size()];
		this.logradouros.toArray(logradouros);
		
		return logradouros; 
	}

	public String[] getEnderecos() {
		String[] enderecos = new String[this.enderecos.size()];
		this.enderecos.toArray(enderecos);
		
		return enderecos; 
	}
	
	public Imovel[] getGaragens() {
		Imovel[] garagens = new Imovel[this.garagens.size()];
		this.garagens.toArray(garagens);
		
		return garagens; 
	}
	
	public Imovel[] getResidencias() {
		Imovel[] residencias = new Imovel[this.residencias.size()];
		this.residencias.toArray(residencias);
		
		return residencias; 
	}
	
	public String getNome(){
		return this.nome;
	}
	
	public UUID getId(){
		return this.id;
	}
}