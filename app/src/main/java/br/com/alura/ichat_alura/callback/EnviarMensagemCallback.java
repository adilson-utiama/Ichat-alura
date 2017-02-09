package br.com.alura.ichat_alura.callback;

import android.view.View;

import br.com.alura.ichat_alura.activity.MainActivity;
import br.com.alura.ichat_alura.modelo.Mensagem;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Adilson on 07/02/2017.
 */
public class EnviarMensagemCallback implements Callback<Void> {


    @Override
    public void onResponse(Call<Void> call, Response<Void> response) {

    }

    @Override
    public void onFailure(Call<Void> call, Throwable t) {

    }
}
