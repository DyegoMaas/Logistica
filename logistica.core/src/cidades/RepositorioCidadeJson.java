package cidades;

import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.util.UUID;

import com.google.gson.Gson;

import estrutura.Cidade;

public class RepositorioCidadeJson implements IRepositorioCidade {

	@Override
	public Cidade obterCidade(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void salvarCidade(Cidade cidade) throws Exception {
		if(cidade.getId() == null)
			throw new Exception("Propriedade id não especificada.");
		String caminhoProjeto = getClass().getClassLoader().getResource(".").getPath();
		String caminhoArquivo = caminhoProjeto + "/fixtures/cidades/" + cidade.getId();
		File arquivoJsonCidade = new File(caminhoArquivo);
		if(arquivoJsonCidade.createNewFile()){
			Gson gson = new Gson();
			String jsonCidade = gson.toJson(cidade);
			FileWriter fileWriter = new FileWriter(arquivoJsonCidade);
			fileWriter.write(jsonCidade);
			fileWriter.close();
		}
	}
}