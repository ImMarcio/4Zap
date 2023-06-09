package modelo;

import java.util.ArrayList;

public class Individual extends Participante {
   
	private String senha;
    private boolean admistrador; 
    private ArrayList<Mensagem> enviadas = new ArrayList<>();
    
    public Individual(String nome, String senha, boolean admistrador) {
    	super(nome);
		this.senha = senha;
		this.admistrador = admistrador;

	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isAdmistrador() {
		return admistrador;
	}

	public void setAdmistrador(boolean admistrador) {
		this.admistrador = admistrador;
	}

	public ArrayList<Mensagem> getEnviadas() {
		return enviadas;
	}

	public void setEnviadas(ArrayList<Mensagem> enviadas) {
		this.enviadas = enviadas;
	}
}
