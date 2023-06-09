package modelo;

import java.util.ArrayList;

public class Individual {
   
	private String senha;
    private boolean admistrador; 
    private ArrayList<Mensagem> enviadas = new ArrayList<>();
    
    public Individual(String senha, boolean admistrador) {
		super();
		this.senha = senha;
		this.admistrador = admistrador;

	}
}
