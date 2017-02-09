package br.com.alura.ichat_alura.component;

import br.com.alura.ichat_alura.activity.MainActivity;
import br.com.alura.ichat_alura.module.ChatModule;
import dagger.Component;

/**
 * Created by Adilson on 08/02/2017.
 */

@Component(modules=ChatModule.class)
public interface ChatComponent {

    void inject(MainActivity activity);
}
