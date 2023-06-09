package repositorio;

public class Repositorio {
    private TreeMap<String,Participante> participantes = new TreeMap<>();
    private TreeMap<Integer,Mensagem> mensagens = new TreeMap<>();

    public Repositorio(){
        carregarObjetos(); //ler dados dos arquivos
    }
}
