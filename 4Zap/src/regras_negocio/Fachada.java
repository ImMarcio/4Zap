/*
 * @authors Allan Amancio and Marcio Jose
 * 
 * Fachada class
 */
package regras_negocio;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.TreeMap;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;
import repositorio.Repositorio;

public class Fachada {
	private static Repositorio repositorio = new Repositorio();
	private Fachada() {}
	
	public static void criarIndividuo(String nome_individuo, String senha) throws Exception {
		nome_individuo = nome_individuo.trim();
		senha = senha.trim();
		if (nome_individuo.isEmpty() || senha.isEmpty()) {
			throw new IllegalArgumentException("Nome ou senha nao podem estar vazios.");
		}
		Individual i = (Individual) repositorio.localizarParticipante(nome_individuo);
		if (i != null ) {
			throw new Exception("Nao criou participante - individuo ja cadastrado: " + nome_individuo);
		}	
		i = new Individual(nome_individuo, senha, false);
		repositorio.adicionarParticipante(i);
		repositorio.salvarObjetos();
	}

	public static void criarAdministrador(String nome_administrador, String senha) throws Exception {
		nome_administrador = nome_administrador.trim();
		senha = senha.trim();
		if (nome_administrador.isEmpty() || senha.isEmpty()) {
			throw new IllegalArgumentException("Nome ou senha nao podem estar vazios.");
		}
		Individual i = (Individual) repositorio.localizarParticipante(nome_administrador);
		if (i != null && i.getNome().equals(nome_administrador)) {
			i.setAdministrador(true);
		}
		else {
			i = new Individual(nome_administrador, senha, true);
		}
		repositorio.adicionarParticipante(i);
		repositorio.salvarObjetos();
	}
	
	public static void criarGrupo(String nome_grupo) throws Exception {
		nome_grupo = nome_grupo.trim();
		if (nome_grupo.isEmpty()) {
			throw new IllegalArgumentException("Nome do grupo nao pode estar vazio.");
		}
		Individual i = (Individual) repositorio.localizarParticipante(nome_grupo);
		if (i != null ) {
			throw new Exception("Nao criou participante - individuo ja cadastrado: " + nome_grupo);
		}
		Grupo g = (Grupo) repositorio.localizarParticipante(nome_grupo);
		if (g != null) {
			throw new Exception("Nao criou participante - grupo ja cadastrado: " + nome_grupo);
		}
		g = new Grupo(nome_grupo);
		repositorio.adicionarParticipante(g);
		repositorio.salvarObjetos();
	}

	public static void criarMensagem(String nome_remetente, String nome_destinatario, String texto) throws Exception {
		nome_remetente = nome_remetente.trim();
		nome_destinatario = nome_destinatario.trim();
		if (nome_remetente.isEmpty() || nome_destinatario.isEmpty() || texto.isEmpty() || texto.isBlank()) {
			throw new IllegalArgumentException("Nomes ou texto nao podem ser vazios.");
		}
		Individual remetente = (Individual) repositorio.localizarParticipante(nome_remetente);
		if (remetente == null) {
			throw new Exception("Mensagem nao enviada - remetente desconhecido: "+nome_remetente);
		}
		Participante destinatario = repositorio.localizarParticipante(nome_destinatario);
		if (destinatario == null) {
			throw new Exception("Mensagem nao enviada - destinatario desconhecido: " + nome_destinatario);
		}
		int id = repositorio.gerarId();
		Mensagem msg = new Mensagem(id,texto,remetente,destinatario, LocalDateTime.now());
		remetente.adicionarMensagemEnviada(msg);
		
		if (destinatario instanceof Grupo) {
			Grupo grupo = (Grupo) destinatario;
			if (grupo.localizar(remetente.getNome())!=null) {
				destinatario.adicionarMensagemRecebida(msg);
	    		texto = nome_remetente + "/" + texto;
	            for (Individual membro : grupo.getIndividuos()) {
	            	if (!(membro.equals(remetente))) {
	            		Mensagem copia = new Mensagem(id, texto, grupo, membro,LocalDateTime.now());
	            		if(copia.getEmitente() instanceof Grupo) {
	            			repositorio.adicionarMensagem(copia);
	            		}
	            	    remetente.adicionarMensagemEnviada(copia);
	            	    destinatario.adicionarMensagemRecebida(copia);
	            	}
	            }
			}
			throw new Exception("Individuo nao esta no grupo: "+remetente.getNome());
		} else { destinatario.adicionarMensagemRecebida(msg); }
		repositorio.adicionarMensagem(msg);
		repositorio.salvarObjetos();
	}
	
