package estrutura;

import estrutura.*;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.assertj.core.api.Assertions.*;

@RunWith(JUnit4.class)
public class RegiaoAbrangenciaTeste {
	
	private final int numeroQualquer = 1;
	private final String logradouroAbrangido = "Rua 1";
	private final String logradouroNaoAbrangido = "Rua 4";
	private String[] lograoudrosAbrangidos = new String[] {logradouroAbrangido, "Rua 2", "Rua 3"};
		
	@Test
	public void logradouros_abrangidos(){		
		RegiaoAbrangencia regiaoAbrangencia = new RegiaoAbrangencia(lograoudrosAbrangidos);
		
		Endereco enderecoAbrangido = new Endereco(logradouroAbrangido, numeroQualquer);
		assertThat(regiaoAbrangencia.estaNaAreaDeAbrangencia(enderecoAbrangido)).isTrue();
	}
	
	@Test
	public void logradouros_nao_abrangidos(){
		RegiaoAbrangencia regiaoAbrangencia = new RegiaoAbrangencia(lograoudrosAbrangidos);		
		
		Endereco enderecoNaoAbrangido = new Endereco(logradouroNaoAbrangido, numeroQualquer);
		assertThat(regiaoAbrangencia.estaNaAreaDeAbrangencia(enderecoNaoAbrangido)).isFalse();
	}
}
