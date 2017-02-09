package br.com.alura.ichat_alura.app;

import android.app.Application;

import br.com.alura.ichat_alura.component.ChatComponent;
import br.com.alura.ichat_alura.component.DaggerChatComponent;
import br.com.alura.ichat_alura.module.ChatModule;

/**
 * Created by Adilson on 08/02/2017.
 */
public class ChatApplication extends Application{

    private ChatComponent component;

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerChatComponent.builder()
                .chatModule(new ChatModule(this))
                .build();
    }

    public ChatComponent getComponent(){
        return component;
    }
}
