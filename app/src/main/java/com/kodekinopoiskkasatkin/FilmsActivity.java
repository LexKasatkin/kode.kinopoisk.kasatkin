package com.kodekinopoiskkasatkin;

import android.app.Dialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.ResponseHandler;
import cz.msebera.android.httpclient.client.entity.UrlEncodedFormEntity;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.cookie.Cookie;
import cz.msebera.android.httpclient.impl.client.BasicResponseHandler;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class FilmsActivity extends AppCompatActivity {
    Dialog dialog;
    RadioButton rb;
    RadioGroup rg;
    String urlCities = "http://api.kinopoisk.cf/getCityList?countryID=2";
    String csrf;
    ArrayList<City>cities;
    ArrayList<String> cityNames;
    ArrayList<String> cityIDs;
    String cityID;
    String cityName;
    EditText etCity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_films);
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
            etCity=(EditText)findViewById(R.id.etCity);
            etCity.setSelected(false);
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
                                } else {
                                    etCity.setText("");
                                    dialog.dismiss();
                                }
                            }
                        });
                    }
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
}