	public static void inserirGrupo(String nome_individuo, String nome_grupo) throws  Exception {
		nome_individuo = nome_individuo.trim();
		nome_grupo = nome_grupo.trim();
		Grupo grupo = (Grupo) repositorio.localizarParticipante(nome_grupo);
		if(grupo == null) 
			throw new Exception("Nao inseriu individuo - grupo inexistente: " + nome_grupo);
		Individual in = (Individual) repositorio.localizarParticipante(nome_individuo);
		if(in == null)
			throw new Exception("Nao inseriu individuo - individuo inexistente: " + nome_individuo);
		Individual individuoDoGrupo = grupo.localizar(nome_individuo);
		if(individuoDoGrupo != null)
			throw new Exception("Nao inseriu individuo - individuo já está no grupo: " + nome_individuo);
		grupo.adicionar(in);
		repositorio.salvarObjetos();
	}

	public static void removerGrupo(String nome_individuo, String nome_grupo) throws Exception {
		nome_individuo = nome_individuo.trim();
		nome_grupo = nome_grupo.trim();
		Individual in = (Individual) repositorio.localizarParticipante(nome_individuo);
		if (in==null)
			throw new Exception("Nao removeu o individuo - individuo inexistente: "+nome_individuo);
		Grupo gru = in.getGrupo(nome_grupo);
		if(gru==null) 
			throw new Exception("Individuo nao esta no grupo.");
		gru.remover(in);
		repositorio.salvarObjetos();
	}
	
	public static void removerParticipante(String nome_participante) throws Exception {
	    nome_participante = nome_participante.trim();
	    Participante participante = repositorio.localizarParticipante(nome_participante);
	    if (participante == null) {
	        throw new Exception("Participante nao encontrado: " + nome_participante);
	    }
	    if (participante instanceof Individual) {
	        Individual individuo = (Individual) participante;
	        for (Grupo grupo : repositorio.getGrupos()) {
	            if (grupo.localizar(individuo.getNome()) != null) {
	                grupo.remover(individuo);
	            }
	        }
	    } else if (participante instanceof Grupo) {
	        Grupo grupo = (Grupo) participante;
	        for (Individual individuo : grupo.getIndividuos()) {
	            individuo.removerGrupo(grupo);
	        }
	    }
	    repositorio.removerParticipante(participante);
	    repositorio.salvarObjetos();
	}

	
	public static void apagarMensagem(String nomeindividuo, int id) throws  Exception{
		nomeindividuo = nomeindividuo.trim();
		Individual emitente = (Individual) repositorio.localizarParticipante(nomeindividuo);	
		if(emitente == null) 
			throw new Exception("apagar mensagem - nome nao existe:" + nomeindividuo);
		
		Mensagem m = emitente.localizarMensagemEnviada(id);
		if(m == null)
			throw new Exception("apagar mensagem - mensagem nao pertence a este individuo:" + id);
		
		emitente.removerMensagemEnviada(m);
		Participante destinatario = m.getDestinatario();
		destinatario.removerMensagemRecebida(m);
		repositorio.removerMensagem(m);	

		if(destinatario instanceof Grupo) {
			destinatario.removerMensagemRecebida(m);
            Grupo grupo = (Grupo) destinatario;
            for (Individual membro : grupo.getIndividuos()) {
            	if (!(membro.equals(emitente))) {
            		emitente.removerMensagemEnviada(m);
            	    destinatario.removerMensagemRecebida(m);
            	}
            }
		}		
	}
	
	public static Individual validarIndividuo(String nome_individuo, String senha) throws Exception {
		nome_individuo = nome_individuo.trim();
		if (nome_individuo.isEmpty() || senha.isEmpty()) {
			throw new IllegalArgumentException("Nome ou senha nao podem estar vazios.");
		}
		Individual ind = (Individual) repositorio.localizarParticipante(nome_individuo);
		if (ind == null) {
			return null;
		}
		if(ind.getNome().equals(nome_individuo) && ind.getSenha().equals(senha)) { return ind; } 
		else { throw new Exception("Usuario ou senha invalidos."); }
	}
	
