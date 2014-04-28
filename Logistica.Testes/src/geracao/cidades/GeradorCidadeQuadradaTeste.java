package geracao.cidades;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.*;

import estrutura.Cidade;
import geracao.cidades.GeradorCidadeQuadrada;
import geracao.cidades.IGeradorCidade;

@RunWith(JUnit4.class)
public class GeradorCidadeQuadradaTeste {

	@Test
	public void tamanho_grafo_gerado() throws Exception{
		final int tamanho = 100;
		
		IGeradorCidade gerador = new GeradorCidadeQuadrada();		
		Cidade cidadeGerada = gerador.gerar(tamanho);
		
		assertEquals(tamanho * tamanho, cidadeGerada.getInterseccoes().length); 
		assertEquals(tamanho * tamanho - 1, cidadeGerada.getEnderecos().length);
	}
	
}
