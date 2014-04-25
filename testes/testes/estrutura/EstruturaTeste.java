package testes.estrutura;

import static org.junit.Assert.*;

import org.junit.Test;

import estrutura.Aresta;
import estrutura.Vertice;

public class EstruturaTeste {
	
	@Test
	public void um_vertice_possui_quatro_arestas(){		
		Aresta norte = new Aresta();
		Aresta sul = new Aresta(); 
		Aresta leste = new Aresta();
		Aresta oeste = new Aresta(); 
		 
		Vertice vertice = new Vertice(norte, sul, leste, oeste);
		
		assertEquals(norte, vertice.getNorte());
		assertEquals(sul, vertice.getSul());
		assertEquals(leste, vertice.getLeste());
		assertEquals(oeste, vertice.getOeste());
	}
	
}