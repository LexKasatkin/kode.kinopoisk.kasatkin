package com.kodekinopoiskkasatkin;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class FilmsActivity extends AppCompatActivity {
    String urlFilms="http://api.kinopoisk.cf/getTodayFilms?date=";
    String cityID;
    String genreName;
    String json_string;
    ArrayList<Film>filmArrayList;
    RecyclerView rvFilms;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films);
        getSupportActionBar().setTitle("Фильмы в прокате");
        filmArrayList=new ArrayList<Film>();
        rvFilms=(RecyclerView)findViewById(R.id.rvFilms);
        cityID=getIntent().getStringExtra("city");
        genreName=getIntent().getStringExtra("genre");
        urlFilms=urlFilms+"&cityID="+cityID.toString();
        CountryTask countryTask=new CountryTask();
        countryTask.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filter:
                    Intent intent = new Intent(this, FilterActivity.class);
                    startActivity(intent);
                return true;
            case R.id.sort_to:
                Collections.sort(filmArrayList); // альтернатива
                RVAdapterFilms rvAdapterFilms=new RVAdapterFilms(filmArrayList);
                rvAdapterFilms.notifyDataSetChanged();
                rvFilms.setAdapter(rvAdapterFilms);
                rvFilms.setHasFixedSize(true);
                LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
                llm.setOrientation(LinearLayoutManager.VERTICAL);
                rvFilms.setLayoutManager(llm);
                return true;
            case R.id.sort_down:
                Collections.sort(filmArrayList); // альтернатива
                Collections.reverse(filmArrayList);
                RVAdapterFilms rvAdapterFilms1=new RVAdapterFilms(filmArrayList);
                rvAdapterFilms1.notifyDataSetChanged();
                rvFilms.setAdapter(rvAdapterFilms1);
                rvFilms.setHasFixedSize(true);
                LinearLayoutManager llm1 = new LinearLayoutManager(getApplicationContext());
                llm1.setOrientation(LinearLayoutManager.VERTICAL);
                rvFilms.setLayoutManager(llm1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.films_menu, menu);
        return true;
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
                HttpGet httpGet = new HttpGet(urlFilms);
                HttpResponse httpResponse = null;
                httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                json_string = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                if(json_string!=null) {
                    JSONObject jsonObject = new JSONObject(json_string);
                    JSONArray jsonArrayFilms=jsonObject.getJSONArray("filmsData");
                    for(int i=0; i<jsonArrayFilms.length();i++){
                        JSONObject jsonObjectFilm=jsonArrayFilms.getJSONObject(i);
                        Film film=new Film();
                        if(jsonObjectFilm.has("type")){
                            film.setType(jsonObjectFilm.getString("type"));
                        }
                        if(jsonObjectFilm.has("id")){
                            film.setId(jsonObjectFilm.getString("id"));
                        }
                        if(jsonObjectFilm.has("nameRU")){
                            film.setNameRU(jsonObjectFilm.getString("nameRU"));
                        }
                        if(jsonObjectFilm.has("nameEN")){
                            film.setNameEN(jsonObjectFilm.getString("nameEN"));
                        }
                        if(jsonObjectFilm.has("year")){
                            film.setYear(jsonObjectFilm.getString("year"));
                        }
                        if(jsonObjectFilm.has("cinemaHallCount")){
                            film.setCinemaHallCount(jsonObjectFilm.getString("cinemaHallCount"));
                        }
                        if(jsonObjectFilm.has("is3D")){
                            film.setIs3D(jsonObjectFilm.getString("is3D"));
                        }
                        if(jsonObjectFilm.has("rating")){
                            String s=(jsonObjectFilm.getString("rating"));
                            s=s.substring(0, s.indexOf("(")-1);
                            film.setRating(Double.valueOf(s));
                        }else if(!jsonObjectFilm.has("rating")){
                            film.setRating(0.0);
                        }
                        if(jsonObjectFilm.has("posterURL")){
                            film.setPosterURL(jsonObjectFilm.getString("posterURL"));
                        }
                        if(jsonObjectFilm.has("filmLength")){
                            film.setFilmLength(jsonObjectFilm.getString("filmLength"));
                        }
                        if(jsonObjectFilm.has("country")){
                            film.setCountry(jsonObjectFilm.getString("country"));
                        }
                        if(jsonObjectFilm.has("genre")){
                            film.setGenre(jsonObjectFilm.getString("genre"));
                        }
                        if(jsonObjectFilm.has("filmLength")){
                            film.setFilmLength(jsonObjectFilm.getString("filmLength"));
                        }
                        if(jsonObjectFilm.has("premiereRU")){
                            film.setPremiereRU(jsonObjectFilm.getString("premiereRU"));
                        }
                        filmArrayList.add(film);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return filmArrayList;
        }
        @Override
        protected void onPostExecute(ArrayList<Film> jsonArray) {
            RVAdapterFilms rvAdapterFilms=new RVAdapterFilms(jsonArray);
            rvAdapterFilms.notifyDataSetChanged();
            rvFilms.setAdapter(rvAdapterFilms);
            rvFilms.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rvFilms.setLayoutManager(llm);
            showProgressDialog(false);
             }
        }


    private void showProgressDialog(boolean visible) {
        if (visible) {
            if (progressDialog == null || !progressDialog.isShowing()) {
                try {
                    progressDialog = new ProgressDialog(this, R.style.MyTheme);
                    progressDialog.setProgress(R.drawable.circular_progress_bar);
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                progressDialog = null;
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
