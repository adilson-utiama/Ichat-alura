package br.com.alura.ichat_alura.callback;

import android.content.Context;


import org.greenrobot.eventbus.EventBus;

import br.com.alura.ichat_alura.modelo.Mensagem;
import br.com.alura.ichat_alura.event.MensagemEvent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Adilson on 07/02/2017.
 */
public class OuvirMensagensCallback implements Callback<Mensagem>{

    private Context context;

    public OuvirMensagensCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onResponse(Call<Mensagem> call, Response<Mensagem> response) {
        if(response.isSuccessful()) {
            Mensagem mensagem = response.body();

            EventBus.getDefault().post(new MensagemEvent(mensagem));
        }
    }

    @Override
    public void onFailure(Call<Mensagem> call, Throwable t) {
            //activity.ouvirMensagem();
    }
}