	public static ArrayList<Mensagem> obterConversa(String nome_participante1, String nome_participante2) throws Exception {
		if (nome_participante1.isEmpty() || nome_participante2.isEmpty()) {
			throw new IllegalArgumentException("Nomes nao podem estar vazios.");
		}
		Individual participante1 = (Individual) repositorio.localizarParticipante(nome_participante1);
		if (participante1 == null) {
			throw new Exception("Remetente desconhecido: "+nome_participante1);
		}
		Participante participante2 = repositorio.localizarParticipante(nome_participante2);
		if (participante2 == null) {
			throw new Exception("Destinatario desconhecido: " + nome_participante2);
		}
		ArrayList<Mensagem> mensagens = repositorio.obterConversaSalva(participante1,participante2);
		return mensagens;
	}
	
	public static ArrayList<Mensagem> listarMensagensEnviadas(String nome_participante) throws Exception{
		nome_participante = nome_participante.trim();
		if(repositorio.localizarParticipante(nome_participante) != null) {
			Participante participante = repositorio.localizarParticipante(nome_participante);
			return participante.getEnviadas();
		}
		throw new Exception("Esse usuario nao existe: " + nome_participante);	
	}
	
	public static ArrayList<Mensagem>listarMensagens(){
		ArrayList<Mensagem> mensagens = new ArrayList<>();
		for(Mensagem mensagem : repositorio.getMensagens()) {
			mensagens.add(mensagem);
		}
		return mensagens;
	}
	
	public static ArrayList<Mensagem> listarMensagensRecebidas(String nome_participante) throws Exception{
		nome_participante = nome_participante.trim();
		if(repositorio.localizarParticipante(nome_participante) != null) {
			Participante participante = repositorio.localizarParticipante(nome_participante);
			return participante.getRecebidas();
		}
		throw new Exception("Esse usuario nao existe " + nome_participante);	
	}
		
	public static ArrayList<Individual> listarIndividuos() {
		ArrayList<Individual> individuos = new ArrayList<>();
	    for (Participante participante : repositorio.getParticipantes().values()) {
	    	if(participante instanceof Individual ind) {
	    		individuos.add(ind);	    			    	
	    	}
	    }
	    return individuos;
	}
	
	public static ArrayList<Grupo> listarGrupos() {
	    ArrayList<Grupo> grupos = new ArrayList<>();
	    for (Participante participante : repositorio.getParticipantes().values()) {
	        if (participante instanceof Grupo grupo) {
	        	grupos.add(grupo);
	        }
	    }
	    return grupos;
	}
	
	public static ArrayList<Mensagem> espionarMensagens(String nomeAdmin, String termo) throws Exception {
	    Participante participante = repositorio.localizarParticipante(nomeAdmin);
	    if (!(participante instanceof Individual) || !((Individual) participante).isAdministrador()) {
	        throw new Exception("O participante nao eh um administrador valido.");
	    }
	    ArrayList<Mensagem> mensagens = new ArrayList<>();
	    ArrayList<Mensagem> todasAsMensagens = repositorio.getMensagens();
	    for (Mensagem mensagem : todasAsMensagens) {
	        if (termo.isEmpty() || mensagem.getTexto().contains(termo)) {
	            mensagens.add(mensagem);
	        }
	    }
	    return mensagens;
	}
	
	public static ArrayList <String> ausentes(String nomeAdmin) throws Exception{
		Participante participante = repositorio.localizarParticipante(nomeAdmin);
	    
	    if (!(participante instanceof Individual) || !((Individual) participante).isAdministrador()) {
	        throw new Exception("O participante nao eh um administrador valido.");
	    }
	    
	    ArrayList<String> participantesAusentes = new ArrayList<>();
	    TreeMap<String, Participante> todosOsParticipantes = repositorio.getParticipantes();
	    
	    for (Participante participante2 : todosOsParticipantes.values()) {
	        if (participante2.getEnviadas().isEmpty()) {
	            participantesAusentes.add(participante2.getNome());
	        }
	    }
	    return participantesAusentes;
	}
}
