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
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private int idDoCliente = 1;
    private List<Mensagem> mensagens;

    @BindView(R.id.et_texto)
    EditText texto;
    @BindView(R.id.btn_enviar)
    Button botaoEnviar;
    @BindView(R.id.lv_mensagens)
    ListView listaDeMensagens;

    @Inject
    ChatService chatService;

    private ChatComponent component;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ChatApplication app = (ChatApplication) getApplication();
        component = app.getComponent();
        component.inject(this);

        mensagens = new ArrayList<>();
        MensagemAdapter adapter = new MensagemAdapter(idDoCliente, mensagens, this);
        listaDeMensagens.setAdapter(adapter);

        ouvirMensagem();

    }

    @OnClick(R.id.btn_enviar)
    public void enviarMEnsagem(){
        chatService.enviar((new Mensagem(idDoCliente, texto.getText().toString())))
                .enqueue(new EnviarMensagemCallback());
        texto.setText("");
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
