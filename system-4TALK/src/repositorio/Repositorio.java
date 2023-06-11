package repositorio;

import java.util.TreeMap;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;

public class Repositorio {
    private TreeMap<String,Participante> participantes = new TreeMap<>();
    private TreeMap<Integer,Mensagem> mensagens = new TreeMap<>();

    public Repositorio(){
        //carregarObjetos(); //ler dados dos arquivos
    }
    
    // Getters e Setters
    public TreeMap<String, Participante> getParticipantes() {return participantes;}
	public TreeMap<Integer, Mensagem> getMensagens() {return mensagens;}

	// Adicionar um índividuo novo no TreeMap participantes
	public void adicionarIndividuo(Individual i){
		this.participantes.put(i.getNome(), i);
	}
	// Adicionar um grupo novo no TreeMap participantes
	public void adicionarGrupo(Grupo g) {
		this.participantes.put(g.getNome(), g);
	}
	// Localizar um individual no TreeMap participantes
	public Individual localizarIndividuo(String nome){
		return (Individual) participantes.get(nome);
	}
	// Localizar um grupo no TreeMap participantes
	public Grupo localizarGrupo(String nome_grupo) {
		return (Grupo) participantes.get(nome_grupo);
	}
	// Gerador de ID para uma mensagem
	public int gerarId() {
		if (mensagens.isEmpty())
			return 1;
		else {
			Mensagem ultima_mensagem = mensagens.get(mensagens.size()-1);
			return ultima_mensagem.getId() + 1;
		}
	}
}