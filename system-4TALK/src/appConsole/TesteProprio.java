package appConsole;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;

public class TesteProprio {
	public static void main(String[] args) {
		try {
			Individual joao = new Individual("João","allan123",true);
			Individual maria = new Individual("Maria","maria123",false);
			Grupo grupo1 = new Grupo("Grupo1");
			grupo1.adicionar(joao);
			grupo1.adicionar(maria);
			Mensagem msg = new Mensagem(1,"alallalal",joao,maria);
			joao.adicionarMensagemEnviada(msg);
			maria.adicionarMensagemRecebida(msg);
			System.out.println(joao.toString());
			System.out.println(maria.toString());
			System.out.println(grupo1.toString());
			
			System.out.println(grupo1.getIndividuos());
		} catch (Exception e){System.out.println(e.getMessage());}
		
	}
}
