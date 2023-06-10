package regras_negocio;

import modelo.Individual;
import repositorio.Repositorio;

public class Fachada {
	private static Repositorio repositorio = new Repositorio();
	private Fachada() {}
	
	// Criar um Individuo
	public static void criarIndividuo(String nome_individuo, String senha) throws Exception {
		if (nome_individuo == null || senha == null) {
			throw new IllegalArgumentException("Nome ou senha nao podem estar vazios. Verifique se digitou algum campo vazio.");
		}
		Individual i = repositorio.localizarIndividuo(nome_individuo);
		if (i != null) {
			throw new Exception("Nao criou participante - individuo ja cadastrado:" + nome_individuo);
		}
		i = new Individual(nome_individuo, senha, false);
		repositorio.adicionarIndividuo(i);
		//repositorio.salvarObjetos();
	}
	// Validar um individuo
	public static boolean validarIndividuo(String nome_individuo, String senha) throws Exception {
		if (nome_individuo == null || senha == null) {
			throw new IllegalArgumentException("Nome ou senha nao podem estar vazios.");
		}
		Individual i = repositorio.localizarIndividuo(nome_individuo);
		if (i == null) {
			return false;
		}
		return true;
	}
}
