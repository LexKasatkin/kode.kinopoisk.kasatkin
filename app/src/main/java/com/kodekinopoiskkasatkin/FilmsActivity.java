package com.kodekinopoiskkasatkin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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
    String json_string;
    ArrayList<Film>filmArrayList;
    RecyclerView rvFilms;


    Dialog dialog;
    RadioButton rb;
    RadioGroup rg;
    String urlCities = "http://api.kinopoisk.cf/getCityList?countryID=2";
    String urlGenres = "http://api.kinopoisk.cf/getGenres";
    ArrayList<City>cities;
    ArrayList<String> cityNames;
    ArrayList<String> cityIDs;
    String cityID;
    String cityName;
    EditText etCity;
    ProgressDialog progressDialog;

    ArrayList<String> genreNames;
    ArrayList<String> genreIDs;
    String genreID;
    String genreName;
    EditText etGenres;

    Button ibCity;
    Button ibGenre;
    private static final String MY_SETTINGS = "my_settings";
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films);
        sp = getSharedPreferences(MY_SETTINGS,
                Context.MODE_PRIVATE);
        if(sp.getString("CITYNAME", null)!=null){
            getSupportActionBar().setTitle("Фильмы в прокате г."+sp.getString("CITYNAME",null));
        }else {
            getSupportActionBar().setTitle("Фильмы в прокате " + "г.Калининград");
        }
        rvFilms=(RecyclerView)findViewById(R.id.rvFilms);
        CountryTask countryTask=new CountryTask();
        countryTask.execute();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
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
            case R.id.genre:
                showRadioButtonDialog(genreNames);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (dialog.findViewById(rg.getCheckedRadioButtonId()) != null) {
                            int i = rg.indexOfChild(dialog.findViewById(rg.getCheckedRadioButtonId()));
                            genreID = genreIDs.get(i);
                            genreName = genreNames.get(i);
                            dialog.dismiss();
                            FilmsTask filmsTask =new FilmsTask();
                            filmsTask.execute();
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
                return true;
            case R.id.cities:
                showRadioButtonDialog(cityNames);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        if (dialog.findViewById(rg.getCheckedRadioButtonId()) != null) {
                            int i = rg.indexOfChild(dialog.findViewById(rg.getCheckedRadioButtonId()));
                            cityID = cityIDs.get(i);
                            cityName = cityNames.get(i);
                            dialog.dismiss();
                            getSupportActionBar().setTitle("Фильмы в прокате г."+cityName);
                             sp = getSharedPreferences(MY_SETTINGS,
                                    Context.MODE_PRIVATE);
                            SharedPreferences.Editor e = sp.edit();
                            e.putString("CITYID", cityID);
                            e.putString("CITYNAME",cityName);
                            e.commit();
                            FilmsTask filmsTask =new FilmsTask();
                            filmsTask.execute();
                        } else {
                            dialog.dismiss();
                        }
                    }
                });
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

    public class FilmsTask extends AsyncTask<String, Void, ArrayList<Film>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            sp = getSharedPreferences(MY_SETTINGS,
                    Context.MODE_PRIVATE);
            filmArrayList=new ArrayList<Film>();
            if(cityID!=null) {
                urlFilms = urlFilms + "&cityID=" + cityID.toString();
            }else if (sp.getString("CITYID",null)!=null){
                cityID=sp.getString("CITYID",null);
                urlFilms = urlFilms + "&cityID=" + cityID.toString();

            }else {
                cityID = "490";
                urlFilms = urlFilms + "&cityID=" + cityID.toString();
            }
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

    private void showRadioButtonDialog(ArrayList<String> arrayList) {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rg_dialog);

        ArrayList<String> stringList=new ArrayList<>();  // here is list

        rg = (RadioGroup) dialog.findViewById(R.id.radio_group);
        for(int i=0;i<arrayList.size();i++){
            rb=new RadioButton(this); // dynamically creating RadioButton and adding to RadioGroup.
            rb.setText(arrayList.get(i).toString());
            rg.addView(rb);
        }

        dialog.show();
    }


    public class CountryTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(true);
            cities=new ArrayList<City>();
            cityIDs=new ArrayList<String>();
            cityNames=new ArrayList<String>();
            genreIDs=new ArrayList<String>();
            genreNames=new ArrayList<String>();
        }

        @Override
        protected String doInBackground(String... urls) {
            String response = null;

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(urlCities);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                String json_string = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                if(json_string!=null){
                    JSONObject jsonObject=new JSONObject(json_string);
                    JSONArray jsonArrayCities=jsonObject.getJSONArray("cityData");
                    for(int i=0;i<jsonArrayCities.length();i++){
                        City city=new City();
                        JSONObject jsonObjectCity=jsonArrayCities.getJSONObject(i);
                        if(jsonObjectCity.has("cityID")){
                            city.setCityID(jsonObjectCity.getString("cityID"));
                            cityIDs.add(city.getCityID());
                        }
                        if(jsonObjectCity.has("cityName")){
                            city.setCityName(jsonObjectCity.getString("cityName"));
                            cityNames.add(city.getCityName());
                        }
                        cities.add(city);
                    }
                }

                HttpClient httpClient1 = new DefaultHttpClient();
                HttpGet httpGet1 = new HttpGet(urlGenres);
                HttpResponse httpResponse1 = httpClient1.execute(httpGet1);
                HttpEntity httpEntity1 = httpResponse1.getEntity();
                String json_string1 = EntityUtils.toString(httpResponse1.getEntity(), "UTF-8");
                if(json_string1!=null){
                    JSONObject jsonObject=new JSONObject(json_string1);
                    JSONArray jsonArrayGenres=jsonObject.getJSONArray("genreData");
                    for(int i=0;i<jsonArrayGenres.length();i++){
                        JSONObject jsonObjectGenre=jsonArrayGenres.getJSONObject(i);
                        if(jsonObjectGenre.has("genreID")){
                            genreIDs.add(jsonObjectGenre.getString("genreID"));
                        }
                        if(jsonObjectGenre.has("genreName")){
                            genreNames.add(jsonObjectGenre.getString("genreName"));
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String jsonData) {
            FilmsTask filmsTask =new FilmsTask();
            filmsTask.execute();
            showProgressDialog(false);
        }
    }
}
