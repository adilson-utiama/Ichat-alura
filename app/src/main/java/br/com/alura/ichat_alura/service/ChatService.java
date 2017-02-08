package br.com.alura.ichat_alura.service;

import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import br.com.alura.ichat_alura.activity.MainActivity;
import br.com.alura.ichat_alura.modelo.Mensagem;

/**
 * Created by Adilson on 07/02/2017.
 */
public class ChatService {

    private MainActivity activity;

    public ChatService(MainActivity activity) {
        this.activity = activity;
    }

    public void enviar(final Mensagem mensagem) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String texto = mensagem.getTexto();

                try {
                    URL url = new URL("http://192.168.1.100:8080/polling");

                    HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

                    httpConnection.setRequestMethod("POST");

                    httpConnection.setRequestProperty("Content-type", "application/json");

                    JSONStringer json = new JSONStringer()
                            .object()
                            .key("text")
                            .value(texto)
                            .key("id")
                            .value(mensagem.getId())
                            .endObject();

                    OutputStream saida = httpConnection.getOutputStream();
                    PrintStream ps = new PrintStream(saida);

                    ps.println(json.toString());

                    httpConnection.connect();
                    httpConnection.getInputStream();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

    public void ouvirMensagens() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    // Altere para o seu IP
                    URL url = new URL("http://192.168.1.100:8080/polling");

                    HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();

                    httpConnection.setRequestMethod("GET");

                    httpConnection.setRequestProperty("Accept", "application/json");

                    httpConnection.connect();

                    Scanner scanner = new Scanner(httpConnection.getInputStream());
                    StringBuilder builder = new StringBuilder();

                    while(scanner.hasNextLine()) {
                        builder.append(scanner.nextLine());
                    }

                    String json = builder.toString();

                    JSONObject jsonObject = new JSONObject(json);
                    final Mensagem mensagem = new Mensagem(jsonObject.getInt("id"), jsonObject.getString("text"));

                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            activity.colocaNaLista(mensagem);
                        }
                    });

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }
}
