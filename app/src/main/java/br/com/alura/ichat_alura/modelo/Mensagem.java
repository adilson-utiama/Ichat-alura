package br.com.alura.ichat_alura.modelo;

/**
 * Created by Adilson on 06/02/2017.
 */
public class Mensagem {

    private int id;
    private String texto;

    public Mensagem(int id, String texto) {
         this.id = id;
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public int getId() {
        return id;
    }
}
