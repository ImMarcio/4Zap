package regras_negocio;

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
			throw new IllegalArgumentException("Nome ou senha nao podem estar vazios. Verifique se digitou algum campo vazio.");
		}
		Individual i = (Individual) repositorio.localizarParticipante(nome_individuo);
		nome_individuo = nome_individuo.trim();
		senha = senha.trim();
		if (i != null) {
			throw new Exception("Nao criou participante - individuo ja cadastrado: " + nome_individuo);
		}
		i = new Individual(nome_individuo, senha, false);
		repositorio.adicionarIndividuo(i);
		repositorio.salvarObjetos();
	}

	public static void criarAdministrador(String nome_administrador, String senha) throws Exception {
		nome_administrador = nome_administrador.trim();
		senha = senha.trim();
		if (nome_administrador.isEmpty() || senha.isEmpty()) {
			throw new IllegalArgumentException("Nome ou senha nao podem estar vazios. Verifique se digitou algum campo vazio.");
		}
		Individual i = (Individual) repositorio.localizarParticipante(nome_administrador);
		if (i != null && i.getNome().equals(nome_administrador)) {
			i.setAdministrador(true);
		}
		else {
			i = new Individual(nome_administrador, senha, true);
		}
		repositorio.adicionarIndividuo(i);
		repositorio.salvarObjetos();
	}
	
	public static void criarGrupo(String nome_grupo) throws Exception {
		nome_grupo = nome_grupo.trim();
		if (nome_grupo.isEmpty()) {
			throw new IllegalArgumentException("Nome do grupo nao pode estar vazio. Verifique se deixou nome vazio.");
		}
		Grupo g = (Grupo) repositorio.localizarParticipante(nome_grupo);
		if (g != null) {
			throw new Exception("Nao criou participante - grupo ja cadastrado: " + nome_grupo);
		}
		g = new Grupo(nome_grupo);
		repositorio.adicionarGrupo(g);
		repositorio.salvarObjetos();
	}
	
	public static void criarMensagem(String nome_remetente, String nome_destinatario, String texto) throws Exception {
		nome_remetente = nome_remetente.trim();
		nome_destinatario = nome_destinatario.trim();
		if (nome_remetente.isEmpty() || nome_destinatario.isEmpty() || texto.isEmpty() || texto.isBlank()) {
			throw new IllegalArgumentException("Nomes ou texto nao podem ser vazios. Verifique se deixou algum campo vazio.");
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
		Mensagem msg = new Mensagem(id,texto,remetente,destinatario);
		repositorio.adicionarMensagem(msg);
		
		if (destinatario instanceof Grupo) {
            Grupo grupo = (Grupo) destinatario;
            for (Participante membro : grupo.getIndividuos()) {
            	if (!(membro.equals(remetente))) {
            		repositorio.adicionarMensagemRecebida(membro, msg);
            	}
            }
            repositorio.adicionarMensagemEnviada(grupo,msg);
            repositorio.adicionarMensagemRecebida(grupo, msg);
        } else {
            repositorio.adicionarMensagemRecebida(destinatario, msg);
        }
        repositorio.adicionarMensagemEnviada(remetente, msg);
		repositorio.salvarObjetos();
	}
	
	public static void inserirGrupo(String nome_individuo, String nome_grupo) throws  Exception {
		nome_individuo = nome_individuo.trim();
		nome_grupo = nome_grupo.trim();
		Grupo gru = (Grupo) repositorio.localizarParticipante(nome_grupo);
		if(gru == null) 
			throw new Exception("Nao inseriu individuo - grupo inexistente: " + nome_grupo);
		Individual in = (Individual) repositorio.localizarParticipante(nome_individuo);
		if(in == null)
			throw new Exception("Nao inseriu individuo - individuo inexistente: " + nome_individuo);
		gru.adicionar(in); // Individuo adicionado ao grupo
		repositorio.salvarObjetos();
	}

	public static void removerGrupo(String nome_individuo, String nome_grupo) throws Exception {
		Individual in = (Individual) repositorio.localizarParticipante(nome_individuo);
		if (in==null)
			throw new Exception("Nao removeu o individuo - individuo inexistente: "+nome_individuo);
		Grupo gru = in.getGrupo(nome_grupo);
		if(gru==null) 
			throw new Exception("Individuo nao esta no grupo.");
		gru.remover(in);
		repositorio.salvarObjetos();
	}
	
	public static void apagarMensagem(String nome_individuo, int id) throws Exception {
		nome_individuo = nome_individuo.trim();
		if(nome_individuo.isEmpty()) {
			throw new IllegalArgumentException("Nome nao pode estar vazio.");
		}
		if (id <= 0) {
			throw new Exception("ID nao pode ser menor ou igual a 0.");
		}
		Individual in = (Individual) repositorio.localizarParticipante(nome_individuo);
		if (in == null) {
			throw new Exception("Individuo desconhecido.");
		}
		Mensagem msg = repositorio.localizarMensagem(id);
		if (msg == null) {
	        throw new Exception("Mensagem não encontrada.");
	    }
	    
	    if (msg.getEmitente().equals(in)) {
	    	Participante destinatario = msg.getDestinatario();
	        repositorio.removerMensagemEnviada(in, msg);

	        if (destinatario instanceof Grupo) {
	            Grupo grupo = (Grupo) destinatario;
	            for (Participante membro : grupo.getIndividuos()) {
	                repositorio.removerMensagemRecebida(membro, msg);
	            }
	        }
	        repositorio.removerMensagemRecebida(destinatario, msg);
	        repositorio.removerMensagemEnviada(destinatario, msg);
	        repositorio.removerMensagem(msg);
	    } else {
	        throw new Exception("A mensagem não foi emitida por esse indivíduo.");
	    }
	    repositorio.salvarObjetos();
	}
	
	public static boolean validarIndividuo(String nome_individuo, String senha) throws Exception {
		nome_individuo = nome_individuo.trim();
		if (nome_individuo.isEmpty() || senha.isEmpty()) {
			throw new IllegalArgumentException("Nome ou senha nao podem estar vazios.");
		}
		Individual i = (Individual) repositorio.localizarParticipante(nome_individuo);
		if (i == null) {
			return false;
		}
		if(i.getNome().equals(nome_individuo) && i.getSenha().equals(senha)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static ArrayList<Mensagem> obterConversa(String nome_participante1, String nome_participante2) throws Exception {
		if (nome_participante1.isEmpty() || nome_participante2.isEmpty()) {
			throw new IllegalArgumentException("Nomes nao podem ser vazios. Verifique se deixou algum campo vazio.");
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
		for(Mensagem mensagem : repositorio.getMensagens().values()) {
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
		
	public static ArrayList<String> listarIndividuos() {
	    ArrayList<String> individuos = new ArrayList<>();
	    for (Participante participante : repositorio.getParticipantes().values()) {
	    	if (participante instanceof Individual) {
		        StringBuilder individuoInfo = new StringBuilder();
		        individuoInfo.append("Nome=").append(participante.getNome()).append("\n");
		        if (!participante.getEnviadas().isEmpty()) {
		            individuoInfo.append("Mensagens Enviadas:\n");
		            for (Mensagem mensagem : participante.getEnviadas()) {
		                individuoInfo.append(" --> ").append(mensagem.toString()).append("\n");
		            }
		        } else {
		            individuoInfo.append("Sem mensagens enviadas").append("\n");
		        }
		        if (!participante.getRecebidas().isEmpty()) {
		            individuoInfo.append("Mensagens Recebidas:\n");
		            for (Mensagem mensagem : participante.getRecebidas()) {
		                individuoInfo.append(" --> ").append(mensagem.toString()).append("\n");
		            }
		        } else {
		            individuoInfo.append("Sem mensagens recebidas").append("\n");
		        }
		        if (participante instanceof Individual) {
		            Individual individuo = (Individual) participante;
		            if (!individuo.getGrupos().isEmpty()) {
		                individuoInfo.append("Grupos=\n");
		                for (Grupo grupo : individuo.getGrupos()) {
		                    individuoInfo.append(" --> ").append(grupo.getNome()).append("\n");
		                }
		            } else {
		                individuoInfo.append("Grupos: sem grupo").append("\n");
		            }
		        }
		        individuos.add(individuoInfo.toString());
	    	}
	    }
	    return individuos;
	}
	
	public static ArrayList<String> listarGrupos() {
		ArrayList<String> grupos = new ArrayList<>();

	    for (Participante participante : repositorio.getParticipantes().values()) {
	        if (participante instanceof Grupo grupo) {
	            StringBuilder grupoInfo = new StringBuilder();
	            grupoInfo.append("Nome=").append(grupo.getNome()).append("\n");

	            if (!grupo.getEnviadas().isEmpty()) {
	                grupoInfo.append("Mensagens Enviadas:\n");
	                for (Mensagem mensagem : grupo.getEnviadas()) {
	                    grupoInfo.append(" --> ").append(mensagem.toString()).append("\n");
	                }
	            } else {
	                grupoInfo.append("Sem mensagens enviadas").append("\n");
	            }
	            if (!grupo.getRecebidas().isEmpty()) {
	                grupoInfo.append("Mensagens Recebidas:\n");
	                for (Mensagem mensagem : grupo.getRecebidas()) {
	                    grupoInfo.append(" --> ").append(mensagem.toString()).append("\n");
	                }
	            } else {
	                grupoInfo.append("Sem mensagens recebidas").append("\n");
	            }
	            if (!grupo.getIndividuos().isEmpty()) {
	                grupoInfo.append("Indivíduos do Grupo:\n");
	                for (Participante membro : grupo.getIndividuos()) {
	                    grupoInfo.append(" --> ").append(membro.getNome()).append("\n");
	                }
	            } else {
	                grupoInfo.append("Indivíduos do Grupo: vazio\n");
	            }
	            grupos.add(grupoInfo.toString());
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
	    TreeMap<Integer, Mensagem> todasAsMensagens = repositorio.getMensagens();
	    for (Mensagem mensagem : todasAsMensagens.values()) {
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
	    	// instanceof para apenas individuos serem considerados "Ausentes" já que não faz sentido grupo mandar mensagem
	        if (participante2.getEnviadas().isEmpty() && participante2 instanceof Individual) {
	            participantesAusentes.add(participante2.getNome());
	        }
	    }
	    return participantesAusentes;
	}
}
