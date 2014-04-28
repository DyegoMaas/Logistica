package estrutura;

public class RegiaoAbrangencia implements IRegiaoAbrangencia{

	private String[] logradourosAbrangidos;
	
	public RegiaoAbrangencia(String[] logradourosAbrangidos){
		this.logradourosAbrangidos = logradourosAbrangidos;
	}
	
	@Override
	public boolean estaNaAreaDeAbrangencia(Endereco endereco) {
		for (String nomeEndereco : this.logradourosAbrangidos) {
			if(nomeEndereco.equals(endereco.getNomeLogradouro()))
				return true;
		}
		return false;
	}

}
