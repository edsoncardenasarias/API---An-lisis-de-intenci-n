package service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import static com.google.gson.JsonParser.parseReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.System.Logger.Level;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import modelo.model;

public class Intencion {

    private static String api_key = "XVVw5hu5QSwaHaIfn9zDTesYDYDorwCAuhsAnAr4tz8";
    private static String host = "https://apis.paralleldots.com/v4/";

    public static String intent(String text) throws Exception {
        if (api_key != null) {
            String url = host + "intent";
            OkHttpClient client = new OkHttpClient();
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("api_key", api_key)
                    .addFormDataPart("text", text)
                    .build();
            Request request = new Request.Builder()
                    .url(url + "?api_key=" + api_key + "&text=" + text)
                    .post(requestBody)
                    .addHeader("cache-control", "no-cache")
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } else {
            return "{ \"Error\": \"API key does not exist\" }";
        }
    }

    public static void intento(model mol) throws Exception {
        try {
            String texto = mol.getText();
            if (api_key != null) {
                String url = host + "intent";
                OkHttpClient client = new OkHttpClient();
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("api_key", api_key)
                        .addFormDataPart("text", texto)
                        .build();
                Request request = new Request.Builder()
                        .url(url + "?api_key=" + api_key + "&text=" + texto)
                        .post(requestBody)
                        .addHeader("cache-control", "no-cache")
                        .build();
                Response response = client.newCall(request).execute();
                JsonObject jsonObject = JsonParser.parseString​(response.body().string()).getAsJsonObject();
                if (jsonObject.isJsonObject()) {
                    JsonObject rootobj = jsonObject.getAsJsonObject();
                    JsonObject intent = rootobj.getAsJsonObject("intent");
                    String news = intent.get("news").getAsString();
                    String query = intent.get("query").getAsString();
                    String spam = intent.get("spam").getAsString();
                    String marketing = intent.get("marketing").getAsString();
                    String feedback = intent.get("feedback").getAsString();
                    System.out.println("resultado\n");
                    System.out.println(news + "\n" + query + "\n" + spam + "\n" + marketing + "\n" + feedback + "\n");
                    mol.setNoticia(news);
                    mol.setConsulta(query);
                    mol.setSpam(spam);
                    mol.setMarketing(marketing);
                    mol.setRetro(feedback);
                }

            }

        } catch (Exception e) {
            System.out.println("error" + e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        String texto = "soy victor la vida es triste, me quiero morir";
        String resultado = "";
        resultado = intent(texto);
        System.out.println("resultado" + resultado);
        model mol = new model();
        mol.setText(texto);
        intento(mol);
    }
}
