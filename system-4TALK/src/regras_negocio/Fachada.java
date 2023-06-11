package regras_negocio;

import modelo.Grupo;
import modelo.Individual;
import modelo.Participante;
import repositorio.Repositorio;

public class Fachada {
	private static Repositorio repositorio = new Repositorio();
	private Fachada() {}
	
	// ainda em andamento
	public static void criarIndividuo(String nome_individuo, String senha) throws Exception {
		if (nome_individuo.isEmpty() || senha.isEmpty()) {
			throw new IllegalArgumentException("Nome ou senha nao podem estar vazios. Verifique se digitou algum campo vazio.");
		}
		Individual i = repositorio.localizarIndividuo(nome_individuo);
		if (i != null) {
			throw new Exception("Nao criou participante - individuo ja cadastrado: " + nome_individuo);
		}
		i = new Individual(nome_individuo, senha, false);
		repositorio.adicionarIndividuo(i);
		//repositorio.salvarObjetos();
	}
	// ainda em andamento
	public static void criarAdministrador(String nome_administrador, String senha) throws Exception {
		if (nome_administrador.isEmpty() || senha.isEmpty()) {
			throw new IllegalArgumentException("Nome ou senha nao podem estar vazios. Verifique se digitou algum campo vazio.");
		}
		Individual i = repositorio.localizarIndividuo(nome_administrador);
		if (i != null) {
			throw new Exception("Nao criou participante - administrador ja cadastrado: " + nome_administrador);
		}
		i = new Individual(nome_administrador, senha, true);
		repositorio.adicionarIndividuo(i);
		//repositorio.salvarObjetos();
	}
	// em andamento
	public static void criarGrupo(String nome_grupo) throws Exception {
		if (nome_grupo.isEmpty()) {
			throw new IllegalArgumentException("Nome do grupo nao pode estar vazio. Verifique se deixou nome vazio.");
		}
		Grupo g = repositorio.localizarGrupo(nome_grupo);
		if (g != null) {
			throw new Exception("Nao criou participante - grupo ja cadastrado: " + nome_grupo);
		}
		g = new Grupo(nome_grupo);
		repositorio.adicionarGrupo(g);
		//repositorio.salvarObjetos();
	}
	// em andamento
	public static void criarMensagem(String nome_remetente, String nome_destinatario, String texto) throws Exception {
		if (nome_remetente.isEmpty() || nome_destinatario.isEmpty() || texto.isEmpty()) {
			throw new IllegalArgumentException("Nomes ou texto nao podem ser vazios. Verifique se deixou algum campo vazio.");
		}
		Individual remetente = repositorio.localizarIndividuo(nome_remetente);
		if (remetente == null) {
			throw new Exception("Mensagem nao enviada - remetente desconhecido: "+nome_remetente);
		}
		Participante destinatario = repositorio.localizarIndividuo(nome_destinatario);
		if (destinatario == null) {
			throw new Exception("Mensagem nao enviada - destinatario desconhecido: " + nome_destinatario);
		}
		// Prosseguir daqui
		//repositorio.adicionarMensagem(g);
		//repositorio.salvarObjetos();
	}
	
	public static boolean validarIndividuo(String nome_individuo, String senha) throws Exception {
		if (nome_individuo.isEmpty() || senha.isEmpty()) {
			throw new IllegalArgumentException("Nome ou senha nao podem estar vazios.");
		}
		Individual i = repositorio.localizarIndividuo(nome_individuo);
		if (i == null) {
			return false;
		}
		return true;
	}
	// Insere um individuo a um grupo - relaciona individuo a um grupo
	public static void inserirGrupo(String nome_individuo, String nome_grupo) throws  Exception {	
		Grupo gru = repositorio.localizarGrupo(nome_grupo);
		if(gru == null) 
			throw new Exception("Nao inseriu individuo - grupo inexistente: " + nome_grupo);
		Individual in = repositorio.localizarIndividuo(nome_individuo);
		if(in == null)
			throw new Exception("Nao inseriu individuo - individuo inexistente: " + nome_individuo);
		gru.adicionar(in); // Individuo adicionado ao grupo
		//repositorio.salvarObjetos();
	}
	// Remove um individuo especifico do grupo
	public static void removerGrupo(String nome_individuo, String nome_grupo) throws Exception {
		nome_individuo = nome_individuo.trim();
		Individual in = repositorio.localizarIndividuo(nome_individuo);
		if (in==null)
			throw new Exception("Nao removeu o individuo - individuo inexistente: "+nome_individuo);
		Grupo gru = in.getGrupo(nome_grupo);
		if(gru==null) 
			throw new Exception("Individuo nao esta no grupo.");
		gru.remover(in); // Individuo removido do grupo
		//repositorio.salvarObjetos();
	}
}
