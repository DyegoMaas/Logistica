package estrutura;

public class Endereco {
	private String nomeLogradouro;
	private int numero;

	public Endereco(String nomeLogradouro, int numero){
		this.nomeLogradouro = nomeLogradouro;
		this.numero = numero;		
	}

	public int getNumero() {
		return numero;
	}

	public String getNomeLogradouro() {
		return nomeLogradouro;
	}
}
