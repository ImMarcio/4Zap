package appConsole;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import regras_negocio.Fachada;

public class Teste1Console {

	public static void main(String[] args) throws Exception {
		try {
			Fachada.criarIndividuo("Allan","allan123");
			Fachada.criarAdministrador("Allan", "allan123");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

}
