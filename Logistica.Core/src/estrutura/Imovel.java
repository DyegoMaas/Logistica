package estrutura;

public abstract class Imovel {

	private TipoImovel tipoImovel;
	private LadoImovel localizacaoImovel;
	private Logradouro logradouro = null;
	private int numero;

	public Imovel(TipoImovel tipoImovel, LadoImovel localizacaoImovel, int numero) {
		this.tipoImovel = tipoImovel;
		this.localizacaoImovel = localizacaoImovel;
		this.numero = numero;
	}

	public TipoImovel getTipoImovel() {
		return tipoImovel;
	}

	public LadoImovel getLocalizacaoImovel() {
		return localizacaoImovel;
	}

	public Logradouro getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(Logradouro logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

}
