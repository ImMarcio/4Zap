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
        public ArrayList<Mensagem> getRecebidas(){ return recebidas; }
        public ArrayList<Mensagem> getEnviadas() {return enviadas;}
        
        // Adição e Remoção nas propriedades
        public void adicionarMensagemEnviada(Mensagem mensagem) {enviadas.add(mensagem);}       
        public void removerMensagemEnviada(Mensagem mensagem) {enviadas.remove(mensagem);}
        public void adicionarMensagemRecebida(Mensagem mensagem) {recebidas.add(mensagem);}
        public void removerMensagemRecebida(Mensagem mensagem) {recebidas.remove(mensagem);}
        
       @Override
        public String toString(){
            String texto = "Nome: " + nome + "\n"+ "Mensagens Recebidas: \n";
            for(Mensagem mensagem : recebidas){
                texto += "-->  " + mensagem.toString() + "\n";
            }
            texto += "\nMensagens Enviadas: \n";
            for(Mensagem mensagem : enviadas){
                texto += "--> " + mensagem.toString()+ "\n";
            }
            return texto;
        }
}
