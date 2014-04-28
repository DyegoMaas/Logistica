package estrutura;

public abstract class Imovel {

	private TipoImovel tipoImovel;
	private LadoImovel localizacaoImovel;

	public Imovel(TipoImovel tipoImovel, LadoImovel localizacaoImovel) {
		this.tipoImovel = tipoImovel;
		this.localizacaoImovel = localizacaoImovel;
	}

	public TipoImovel getTipoImovel() {
		return tipoImovel;
	}

	public LadoImovel getLocalizacaoImovel() {
		return localizacaoImovel;
	}

}
