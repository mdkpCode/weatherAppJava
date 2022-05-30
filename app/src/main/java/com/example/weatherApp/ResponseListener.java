package com.example.weatherApp;

public interface ResponseListener {

    void onError(String message);

    void onResponse(DataWarehouse dataWarehouse);
}
