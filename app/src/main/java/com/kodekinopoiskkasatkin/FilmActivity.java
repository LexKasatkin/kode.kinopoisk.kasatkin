package com.kodekinopoiskkasatkin;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class FilmActivity extends AppCompatActivity {

    String urlFilm="http://api.kinopoisk.cf/getFilm?filmID=";
    String json_string;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        String id=getIntent().getStringExtra("id");
        urlFilm=urlFilm+id;
    }

    public class CountryTask extends AsyncTask<String, Void, ArrayList<Film>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgressDialog(true);
        }

        @Override
        protected ArrayList<Film> doInBackground(String... urls) {

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(urlFilm);
                HttpResponse httpResponse = null;
                httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                json_string = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                if (json_string != null) {
                    JSONObject jsonObject = new JSONObject(json_string);
                }
            }catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return json_string;
        }
        @Override
        protected void onPostExecute(ArrayList<Film> jsonArray) {
        }
    }

}
