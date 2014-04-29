package cidades;

import java.io.File;
import java.io.FileWriter;
import java.util.UUID;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.google.gson.Gson;

import estrutura.Cidade;

public class RepositorioCidadeJson implements IRepositorioCidade {

	static final Logger logger = LogManager.getLogger(RepositorioCidadeJson.class.getName());
	
	@Override
	public Cidade obterCidade(UUID id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void salvarCidade(Cidade cidade) throws Exception {
		logger.debug("Salvando cidade com id = " + cidade.getId().toString());
		if(cidade.getId() == null)
			throw new Exception("Propriedade id não especificada.");
		String caminhoArquivo = "C:\\dev\\java\\fixtures\\cidades\\" + cidade.getId();
		File arquivoJsonCidade = new File(caminhoArquivo);
		if(arquivoJsonCidade.createNewFile()){
			Gson gson = new Gson();
			String jsonCidade = gson.toJson(cidade);
			FileWriter fileWriter = new FileWriter(arquivoJsonCidade);
			fileWriter.write(jsonCidade);
			fileWriter.close();
		}
		logger.debug("Finalizou salvamento de cidade com id = " + cidade.getId().toString());
	}
}