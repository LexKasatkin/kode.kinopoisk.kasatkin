package com.kodekinopoiskkasatkin;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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

public class FilterActivity extends AppCompatActivity {
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

    ArrayList<String> genreNames;
    ArrayList<String> genreIDs;
    String genreID;
    String genreName;
    EditText etGenres;

    Button ibCity;
    Button ibGenre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
getSupportActionBar().setTitle("");
        CountryTask countryTask=new CountryTask();
        countryTask.execute();


//получаем ответ от сервера

    }


    public class CountryTask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            cities=new ArrayList<City>();
            cityIDs=new ArrayList<String>();
            cityNames=new ArrayList<String>();
            genreIDs=new ArrayList<String>();
            genreNames=new ArrayList<String>();
            etCity=(EditText)findViewById(R.id.etCity);
            etGenres=(EditText)findViewById(R.id.etGenres);
            etCity.setSelected(false);
            ibCity=(Button)findViewById(R.id.ibCity);
            ibGenre=(Button)findViewById(R.id.ibGenre);
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

            etCity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {
                        showRadioButtonDialog(cityNames);
                        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (dialog.findViewById(rg.getCheckedRadioButtonId()) != null) {
                                    int i = rg.indexOfChild(dialog.findViewById(rg.getCheckedRadioButtonId()));
                                    cityID = cityIDs.get(i);
                                    cityName = cityNames.get(i);
                                    etCity.setText(cityName);
                                    dialog.dismiss();
                                    ibCity.setEnabled(true);
                                    ibCity.setVisibility(View.VISIBLE);
                                } else {
                                    etCity.setText("");
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                }
            });

            etCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showRadioButtonDialog(cityNames);
                    rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                            if (dialog.findViewById(rg.getCheckedRadioButtonId()) != null) {
                                int i = rg.indexOfChild(dialog.findViewById(rg.getCheckedRadioButtonId()));
                                cityID = cityIDs.get(i);
                                cityName = cityNames.get(i);
                                etCity.setText(cityName);
                                dialog.dismiss();
                                ibCity.setEnabled(true);
                                ibCity.setVisibility(View.VISIBLE);
                            }else {
                                etCity.setText("");
                                dialog.dismiss();
                            }
                        }
            });
                }
            });

            etGenres.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if(hasFocus) {
                        showRadioButtonDialog(genreNames);
                        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(RadioGroup group, int checkedId) {
                                if (dialog.findViewById(rg.getCheckedRadioButtonId()) != null) {
                                    int i = rg.indexOfChild(dialog.findViewById(rg.getCheckedRadioButtonId()));
                                    genreID = genreIDs.get(i);
                                    genreName = genreNames.get(i);
                                    etGenres.setText(genreName);
                                    dialog.dismiss();
                                    ibGenre.setEnabled(true);
                                    ibGenre.setVisibility(View.VISIBLE);
                                } else {
                                    etGenres.setText("");
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
                }
            });

                    etGenres.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showRadioButtonDialog(genreNames);
                            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(RadioGroup group, int checkedId) {
                                    if (dialog.findViewById(rg.getCheckedRadioButtonId()) != null) {
                                        int i = rg.indexOfChild(dialog.findViewById(rg.getCheckedRadioButtonId()));
                                        genreID = genreIDs.get(i);
                                        genreName = genreNames.get(i);
                                        etGenres.setText(genreName);
                                        dialog.dismiss();
                                        ibGenre.setEnabled(true);
                                        ibGenre.setVisibility(View.VISIBLE);
                                    } else {
                                        etGenres.setText("");
                                        dialog.dismiss();
                                    }
                                }
                            });
                        }
                    });


            ibGenre.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ibGenre.setEnabled(false);
                    ibGenre.setVisibility(View.INVISIBLE);
                    etGenres.setText(null);
                    genreID=null;
                    genreName=null;
                }
            });
            ibCity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ibCity.setEnabled(false);
                    ibCity.setVisibility(View.INVISIBLE);
                    etCity.setText(null);
                    cityName=null;
                    cityID=null;
                }
            });
        }
    }

    private void showRadioButtonDialog(ArrayList<String> arrayList) {

        // custom dialogmel
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.rg_dialog);
//        ivSearchOK=(ImageView)dialog.findViewById(R.id.ivSearchOK);

        ArrayList<String> stringList=new ArrayList<>();  // here is list

        rg = (RadioGroup) dialog.findViewById(R.id.radio_group);
        for(int i=0;i<arrayList.size();i++){
            rb=new RadioButton(this); // dynamically creating RadioButton and adding to RadioGroup.
//            rb.setButtonTintList(ColorStateList.valueOf(R.color.colorAccent));
//            rb.setHighlightColor(R.color.colorAccent);
            rb.setText(arrayList.get(i).toString());
            rg.addView(rb);
        }

        dialog.show();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.ok:
                if(cityID==null){
                    Toast toast = Toast.makeText(FilterActivity.this, "Пожалуйста выберите город"
                            ,Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    Intent intent = new Intent(this, FilmsActivity.class);
                    startActivity(intent);
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.filter_menu, menu);
        return true;
    }

}
