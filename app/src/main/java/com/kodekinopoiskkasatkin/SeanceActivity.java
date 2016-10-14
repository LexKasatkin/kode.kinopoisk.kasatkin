package com.kodekinopoiskkasatkin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
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

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class SeanceActivity extends AppCompatActivity {
    private static final String MY_SETTINGS = "my_settings";
    String idCity;
    String idFilm;
    ProgressDialog progressDialog;
    String urlSeance="http://api.kinopoisk.cf/getSeance?filmID=";
    String json_string;
    ArrayList<Cinema> cinemaArrayList;
    ArrayList<String> seances;
    RecyclerView rvCinema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seance);
        getSupportActionBar().setTitle("Расписание сеансов");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        SharedPreferences sp = getSharedPreferences(MY_SETTINGS,
                Context.MODE_PRIVATE);
        idCity=sp.getString("CITYID", "490");
        idFilm=getIntent().getStringExtra("FILMID");
        urlSeance=urlSeance+idFilm+"&cityID="+idCity+"&date=";
        SeanceTask seanceTask=new SeanceTask();
        seanceTask.execute();
    }

    public class SeanceTask extends AsyncTask<String, Void, ArrayList<Cinema>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cinemaArrayList=new ArrayList<Cinema>();
            showProgressDialog(true);
        }

        @Override
        protected ArrayList<Cinema> doInBackground(String... urls) {

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(urlSeance);
                HttpResponse httpResponse = null;
                httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                json_string = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                if (json_string != null) {
                    JSONObject jsonObject=new JSONObject(json_string);
                    if(jsonObject.has("items")){
                        JSONArray jsonArrayItems=jsonObject.getJSONArray("items");
                        if(jsonArrayItems.length()!=0){
                            for (int i=0;i<jsonArrayItems.length();i++){
                                Cinema cinema=new Cinema();
                                JSONObject jsonObjectItem=jsonArrayItems.getJSONObject(i);
                                if(jsonObjectItem.has("cinemaID")){
                                    cinema.setCinemaID(jsonObjectItem.getString("cinemaID"));
                                }
                                if(jsonObjectItem.has("address")){
                                    cinema.setAddress(jsonObjectItem.getString("address"));
                                }
                                if(jsonObjectItem.has("lon")){
                                    cinema.setLon(jsonObjectItem.getString("lon"));
                                }
                                if(jsonObjectItem.has("lat")){
                                    cinema.setLat(jsonObjectItem.getString("lat"));
                                }
                                if(jsonObjectItem.has("cinemaName")){
                                    cinema.setCinemaName(jsonObjectItem.getString("cinemaName"));
                                }
                                if(jsonObjectItem.has("seance")){
                                    seances=new ArrayList<String>();
                                    JSONArray jsonArraySeances=jsonObjectItem.getJSONArray("seance");
                                    for(int j=0;j<jsonArraySeances.length();j++){
                                        JSONObject jsonObjectTime=jsonArraySeances.getJSONObject(j);
                                        String seance = new String();
                                        if(jsonObjectTime.has("time")) {
                                            seance = jsonObjectTime.getString("time");
                                        }
                                        seances.add(seance);
                                    }
                                }
                                cinema.setTime(seances);
                                cinemaArrayList.add(cinema);
                            }
                        }
                    }
                }
            } catch (IOException e) {

                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return cinemaArrayList;
        }

        @Override
        protected void onPostExecute(ArrayList<Cinema> jsonArray) {
            showProgressDialog(false);
            rvCinema=(RecyclerView)findViewById(R.id.rvSeances);
            RVAdapterCinema rvAdapterCinema=new RVAdapterCinema(jsonArray);
            rvAdapterCinema.notifyDataSetChanged();
            rvCinema.setAdapter(rvAdapterCinema);
            rvCinema.setHasFixedSize(true);
            LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            rvCinema.setLayoutManager(llm);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                this.finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.film_menu, menu);
        return true;
    }
}
