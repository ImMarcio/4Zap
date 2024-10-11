/*
 * @authors Allan Amancio and Marcio Jose
 * 
 * Participante class
 */
package modelo;

import java.util.ArrayList;

public class Participante {
        private String nome;
        private ArrayList<Mensagem> recebidas = new ArrayList<>();
        private ArrayList<Mensagem> enviadas = new ArrayList<>();
        
        public Participante(String nome){
            this.nome = nome;
        }
        
        // Getters e Setters
        public String getNome(){ return this.nome; }
        public void setNome(String nome){this.nome = nome;}
        public ArrayList<Mensagem> getRecebidas(){return recebidas; }
        public ArrayList<Mensagem> getEnviadas() {return enviadas;}
        
        public void adicionarMensagemEnviada(Mensagem mensagem) {enviadas.add(mensagem);}       
        public void removerMensagemEnviada(Mensagem mensagem) {enviadas.remove(mensagem);}
        public void adicionarMensagemRecebida(Mensagem mensagem) {recebidas.add(mensagem);}
        public void removerMensagemRecebida(Mensagem mensagem) {recebidas.remove(mensagem);}
        
        public Mensagem localizarMensagemEnviada(int id) {
    		for (Mensagem m : enviadas) {
    			if(m.getId() == id)
    				return m;
    		}
    		return null;
    	}
        
        public Mensagem localizarMensagemRecebida(int id) {
    		for (Mensagem m : recebidas) {
    			if(m.getId() == id)
    				return m;
    		}
    		return null;
    	}
        
       @Override
        public String toString(){
    	   StringBuilder texto = new StringBuilder();
    	   texto.append("Nome: ").append(nome).append("\n");
    	   
    	   texto.append("Mensagens enviadas: ");
    	   if (enviadas.isEmpty()) {
    		   texto.append("nenhuma mensagem\n");
    	   }else {
    		   texto.append("\n");
    		   for (Mensagem mensagem : enviadas) {
    			   texto.append("--> ").append(mensagem.toString()).append("\n");
    	       }
    	   }
    	   
    	   texto.append("Mensagens recebidas: ");
    	   if (recebidas.isEmpty()) {
    		   texto.append("nenhuma mensagem\n");
    	   }else {
    		   texto.append("\n");
    	       for (Mensagem mensagem : recebidas) {
    	    	   texto.append("--> ").append(mensagem.toString()).append("\n");
    	       }
    	   }
    	   return texto.toString();
        }
}
