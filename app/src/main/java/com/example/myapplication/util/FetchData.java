package com.example.myapplication.util;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FetchData extends Thread {
    private URL url;
    private JSONObject jsonObject;

    public FetchData(String url) {
        try {
            this.url = new URL(url);
            this.start();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        String data = "";
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                data = data + line;
            }

            if(!data.isEmpty()) {
                jsonObject = new JSONObject(data);
            }
        } catch (IOException | JSONException e) {
            Log.e("FetchData", "Error in fetching data");
        }
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }

}
