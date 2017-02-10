package br.com.alura.ichat_alura.event;

import br.com.alura.ichat_alura.modelo.Mensagem;

/**
 * Created by Adilson on 09/02/2017.
 */
public class MensagemEvent {

    public Mensagem mensagem;

    public MensagemEvent(){}

    public MensagemEvent(Mensagem mensagem) {
        this.mensagem = mensagem;
    }
}
