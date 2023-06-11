package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mensagem {
    private int id;
    private String texto;
    private Individual emitente;
    private Participante destinatario;
    private LocalDateTime datahora;

    public Mensagem(int id, String texto, Individual emitente, Participante destinatario, LocalDateTime datahora){
        this.id = id;
        this.texto = texto;
        this.emitente = emitente;
        this.destinatario = destinatario;
        this.datahora = datahora;
    }
    public Mensagem(){

    }

    public int getId(){
        return this.id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getTexto(){
        return this.texto;
    }

    public void setTexto(String texto){
        this.texto = texto;
    }

    public Individual getEmitente(){
        return this.emitente;
    }
    public void setEmitente(Individual emitente){
        this.emitente = emitente;
    }

    public Participante getDestinatario(){
        return this.destinatario;
    }
    public void setDestinatario(Participante destinatario){
        this.destinatario = destinatario;
    }

    public LocalDateTime getDataHora(){
        return this.datahora;
    }
    public void setDataHora(LocalDateTime datahora){
    	DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
    	LocalDateTime now = LocalDateTime.now(); 
        this.datahora = now;
    }


    @Override
    public String toString(){
        String texto = "ID" + this.id + "\n" + " Texto da mensagem:" + this.texto + "\n" + "Emitente:" + emitente + "\n" + "Destinatário: " + destinatario + "\n" +  "Hora da Mensagem: " + datahora;
        
        return texto;
    }
}
