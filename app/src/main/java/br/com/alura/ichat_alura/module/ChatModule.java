package br.com.alura.ichat_alura.module;

import android.app.Application;

import com.squareup.picasso.Picasso;

import br.com.alura.ichat_alura.service.ChatService;
import dagger.Module;
import dagger.Provides;
import dagger.producers.Produces;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Adilson on 08/02/2017.
 */

@Module
public class ChatModule {

    private Application app;

    public ChatModule(Application app) {
        this.app = app;
    }

    @Provides
    public ChatService getChatService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.100:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatService chatService = retrofit.create(ChatService.class);
        return chatService;
    }

    @Provides
    public Picasso getPicasso(){
        Picasso picasso = new Picasso.Builder(app).build();
        return picasso;
    }
}
