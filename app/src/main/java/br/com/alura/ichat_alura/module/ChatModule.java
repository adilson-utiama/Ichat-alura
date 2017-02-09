package br.com.alura.ichat_alura.module;

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

    @Provides
    public ChatService getChatService(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.101:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ChatService chatService = retrofit.create(ChatService.class);
        return chatService;
    }
}
