package repositorio;

import java.util.TreeMap;

import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;

public class Repositorio {
    private TreeMap<String,Participante> participantes = new TreeMap<>();
    private TreeMap<Integer,Mensagem> mensagens = new TreeMap<>();

    public Repositorio(){
        //carregarObjetos(); //ler dados dos arquivos
    }
    
    // Adicionar um índividuo novo
	public void adicionarIndividuo(Individual i){
		this.participantes.put(i.getNome(), i);
	}
	// Localizar um individual no TreeMap participantes
	public Individual localizarIndividuo(String nome){
		return (Individual) participantes.get(nome);
	}
}
