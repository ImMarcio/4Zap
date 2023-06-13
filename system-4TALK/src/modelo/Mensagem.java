/*
 * @authors Allan Amancio and Marcio Jose
 * 
 * Mensagem class
 */
package modelo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Mensagem {
    private int id;
    private String texto;
    private Participante emitente;
    private Participante destinatario;
    private LocalDateTime datahora;
    
	public Mensagem(int id,String texto, Individual emitente, Participante destinatario){
		this.id = id;
        this.texto = texto;
        this.emitente = emitente;
        this.destinatario = destinatario;
        this.datahora = LocalDateTime.now();
    }

    // Getters e setters
    public int getId(){ return this.id; }
    public void setId(int id){ this.id = id; }
    public String getTexto(){ return this.texto; }
    public void setTexto(String texto){ this.texto = texto; }
    public Individual getEmitente(){ return (Individual) this.emitente; }
    public void setEmitente(Individual emitente){ this.emitente = emitente; }
    public Participante getDestinatario(){ return this.destinatario; }
    public void setDestinatario(Participante destinatario){ this.destinatario = destinatario; }
    public LocalDateTime getDataHora(){ return this.datahora; }  

    @Override
	public String toString() {
    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String datahora_formatada = datahora.format(formatter);
        
		return "Mensagem [id=" + id + ", texto=" + texto + ", emitente=" + emitente + ", destinatario=" + destinatario
				+ ", datahora=" + datahora_formatada + "]";
	}

}