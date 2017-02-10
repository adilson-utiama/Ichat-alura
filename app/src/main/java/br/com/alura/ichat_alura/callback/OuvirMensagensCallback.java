package br.com.alura.ichat_alura.callback;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import br.com.alura.ichat_alura.activity.MainActivity;
import br.com.alura.ichat_alura.modelo.Mensagem;
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

            Intent intent = new Intent("nova_mensagem");
            intent.putExtra("mensagem", mensagem);

            LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(context);
            localBroadcastManager.sendBroadcast(intent);

            //activity.colocaNaLista(mensagem);
            //activity.ouvirMensagem();
        }
    }

    @Override
    public void onFailure(Call<Mensagem> call, Throwable t) {
            //activity.ouvirMensagem();
    }
}
