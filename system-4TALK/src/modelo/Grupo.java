package modelo;

import java.util.ArrayList;

public class Grupo {
	private ArrayList<Individual> individuos = new ArrayList<>();
	
    public Grupo(ArrayList<Individual> individuos) {
		super();
		this.individuos = individuos;
	}
    public Grupo() {
		
   	}

	
	
	public void adicionar(Individual individuo) {
		individuos.add(individuo);
	}
	
	public void remover(Individual individuo) {
		individuos.remove(individuo);
	}
}
