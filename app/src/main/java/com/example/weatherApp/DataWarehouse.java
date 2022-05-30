package com.example.weatherApp;

import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataWarehouse {

    protected String location;
    protected String lastModified;
    protected String description;
    protected String temp;
    protected String tempMin;
    protected String tempMax;
    protected String sunrise;
    protected String sunset;
    protected String speed;
    protected String pressure;
    protected String humidity;

    public DataWarehouse(String jsonData) throws JSONException {

        JSONObject jObject = new JSONObject(jsonData);

        JSONObject main = jObject.getJSONObject("main");
        JSONObject sys = jObject.getJSONObject("sys");
        JSONObject wind = jObject.getJSONObject("wind");
        JSONObject weather = jObject.getJSONArray("weather").getJSONObject(0);

        location = jObject.getString("name") + ", " + sys.getString("country");
        temp = (Math.round(main.getDouble("temp"))) + "°C";
        description = weather.getString("description");
        tempMin = "Min temp: " + (main.getString("temp_min")) + "°C";
        tempMax = "Max temp: " + (main.getString("temp_max")) + "°C";
        pressure = main.getString("pressure");
        humidity = (main.getString("humidity")) + " %";
        speed = (wind.getString("speed")) + " km/h";

        sunrise = new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                .format(new Date(sys.getLong("sunrise") * 1000));

        sunset = new SimpleDateFormat("hh:mm a", Locale.ENGLISH)
                .format(new Date(sys.getLong("sunset") * 1000));


        lastModified = "Ostatnia akutlizacja: " + (new SimpleDateFormat
                ("dd/MM/yyyy hh:mm a", Locale.ENGLISH).format
                (new Date(jObject.getLong("dt") * 1000)));
    }
}
