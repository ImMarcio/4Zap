/*
 * @authors Allan Amancio and Marcio Jose
 * 
 * Participante class
 */
package modelo;

import java.util.ArrayList;


public class Grupo extends Participante {
	private ArrayList<Individual> individuos = new ArrayList<>();
	
    public Grupo(String nome_grupo) {
		super(nome_grupo);
	}
    
    // Getters e Setters
	public ArrayList<Individual> getIndividuos(){return individuos;}

	public void adicionar(Individual individuo) throws Exception{
		if(individuo!= null) {
			individuos.add(individuo);
			individuo.adicionarGrupo(this);
			
		}
		else {throw new Exception("Individuo nulo!");}
	}
	
	public void remover(Individual individuo) throws Exception {
		if(individuos.remove(individuo) == false) {
			throw new Exception("Esse individuo nao esta no grupo!");
		}
		individuo.sairGrupo(this);
	}
	
	public Individual localizar(String nome){
		for(Individual individuo : individuos){
			if(individuo.getNome().equals(nome))
				return individuo;
		}
		return null;
	}
	
	public int getTotalIndividuos() {return individuos.size();}
	
	@Override
	public String toString() {
	    StringBuilder texto = new StringBuilder();
	    texto.append("Nome: ").append(super.getNome()).append("\n");
	    if (individuos.isEmpty()) {
	        texto.append("Membros: nenhum membro");
	    } else {
	        texto.append("Membros:");
	        for (Individual individuo : individuos) {
	            texto.append(" ").append(individuo.getNome());
	        }
	    }
	    
	    texto.append("\nMensagens enviadas: ");
	    int cont = 0;
	    for (Mensagem mensagem : super.getEnviadas()) {
	        texto.append("\n"+mensagem.toString());
	    }
	    if(cont==0) {texto.append("nenhuma mensagem");}	    
	    texto.append("\nMensagens recebidas: ");
	    cont = 0;
	    for (Mensagem mensagem : super.getRecebidas()) {
	        texto.append("\n"+mensagem.toString());
	        cont++;
	    }
	    if (cont==0) {texto.append("nenhuma mensagem");}
	    texto.append("\n");
	    return texto.toString();
	}	
}
