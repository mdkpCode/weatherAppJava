package com.example.weatherApp;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import java.io.IOException;
import java.util.Objects;

public class WeatherDataSerivce {

    private static final String API = "8544c10e7dc1de8a48fd3f18dd3c13ed";


    AppCompatActivity appCompatActivity;
    OkHttpClient client;

    public WeatherDataSerivce(AppCompatActivity appCompatActivity, OkHttpClient client) {
        this.appCompatActivity = appCompatActivity;
        this.client = client;
    }

    public void getWebservice(String city, final ResponseListener responseListener) {

        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&units=metric&lang=pl&appid=" + API;
        final Request request = new Request.Builder().url(url).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                appCompatActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String error = "Brak internetu";
                        responseListener.onError(error);

                    }
                });
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                String jsonData = Objects.requireNonNull(response.body()).string();

                appCompatActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DataWarehouse dataWarehouse = new DataWarehouse(jsonData);
                            responseListener.onResponse(dataWarehouse);

                        } catch (JSONException e) {
                            String error = "Niepoprawna nazwa";
                            responseListener.onError(error);
                        }
                    }
                });
            }
        });
        client = new OkHttpClient();
    }
}
