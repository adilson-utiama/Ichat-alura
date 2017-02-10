package br.com.alura.ichat_alura.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.alura.ichat_alura.R;
import br.com.alura.ichat_alura.adapter.MensagemAdapter;
import br.com.alura.ichat_alura.app.ChatApplication;
import br.com.alura.ichat_alura.callback.EnviarMensagemCallback;
import br.com.alura.ichat_alura.callback.OuvirMensagensCallback;
import br.com.alura.ichat_alura.component.ChatComponent;
import br.com.alura.ichat_alura.event.MensagemEvent;
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
    @BindView(R.id.iv_avatar_usuario)
    ImageView avatar;

    @Inject
    ChatService chatService;
    @Inject
    Picasso picasso;
    @Inject
    EventBus eventBus;
    @Inject
    InputMethodManager inputMethodManager;

    private ChatComponent component;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        ChatApplication app = (ChatApplication) getApplication();
        component = app.getComponent();
        component.inject(this);

        picasso.with(this)
                .load("http://api.adorable.io/avatars/285/" + idDoCliente + ".png")
                .into(avatar);

        if(savedInstanceState != null){
            mensagens = (List<Mensagem>) savedInstanceState.getSerializable("mensagens");
        }else {
            mensagens = new ArrayList<>();
        }

        MensagemAdapter adapter = new MensagemAdapter(idDoCliente, mensagens, this);
        listaDeMensagens.setAdapter(adapter);

        ouvirMensagem(new MensagemEvent());

        eventBus.register(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putSerializable("mensagens", (ArrayList<Mensagem>) mensagens);
    }

    @Override
    protected void onStop() {
        super.onStop();

        eventBus.unregister(this);
    }

    @OnClick(R.id.btn_enviar)
    public void enviarMEnsagem(){
        chatService.enviar((new Mensagem(idDoCliente, texto.getText().toString())))
                .enqueue(new EnviarMensagemCallback());
        texto.getText().clear();
        inputMethodManager.hideSoftInputFromWindow(texto.getWindowToken(), 0);
    }

    @Subscribe
    public void colocaNaLista(MensagemEvent mensagemEvent){
        mensagens.add(mensagemEvent.mensagem);
        MensagemAdapter addapter = new MensagemAdapter(idDoCliente, mensagens, this);
        listaDeMensagens.setAdapter(addapter);

    }

    @Subscribe
    public void ouvirMensagem(MensagemEvent mensagemEvent) {
        Call<Mensagem> call = chatService.ouvirMensagens();
        call.enqueue(new OuvirMensagensCallback(eventBus, this));
    }


}
