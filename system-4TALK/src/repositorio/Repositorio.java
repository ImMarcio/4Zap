/*
 * @authors Allan Amancio and Marcio Jose
 * 
 * Repositorio class
 */
package repositorio;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;
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
        participantes.put("admin", new Individual("admin", "admin", true));
    } 

    // Getters e Setters
    public TreeMap<String, Participante> getParticipantes() {return participantes;}
    public ArrayList<Individual> getIndividuos(){
    	ArrayList<Individual> individuos =  new ArrayList<Individual>();
    	for(Participante participantes : participantes.values()) {
    		if(participantes instanceof Individual ind) {
    			individuos.add(ind);
    		}
    	}
    	return individuos;
    }
    public ArrayList<Grupo> getGrupos(){
    	ArrayList<Grupo> grupos =  new ArrayList<Grupo>();
    	for(Participante participantes : participantes.values()) {
    		if(participantes instanceof Grupo grup) {
    			grupos.add(grup);
    		}
    	}
    	return grupos;
    }
	public ArrayList<Mensagem> getMensagens() {return mensagens;}
	public int getTotalParticipantes(){ return participantes.size();}
	public int getTotalMensagens(){return mensagens.size();}
	
	public Participante localizarParticipante(String nome){
		return participantes.get(nome);
	}
	public Individual localizarIndividuo(String nome) {
		for(Participante participante : participantes.values()) {
			if(participante instanceof Individual ind && ind.getNome().equals(nome)) {
				return ind;
			}
		}
		return null;
	}
	
	public Grupo localizarGrupo(String nome) {
		for(Participante participante : participantes.values()) {
			if(participante instanceof Grupo grup && grup.getNome().equals(nome)) {
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
		mensagens.removeIf(mensagemAtual -> (mensagemAtual.getId() == mensagem.getId()));
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
	

	
	public void carregarObjetos()  	{
		// carregar para o repositorio os objetos dos arquivos csv
		try {
			//caso os arquivos nao existam, serao criados vazios
			File f1 = new File( new File("./data/mensagens.csv").getCanonicalPath() ) ; 
			File f2 = new File( new File("./data/individual.csv").getCanonicalPath() ) ; 
			File f3 = new File( new File("./data/grupos.csv").getCanonicalPath() ) ; 
			if (!f1.exists() || !f2.exists() || !f3.exists() ) {
				//System.out.println("criando arquivo .csv vazio");
				FileWriter arquivo1 = new FileWriter(f1); arquivo1.close();
				FileWriter arquivo2 = new FileWriter(f2); arquivo2.close();
				FileWriter arquivo3 = new FileWriter(f3); arquivo3.close();
				return;
			}
		}
		catch(Exception ex)		{
			throw new RuntimeException("criacao dos arquivos vazios:"+ex.getMessage());
		}

		String linha;	
		String[] partes;	

		try	{
			String nome,senha,administrador;
			File f = new File( new File("./data/individual.csv").getCanonicalPath())  ;
			Scanner arquivo1 = new Scanner(f);	 //  pasta do projeto
			while(arquivo1.hasNextLine()) 	{
				linha = arquivo1.nextLine().trim();	
				partes = linha.split(";");
				nome = partes[0];
				senha = partes[1];
				administrador = partes[2];
				Individual ind = new Individual(nome,senha,Boolean.parseBoolean(administrador));
				this.adicionarIndividuo(ind);
				
			}
			arquivo1.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de individuos:"+ex.getMessage());
		}

		try	{
			String nome;
			Grupo grupo;
			Individual individuo;
			File f = new File( new File("./data/grupos.csv").getCanonicalPath())  ;
			Scanner arquivo2 = new Scanner(f);	 //  pasta do projeto
			
			while(arquivo2.hasNextLine()) 	{
				linha = arquivo2.nextLine().trim();	
				partes = linha.split(";");
				//System.out.println(Arrays.toString(partes));
				nome = partes[0];
				grupo = new Grupo(nome);
				if(partes.length>1)
					for(int i=1; i< partes.length; i++) {
						individuo = this.localizarIndividuo(partes[i]);						
						grupo.adicionar(individuo);
						individuo.adicionarGrupo(grupo);
					}
				this.adicionarGrupo(grupo);
				
			}
			arquivo2.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de grupos:"+ex.getMessage());
		}


		try	{
			String nomeemitente, nomedestinatario,texto;
			int id;
			LocalDateTime datahora;
			Mensagem m;
			Participante emitente,destinatario;
			File f = new File( new File("./data/mensagens.csv").getCanonicalPath() )  ;
			Scanner arquivo3 = new Scanner(f);	 //  pasta do projeto
			while(arquivo3.hasNextLine()) 	{
				linha = arquivo3.nextLine().trim();		
				partes = linha.split(";");	
				id = Integer.parseInt(partes[0]);
				texto = partes[1];
				nomeemitente = partes[2];
				nomedestinatario = partes[3];			
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
				datahora = LocalDateTime.parse(partes[4], formatter );
				emitente = this.localizarParticipante(nomeemitente);
				destinatario = this.localizarParticipante(nomedestinatario);				
				m = new Mensagem(id,texto,emitente,destinatario, datahora);
				this.adicionarMensagem(m);
				emitente.adicionarMensagemEnviada(m);
				destinatario.adicionarMensagemRecebida(m);
				
			} 
			arquivo3.close();
		}
		catch(Exception ex)		{
			throw new RuntimeException("leitura arquivo de mensagens:"+ex.getMessage());
		}
	}


	public void	salvarObjetos()  {
		//gravar nos arquivos csv os objetos que estão no repositório
		try	{
			File f = new File( new File("./data/mensagens.csv").getCanonicalPath())  ;
			FileWriter arquivo1 = new FileWriter(f); 
			for(Mensagem m : mensagens) 	{
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS");
		        String dataString = formatter.format(m.getDataHora());
				arquivo1.write(	m.getId()+";"+
						m.getTexto()+";"+
						m.getEmitente().getNome()+";"+
						m.getDestinatario().getNome()+";"+
						dataString+"\n");	
			} 
			arquivo1.close();
		}
		catch(Exception e){
			throw new RuntimeException("problema na criação do arquivo  mensagens "+e.getMessage());
		}

		try	{
			File f = new File( new File(".\\individual.csv").getCanonicalPath())  ;
			FileWriter arquivo2 = new FileWriter(f) ; 
			for(Individual ind : this.getIndividuos()) {
				arquivo2.write(ind.getNome() +";"+ ind.getSenha() +";"+ ind.getAdministrador() +"\n");	
			} 
			arquivo2.close();
		}
		catch (Exception e) {
			throw new RuntimeException("problema na criação do arquivo  individuos "+e.getMessage());
		}

		try	{
			File f = new File( new File("./data/grupos.csv").getCanonicalPath())  ;
			FileWriter arquivo3 = new FileWriter(f) ; 
			for(Grupo g : this.getGrupos()) {
				String texto="";
				for(Individual ind : g.getIndividuos())
					texto += ";" + ind.getNome();
				arquivo3.write(g.getNome() + texto + "\n");	
			} 
			arquivo3.close();
		}
		catch (Exception e) {
			throw new RuntimeException("problema na criação do arquivo  grupos "+e.getMessage());
		}
	}

	
	
	
	
	
	
	
	
}
