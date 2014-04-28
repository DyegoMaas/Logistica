package geracao.cidades;

import java.io.File;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import cidades.IRepositorioCidade;
import cidades.RepositorioCidadeJson;
import estrutura.Cidade;

@RunWith(JUnit4.class)
public class RepositorioCidadeJsonTeste {

	private IGeradorCidade geradorCidade;
	private IRepositorioCidade repositorioCidade;
	
	@Before
	public void setup(){
		geradorCidade = new GeradorCidadeQuadrada();
		repositorioCidade = new RepositorioCidadeJson();
	}
	
	@Test
	public void salvando_cidade_em_json() throws Exception{
		Cidade cidade = geradorCidade.gerar("Teste", 10);
		UUID idCidade = cidade.getId();
		repositorioCidade.salvarCidade(cidade);
		File arquivoSalvo = new File(getClass().getResource("cidades/" + idCidade.toString()).getPath());
		Assert.assertTrue(arquivoSalvo.exists());
	}

	@Test
	public void lendo_cidade_de_um_json() throws Exception {
		Cidade cidade = geradorCidade.gerar("Teste", 10);
		UUID idCidade = cidade.getId();
		repositorioCidade.salvarCidade(cidade);
		Cidade cidadeObtida = repositorioCidade.obterCidade(idCidade);
	}
}