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
		}
		else {throw new Exception("Individuo nao existe!");}
	}
	// Talvez vá pro repositorio
	public void remover(Individual individuo) throws Exception {
		if(individuos.remove(individuo) == false) {
			throw new Exception("Esse individuo nao esta no grupo!");
		}
	}
	// Talvez repositório
	public Individual localizar(String nome){
		for(Individual individuo : individuos){
			if(individuo.getNome().equals(nome))
				return individuo;
		}
		return null;
	}
	
	// talvez pro Repositorio
	public int getTotalIndividuos() {return individuos.size();}
	
	@Override
	public String toString() {
		String texto = "" ;
		if (individuos.isEmpty())
			texto += "vazio";
		else
			texto = "Membros:";
			for(Individual individuo: individuos) 
				texto += " " + individuo.getNome();
		return texto ;
	}	
}