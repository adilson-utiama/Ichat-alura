package br.com.alura.ichat_alura.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.alura.ichat_alura.app.ChatApplication;
import br.com.alura.ichat_alura.R;
import br.com.alura.ichat_alura.adapter.MensagemAdapter;
import br.com.alura.ichat_alura.callback.EnviarMensagemCallback;
import br.com.alura.ichat_alura.callback.OuvirMensagensCallback;
import br.com.alura.ichat_alura.component.ChatComponent;
import br.com.alura.ichat_alura.modelo.Mensagem;
import br.com.alura.ichat_alura.service.ChatService;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private int idDoCliente = 1;
    private List<Mensagem> mensagens;

    private EditText texto;
    private Button botaoEnviar;
    private ListView listaDeMensagens;

    @Inject
    ChatService chatService;

    private ChatComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ChatApplication app = (ChatApplication) getApplication();
        component = app.getComponent();
        component.inject(this);

        texto = (EditText) findViewById(R.id.et_texto);
        botaoEnviar = (Button) findViewById(R.id.btn_enviar);
        listaDeMensagens = (ListView) findViewById(R.id.lv_mensagens);

        mensagens = new ArrayList<>();
        MensagemAdapter adapter = new MensagemAdapter(idDoCliente, mensagens, this);
        listaDeMensagens.setAdapter(adapter);

        ouvirMensagem();

        botaoEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatService.enviar((new Mensagem(idDoCliente, texto.getText().toString())))
                        .enqueue(new EnviarMensagemCallback());
                texto.setText("");
            }
        });

    }

    public void colocaNaLista(Mensagem mensagem){
        mensagens.add(mensagem);
        MensagemAdapter addapter = new MensagemAdapter(idDoCliente, mensagens, this);
        listaDeMensagens.setAdapter(addapter);

    }

    public void ouvirMensagem() {
        Call<Mensagem> call = chatService.ouvirMensagens();
        call.enqueue(new OuvirMensagensCallback(this));
    }


}
