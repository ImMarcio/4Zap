/*
 * @authors Allan Amancio and Marcio Jose
 * 
 * Participante class
 */
package modelo;

import java.util.ArrayList;

public class Individual extends Participante {
	private String senha;
    private boolean administrador; 
	private ArrayList<Grupo> grupos = new ArrayList<>();
    
    public Individual(String nome, String senha, boolean administrador) {
    	super(nome);
		this.senha = senha;
		this.administrador = administrador;
	}
    
    // Getters e Setters
	public String getSenha() {return senha;}
	public void setSenha(String senha) {this.senha = senha;}
	public boolean isAdministrador() {return administrador;}
	public Boolean getAdministrador() {
		if (administrador == true) {
			return administrador;
		};
		return null;
	}
	public void setAdministrador(boolean administrador) {this.administrador = administrador;}
	public void adicionarGrupo(Grupo grupo) {
		grupos.add(grupo);
	}
	public ArrayList<Grupo> getGrupos() {return grupos;}
    public Grupo getGrupo(String nome_grupo) {
    	for (Grupo g : grupos) {
    		if (g.getNome() == nome_grupo)
    			return g;
    	}
    	return null;
    }
    
    @Override
    public String toString(){
       String textoDoGrupo = "Grupos: ";
       if(grupos.isEmpty()) {
    	   textoDoGrupo += "sem grupos";
    	   return  super.toString() + textoDoGrupo + "\n";
       }
       for(Grupo grupo : grupos) {
    	   textoDoGrupo += ""+ grupo.getNome();
       }
       return  super.toString() + textoDoGrupo + "\n";
    }
}
