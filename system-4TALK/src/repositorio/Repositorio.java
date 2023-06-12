package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.TreeMap;

import modelo.Grupo;
import modelo.Individual;
import modelo.Mensagem;
import modelo.Participante;

public class Repositorio {
    private TreeMap<String,Participante> participantes = new TreeMap<>();
    private TreeMap<Integer,Mensagem> mensagens = new TreeMap<>();

    public Repositorio() {
        carregarObjetos(); //ler dados dos arquivos
    }

    // Getters e Setters
    public TreeMap<String, Participante> getParticipantes() {return participantes;}
	public TreeMap<Integer, Mensagem> getMensagens() {return mensagens;}

	public void adicionarIndividuo(Individual i){
		this.participantes.put(i.getNome(), i);
	}
	
	public void adicionarGrupo(Grupo g) {
		this.participantes.put(g.getNome(), g);
	}
	
	public void adicionarMensagem(Mensagem msg) {
		this.mensagens.put(msg.getId(), msg);
		
		msg.getEmitente().adicionarMensagemEnviada(msg);
	    msg.getDestinatario().adicionarMensagemRecebida(msg);
	}
	// Versão para grupos
	public void adicionarMensagemRecebida(Participante destinatario, Mensagem mensagem) {
	    destinatario.adicionarMensagemRecebida(mensagem);
	}
	// Versão para grupos
	public void adicionarMensagemEnviada(Individual remetente, Mensagem mensagem) {
	    remetente.adicionarMensagemEnviada(mensagem);
	}

	public Individual localizarIndividuo(String nome){
		return (Individual) participantes.get(nome);
	}

	public Grupo localizarGrupo(String nome_grupo) {
		return (Grupo) participantes.get(nome_grupo);
	}
	
	public Mensagem localizarMensagem(int id){
		for(Mensagem msg : mensagens.values()){
			if(msg.getId()==id)
				return msg;
		}
		return null;
	}
	
	public ArrayList<Mensagem> obterConversaSalva(Individual remetente, Participante destinatario) {
		ArrayList<Mensagem> conversa = new ArrayList<>();
	    for (Mensagem mensagem : mensagens.values()) {
	        if (mensagem.getEmitente().equals(remetente) && mensagem.getDestinatario().equals(destinatario)) {
	            conversa.add(mensagem);}
	    }
	    Collections.sort(conversa, Comparator.comparing(Mensagem::getDataHora));
	    return conversa;
	}
	
	public int gerarId() {
		if (mensagens.isEmpty())
			return 1;
		else {
			Mensagem ultima_mensagem = mensagens.get(mensagens.size()-1);
			return ultima_mensagem.getId() + 1;
		}
	}
	
	public void removerMensagemEnviada(Individual remetente, Mensagem mensagem) {
	    remetente.removerMensagemEnviada(mensagem);
	}

	public void removerMensagemRecebida(Participante destinatario, Mensagem mensagem) {
	    destinatario.removerMensagemRecebida(mensagem);
	}

	public void removerMensagem(Mensagem mensagem) {
		Integer id = mensagem.getId();
        mensagens.remove(id);
    }
	
	public void carregarObjetos(){
		try {
			File arquivoDeIndividuo = new File( new File(".\\individual.csv").getCanonicalPath() ) ; 
			File arquivoDoGrupo = new File( new File(".\\grupo.csv").getCanonicalPath() ) ; 
			File arquivoDeMensagem = new File( new File(".\\mensagem.csv").getCanonicalPath() ) ;
			if (!arquivoDeIndividuo.exists() || !arquivoDoGrupo.exists() || arquivoDeMensagem.exists()  ) {
				//System.out.println("criando arquivo .csv vazio");
				FileWriter arquivo1 = new FileWriter(arquivoDeIndividuo); arquivo1.close();
				FileWriter arquivo2 = new FileWriter(arquivoDoGrupo); arquivo2.close();
				FileWriter arquivo3 = new FileWriter(arquivoDeMensagem); arquivo3.close();
				return;
			}
		}
		catch(Exception ex)		{
			throw new RuntimeException("criacao dos arquivos vazios:"+ex.getMessage());
		}
		String linha;
		String[] partes;
		String nomeindividuo,senha,nomegrupo;
		//ArrayList<String> nomeDosParticipantes = null;
		boolean admistrador;
		Individual individuo;
		Grupo grupo;
		Scanner arquivo = null;
		
		try {
			File f = new File( new File(".\\individual.csv").getCanonicalPath() );
			arquivo = new Scanner(f); // pasta do objeto
			while(arquivo.hasNextLine()) {
				linha = arquivo.nextLine().trim();		
				partes = linha.split(";");	
				nomeindividuo = partes[0];
				senha = partes[1];
				admistrador = Boolean.getBoolean(partes[3]);
			
				individuo = new Individual(nomeindividuo, senha, admistrador);
				this.adicionarIndividuo(individuo);
				
			}
			arquivo.close();
			
		}
		catch(Exception ex) {
			throw new RuntimeException("leitura arquivo de individuos:"+ex.getMessage());
		}
		try {
			File f = new File( new File(".\\grupo.csv").getCanonicalPath() );
			arquivo = new Scanner(f); // pasta do objeto
			while(arquivo.hasNextLine()) {
				linha = arquivo.nextLine().trim();		
				partes = linha.split(";");
				nomegrupo = partes[0];
				grupo = new Grupo(nomegrupo);
				for(int i = 1; i < partes.length; i++) {
					grupo.adicionar(this.localizarIndividuo(partes[i]));
				}
				this.adicionarGrupo(grupo);
			}
		}
		catch(Exception e) {
			
		}
	}
	
//	linha = arquivo.nextLine().trim();		
//	partes = linha.split(";");
//	nomegrupo = partes[0];
//	nomeDosParticipantes =  "Participantes: " + '\n';
//	for(int i = 1; i < partes.length; i++) {
//		nomeDosParticipantes =  partes[i] + '\n';
//	}
//	
//		
}
