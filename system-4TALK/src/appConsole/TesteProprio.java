package appConsole;

import modelo.Individual;
import modelo.Mensagem;
import regras_negocio.Fachada;

public class TesteProprio {
	public static void main(String[] args) {
		try {
			Fachada.criarIndividuo("Allan", "allan123");
			Individual joao = new Individual("Allan","allan123",true);
			Individual maria = new Individual("Maria","maria123",false);
			Mensagem msg = new Mensagem(1,"alallalal",joao,maria);
			joao.adicionarMensagemEnviada(msg);
			maria.adicionarMensagemRecebida(msg);
			System.out.println(joao.toString());
			System.out.println(maria.toString());
		} catch (Exception e){System.out.println(e.getMessage());}
		
	}
}
