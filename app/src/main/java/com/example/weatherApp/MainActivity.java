package com.example.weatherApp;

import android.widget.*;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import okhttp3.*;


public class MainActivity extends AppCompatActivity {

    Button btn1;
    ImageButton btn2;
    EditText getCity;

    CurrentLocation currentLocation;

    OkHttpClient client = new OkHttpClient();

    final WeatherDataSerivce weatherDataSerivce = new WeatherDataSerivce(MainActivity.this,
            client);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new GPS(MainActivity.this);

        getCity = (EditText) findViewById(R.id.enterLocation);
        btn1 = (Button) findViewById(R.id.searchLocation);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weatherDataSerivce.getWebservice(getCity.getText().toString().trim(), new ResponseListener() {
                    @Override
                    public void onError(String message) {
                        ((TextView) findViewById(R.id.location)).setText(message);
                    }

                    @Override
                    public void onResponse(DataWarehouse data) {
                        updateElements(data);

                    }
                });
                getCity.setText("");
            }
        });

        btn2 = (ImageButton) findViewById(R.id.btn2);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GPS(MainActivity.this);
                currentLocation = new CurrentLocation(MainActivity.this, weatherDataSerivce);
            }
        });
    }

    protected void updateElements(DataWarehouse value) {
        ((TextView) findViewById(R.id.location)).setText(value.location);
        ((TextView) findViewById(R.id.last_updated)).setText(value.lastModified);
        ((TextView) findViewById(R.id.weatherDescription)).setText(value.description);
        ((TextView) findViewById(R.id.temperature)).setText(value.temp);
        ((TextView) findViewById(R.id.minTemp)).setText(value.tempMin);
        ((TextView) findViewById(R.id.maxTemp)).setText(value.tempMax);
        ((TextView) findViewById(R.id.sunrise)).setText(value.sunrise);
        ((TextView) findViewById(R.id.sunset)).setText(value.sunset);
        ((TextView) findViewById(R.id.wind)).setText(value.speed);
        ((TextView) findViewById(R.id.pressure)).setText(value.pressure);
        ((TextView) findViewById(R.id.humidity)).setText(value.humidity);
    }
}