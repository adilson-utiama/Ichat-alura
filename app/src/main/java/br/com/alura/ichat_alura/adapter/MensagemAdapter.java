package br.com.alura.ichat_alura.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.alura.ichat_alura.R;
import br.com.alura.ichat_alura.modelo.Mensagem;

/**
 * Created by Adilson on 06/02/2017.
 */
public class MensagemAdapter extends BaseAdapter{

    private List<Mensagem> mensagens;
    private Activity activity;
    private int idDoCliente;

    public MensagemAdapter(int idDoCliente, List<Mensagem> mensagens, Activity activity){
        this.mensagens = mensagens;
        this.activity = activity;
        this.idDoCliente = idDoCliente;
    }

    @Override
    public int getCount() {
        return mensagens.size();
    }

    @Override
    public Mensagem getItem(int i) {
        return mensagens.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View linha = activity.getLayoutInflater().inflate(R.layout.mensagem, viewGroup, false);
        TextView texto = (TextView) linha.findViewById(R.id.tv_texto);

        Mensagem mensagem = getItem(i);

        if(idDoCliente != mensagem.getId()){
            linha.setBackgroundColor(Color.CYAN);
        }

        texto.setText(mensagem.getTexto());

        return linha;
    }
}
