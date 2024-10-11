package appsConsole;

/**********************************
 * IFPB - TSI - POO 
 * Prof. Fausto Ayres
 *
 * Teste do sistema 4Zap
 *********************************/

import java.util.ArrayList;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import regras_negocio.Fachada;


//---- atualizado em 30/06


public class Teste1 {

	public Teste1() {
	
		/**
		 * PARTICIPANTE INDIVIDUAL
		 */
		try {
			System.out.println("\ncriar individuos");
			Fachada.criarIndividuo("joao", "123");
			Fachada.criarIndividuo("maria", "123");
			Fachada.criarIndividuo("jose", "123");
			System.out.println("criou individuos");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		/**
		 * MENSAGEM
		 */
		try {
			System.out.println("\ncriar mensagens");
			Fachada.criarMensagem("joao", "maria", "oi maria tudo bem?");
			Fachada.criarMensagem("maria", "joao", "tudo bem joao!");
			Fachada.criarMensagem("joao", "maria", "vamos fazer juntos?");
			Fachada.criarMensagem("maria", "joao", "vou criar um grupo e chamar jose tb");
			Fachada.criarMensagem("joao", "joao", "teste");
			System.out.println("criou mensagens");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("*********************************************");

		System.out.println("\nlistar mensagens do sistema");
		for (Mensagem m : Fachada.listarMensagens())
			System.out.println(m);

		System.out.println("\nlistar individuos ");
		for (Individual ind : Fachada.listarIndividuos())
			System.out.println(ind);

		try {
			System.out.println("\nconversa entre joao e maria");
			for (Mensagem m : Fachada.obterConversa("joao", "maria"))
				System.out.println(m);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\n*******************************************************");

		/**
		 * GRUPO
		 */

		try {
			System.out.println("\ncriar grupos");
			Fachada.criarGrupo("grupo1");
			Fachada.criarGrupo("grupo2");
			System.out.println("criou grupos");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("\ninserir nos grupos");
			Fachada.inserirGrupo("joao", "grupo1");
			Fachada.inserirGrupo("maria", "grupo1");
			Fachada.inserirGrupo("jose", "grupo1");
			System.out.println("inseriu nos grupos");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("\ncriar mensagens em grupo");
			Fachada.criarMensagem("maria", "grupo1", "oi pessoal criei o grupo do projeto");
			Fachada.criarMensagem("joao", "grupo1", "obrigado maria");
			Fachada.criarMensagem("jose", "grupo1", "valeu maria");
			Fachada.criarMensagem("maria", "grupo1", "vamos conversar sobre o projeto hoje");
			Fachada.criarMensagem("maria", "grupo1", "vamos conversar sobre o projeto amanha");
			System.out.println("criou mensagens");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("\nconversa entre joao e grupo1");
			for (Mensagem m : Fachada.obterConversa("joao", "grupo1"))
				System.out.println(m);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("\nconversa entre maria e grupo1");
			for (Mensagem m : Fachada.obterConversa("maria", "grupo1"))
				System.out.println(m);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("\nconversa entre jose e grupo1");
			for (Mensagem m : Fachada.obterConversa("jose", "grupo1"))
				System.out.println(m);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("*********************************************");
		try {
			System.out.println("\nlistar mensagens enviadas por maria");
			ArrayList<Mensagem> lista = Fachada.listarMensagensEnviadas("maria");
			for (Mensagem m : lista)
				System.out.println(m);

			System.out.println("\napagar a ultima mensagem enviada de maria");
			Mensagem ultima = lista.get(lista.size() - 1);
			Fachada.apagarMensagem("maria", ultima.getId());
			System.out.println("apagou a mensagem " + ultima.getId());

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("\nremover jose do grupo");
			Fachada.removerGrupo("jose", "grupo1");
			System.out.println("removeu do grupo");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("*******************************************************");

		System.out.println("listar mensagens finais");
		for (Mensagem m : Fachada.listarMensagens())
			System.out.println(m);

		System.out.println("\nlistar individuos finais");
		for (Individual ind : Fachada.listarIndividuos())
			System.out.println(ind);

		System.out.println("\nlistar grupos finais");
		for (Grupo g : Fachada.listarGrupos())
			System.out.println(g);

		/**
		 * ADMINISTRADOR
		 */

		System.out.println("\n*******************************************************");
		try {
			Fachada.criarAdministrador("admin", "admin");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			System.out.println("ESPIONAR o termo 'projeto'");
			for (Mensagem m : Fachada.espionarMensagens("admin", "projeto"))
				System.out.println(m);

			System.out.println("\nAUSENTES");
			for (String nome : Fachada.ausentes("admin"))
				System.out.println(nome);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		System.out.println("\nlistar mensagens do sistema");
		for (Mensagem m : Fachada.listarMensagens())
			System.out.println(m);
			System.out.println("\n");
		
		System.out.println("fim do programa");
		
		
//		Fachada.lerDados();
		

	}

	// =================================================
	public static void main(String[] args) {
		new Teste1();
	}
}
