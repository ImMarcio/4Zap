package repositorio;

import java.util.TreeMap;

import modelo.Mensagem;
import modelo.Participante;

public class Repositorio {
    private TreeMap<String,Participante> participantes = new TreeMap<>();
    private TreeMap<Integer,Mensagem> mensagens = new TreeMap<>();

    public Repositorio(){
        carregarObjetos(); //ler dados dos arquivos
    }
}
