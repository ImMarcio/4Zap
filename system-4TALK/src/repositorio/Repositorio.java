package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.time.format.DateTimeFormatter;
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
    private ArrayList<Mensagem> mensagens = new ArrayList<>();

    public Repositorio() {
        carregarObjetos(); //ler dados dos arquivos
        Individual admin = (Individual) participantes.put("admin", new Individual("admin", "admin", true));
    } 

    // Getters e Setters
    public TreeMap<String, Participante> getParticipantes() {return participantes;}
	public ArrayList<Mensagem> getMensagens() {return mensagens;}
	public int getTotalParticipantes(){ return participantes.size();}
	public int getTotalMensagens(){return mensagens.size();}
	
	public Participante localizarParticipante(String nome){
		return (Participante) participantes.get(nome);
	}
	public Individual localizarIndividuo(String nome) {
		for(Participante participante : participantes.values()) {
			if(participante instanceof Individual ind) {
				return ind;
			}
		}
		return null;
	}
	
	public Grupo localizarGrupo(String nome) {
		for(Participante participante : participantes.values()) {
			if(participante instanceof Grupo grup) {
				return grup;
			}
		}
		return null;
	}
	
	public Mensagem localizarMensagem(int id){
		for(Mensagem msg : getMensagens()){
			if(msg.getId()==id)
				return msg;
		}
		return null;
	}

	public void adicionarIndividuo(Individual i){
		this.participantes.put(i.getNome(), i);
	}
	
	public void adicionarGrupo(Grupo g) {
		this.participantes.put(g.getNome(), g);
	}
	
	public void adicionarMensagem(Mensagem msg) {
		this.mensagens.add(msg);
	}
	
	public void adicionarMensagemEnviada(Participante remetente, Mensagem mensagem) {
		if(mensagem.getEmitente() instanceof Grupo) {
			this.adicionarMensagem(mensagem);
		}
	    remetente.adicionarMensagemEnviada(mensagem);
	}
	
	public void adicionarMensagemRecebida(Participante destinatario, Mensagem mensagem) {
	    destinatario.adicionarMensagemRecebida(mensagem);
	}
	
	public void removerMensagem(Mensagem mensagem) {
		for(Mensagem msg : mensagens) {	
				mensagens.removeIf(mensagemAtual -> (mensagemAtual.getId() == mensagem.getId()));
		}
        mensagens.remove(mensagem);
    }
	
	public void removerMensagemEnviada(Participante remetente, Mensagem mensagem) {
	    remetente.removerMensagemEnviada(mensagem);
	}

	public void removerMensagemRecebida(Participante destinatario, Mensagem mensagem) {
	    destinatario.removerMensagemRecebida(mensagem);
	}
	
	public ArrayList<Mensagem> obterConversaSalva(Participante participante1, Participante participante2) {
		ArrayList<Mensagem> conversa = new ArrayList<>();
	    for (Mensagem mensagem : mensagens) {
	        if (mensagem.getEmitente().equals(participante1) && mensagem.getDestinatario().equals(participante2)) {
	            conversa.add(mensagem);}
	        if (mensagem.getEmitente().equals(participante2) && mensagem.getDestinatario().equals(participante1)) {
	            conversa.add(mensagem);}
	    }
	    Collections.sort(conversa, Comparator.comparing(Mensagem::getId));
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
	
	public void carregarObjetos(){
		try {
			File arquivoDeIndividuo = new File( new File(".\\individual.csv").getCanonicalPath() ) ; 
			File arquivoDoGrupo = new File( new File(".\\grupo.csv").getCanonicalPath() ) ; 
			File arquivoDeMensagem = new File( new File(".\\mensagem.csv").getCanonicalPath() ) ;
			if (!arquivoDeIndividuo.exists() || !arquivoDoGrupo.exists() || arquivoDeMensagem.exists()  ) {
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
		Scanner scan = null;
		
		try {
			String nomeindividuo,senha;
			boolean admistrador;
			Individual individuo;
			File f = new File( new File(".\\individual.csv").getCanonicalPath() );
			scan = new Scanner(f); // pasta do objeto
			while(scan.hasNextLine()) {
				linha = scan.nextLine().trim();		
				partes = linha.split(";");	
				nomeindividuo = partes[0];
				senha = partes[1];
				admistrador = Boolean.parseBoolean(partes[3]);
				individuo = new Individual(nomeindividuo, senha, admistrador);
				this.adicionarIndividuo(individuo);
				
			}
			scan.close();
			
		}
		catch(Exception ex) {
			throw new RuntimeException("leitura arquivo de individuos:"+ex.getMessage());
		}
		try {
			String nomegrupo;
			Grupo grupo;
			File f = new File( new File(".\\grupo.csv").getCanonicalPath() );
			scan = new Scanner(f); // pasta do objeto
			while(scan.hasNextLine()) {
				linha = scan.nextLine().trim();		
				partes = linha.split(";");
				nomegrupo = partes[0];
				grupo = new Grupo(nomegrupo);
				if(partes.length>1) {
					for(int i = 1; i < partes.length; i++) {
						Participante participanteAtual = this.localizarParticipante(partes[i]);
						if( participanteAtual instanceof Individual indi) {
							grupo.adicionar(indi);
						}
					}
				}

				this.adicionarGrupo(grupo);	
			}	
			scan.close();
				
			}
		catch(Exception ex) {
			throw new RuntimeException("leitura arquivo de grupo:"+ex.getMessage());
		}
		try {
			int id;
			String texto;
			Individual emitente;
			Participante destinatario;
			Mensagem mensagem;
			File f = new File( new File(".\\mensagem.csv").getCanonicalPath() );
			scan = new Scanner(f); // pasta do objeto
			while(scan.hasNextLine()) {
				linha = scan.nextLine();
				partes = linha.split(";");
				id = Integer.parseInt(partes[0]);
				texto = partes[1];
				emitente = (Individual) this.localizarParticipante(partes[2]);
				destinatario = this.localizarParticipante(partes[3]);
				mensagem = new Mensagem(id,texto, emitente, destinatario);
				this.adicionarMensagem(mensagem);
			}
			scan.close();
		}
		catch(Exception ex) {
			throw new RuntimeException("leitura arquivo de mensagens:"+ex.getMessage());
		}
	}

	public void salvarObjetos(){
		FileWriter arquivo = null;
		try{
			File f = new File( new File(".\\individual.csv").getCanonicalPath())  ;
			arquivo = new FileWriter(f);
			for(Participante participante: participantes.values() ){
				if (participante instanceof Individual) {
					Individual individual = (Individual) participante;
					arquivo.write( individual.getNome()+";" + individual.getSenha() + ";" + individual.isAdministrador() + "\n");
				}
			}	
			arquivo.close();		
		}
		catch(Exception ex) {
			throw new RuntimeException("problema na criacao do arquivo  individual "+ex.getMessage());
		}
		try{
			File f = new File( new File(".\\grupo.csv").getCanonicalPath())  ;
			arquivo = new FileWriter(f);
				
		
			for(Participante participante: participantes.values() ){	
				String individuos = "";
				if (participante instanceof Grupo) {
					ArrayList<Individual> listaDeIndividuos = ((Grupo) participante).getIndividuos();
					for(int i = 0; i < listaDeIndividuos.size(); i++) {
						individuos += listaDeIndividuos.get(i).getNome() + ";";
					};	
					arquivo.write( participante.getNome() + ";" + individuos +  "\n");	
				}
					
			}	
			arquivo.close();	
		}
		catch(Exception ex) {
			throw new RuntimeException("problema na criacao do arquivo  grupo "+ex.getMessage());
		}
		try{
			File f = new File( new File(".\\mensagem.csv").getCanonicalPath())  ;
			arquivo = new FileWriter(f);
			for(Mensagem mensagem : mensagens){
		    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
		        String datahora_formatada = mensagem.getDataHora().format(formatter);
				arquivo.write( mensagem.getId() + ";"+ mensagem.getEmitente().getNome() + ";" + mensagem.getDestinatario().getNome() + ";" + datahora_formatada +  "\n");		
				}	
			arquivo.close();	
		}
		catch(Exception ex) {
			throw new RuntimeException("problema na criacao do arquivo  mensagem "+ex.getMessage());
		}	
	}
}
