package com.kodekinopoiskkasatkin;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;

public class FilmActivity extends AppCompatActivity {

    String urlFilm="http://api.kinopoisk.cf/getFilm?filmID=";
    String json_string;
    ProgressDialog progressDialog;
    Film film;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        String id=getIntent().getStringExtra("id");
        urlFilm=urlFilm+id;
        FilmTask filmTask=new FilmTask();
        filmTask.execute();
    }

    public class FilmTask extends AsyncTask<String, Void, Film> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            showProgressDialog(true);
        }

        @Override
        protected Film doInBackground(String... urls) {

            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(urlFilm);
                HttpResponse httpResponse = null;
                httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                json_string = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                if (json_string != null) {
                    JSONObject jsonObject = new JSONObject(json_string);
                    film=new Film();
                    if(jsonObject.has("type")){
                        film.setType(jsonObject.getString("type"));
                    }
                    if(jsonObject.has("id")){
                        film.setId(jsonObject.getString("id"));
                    }
                    if(jsonObject.has("nameRU")){
                        film.setNameRU(jsonObject.getString("nameRU"));
                    }
                    if(jsonObject.has("nameEN")){
                        film.setNameEN(jsonObject.getString("nameEN"));
                    }
                    if(jsonObject.has("year")){
                        film.setYear(jsonObject.getString("year"));
                    }
                    if(jsonObject.has("cinemaHallCount")){
                        film.setCinemaHallCount(jsonObject.getString("cinemaHallCount"));
                    }
                    if(jsonObject.has("is3D")){
                        film.setIs3D(jsonObject.getString("is3D"));
                    }
                    if(jsonObject.has("posterURL")){
                        film.setPosterURL(jsonObject.getString("posterURL"));
                    }
                    if(jsonObject.has("filmLength")){
                        film.setFilmLength(jsonObject.getString("filmLength"));
                    }
                    if(jsonObject.has("country")){
                        film.setCountry(jsonObject.getString("country"));
                    }
                    if(jsonObject.has("genre")){
                        film.setGenre(jsonObject.getString("genre"));
                    }
                    if(jsonObject.has("filmLength")){
                        film.setFilmLength(jsonObject.getString("filmLength"));
                    }
                    if(jsonObject.has("premiereRU")){
                        film.setPremiereRU(jsonObject.getString("premiereRU"));
                    }
                    if(jsonObject.has("slogan")){
                        film.setSlogan(jsonObject.getString("slogan"));
                    }
                    if(jsonObject.has("description")){
                        film.setDescription(jsonObject.getString("description"));
                    }
                    if(jsonObject.has("ratingAgeLimits")){
                        film.setRatingAgeLimits(jsonObject.getString("ratingAgeLimits"));
                    }
                    if(jsonObject.has("ratingMPAA")){
                        film.setRatingMPAA(jsonObject.getString("ratingMPAA"));
                    }
                    if(jsonObject.has("ratingData")){
                        RatingData ratingData=new RatingData();
                        JSONObject jsonObjectDataRating=jsonObject.getJSONObject("ratingData");
                        if(jsonObjectDataRating.has("ratingGoodReview")){
                            ratingData.setRatingGoodReview(jsonObjectDataRating.getString("ratingGoodReview"));
                        }
                        if(jsonObjectDataRating.has("ratingGoodReviewVoteCount")){
                            ratingData.setRatingGoodReviewCount(jsonObjectDataRating.getString("ratingGoodReviewVoteCount"));
                        }
                        if(jsonObjectDataRating.has("rating")){
                            ratingData.setRating(jsonObjectDataRating.getString("rating"));
                        }
                        if(jsonObjectDataRating.has("ratingVoteCount")){
                            ratingData.setRatingVoteCount(jsonObjectDataRating.getString("ratingVoteCount"));
                        }
                        if(jsonObjectDataRating.has("ratingAwait")){
                            ratingData.setRatingAwait(jsonObjectDataRating.getString("ratingAwait"));
                        }
                        if(jsonObjectDataRating.has("ratingAwaitCount")){
                            ratingData.setRatingAwaitCount(jsonObjectDataRating.getString("ratingAwaitCount"));
                        }
                        if(jsonObjectDataRating.has("ratingFilmCritics")){
                            ratingData.setRatingFilmCritics(jsonObjectDataRating.getString("ratingFilmCritics"));
                        }
                        if(jsonObjectDataRating.has("ratingFilmCriticsVoteCount")){
                            ratingData.setRatingFilmCriticsVoteCounts(jsonObjectDataRating.getString("ratingFilmCriticsVoteCount"));
                        }
                        film.setRatingData(ratingData);
                    }
                }
            }catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return film;
        }
        @Override
        protected void onPostExecute(Film jsonArray) {
            getSupportActionBar().setTitle(film.nameRU);
            TextView tvName=(TextView)findViewById(R.id.tvName);
            TextView tvEnName=(TextView)findViewById(R.id.tvEnName);
            TextView tvGenre=(TextView)findViewById(R.id.tvGenre);
            TextView tvCountryYear=(TextView)findViewById(R.id.tvCountryYear);
            TextView tvSlogan=(TextView)findViewById(R.id.tvSlogan);
            TextView tvRatingAgeLimits=(TextView)findViewById(R.id.tvRatingAgeLimits);
            TextView tvDescription=(TextView)findViewById(R.id.tvDescription);

            TextView tvRating=(TextView)findViewById(R.id.tvRating);
            TextView tvRatingCount=(TextView)findViewById(R.id.tvRatingCount);
            TextView tvRatingCritics=(TextView)findViewById(R.id.tvRatingCritics);
            TextView tvRatingCriticsCount=(TextView)findViewById(R.id.tvRatingCriticsCount);
            TextView tvRatingAwait=(TextView)findViewById(R.id.tvRaitingWait);
            TextView tvRatingAwaitCount=(TextView)findViewById(R.id.tvRatingWaitCount);
            TextView tvRatingGood=(TextView)findViewById(R.id.tvRatingGoodReview);
            TextView tvRatingGoodCount=(TextView)findViewById(R.id.tvRatingGoodReviewCount);

            if(film.nameRU!=null){
                tvName.setText(film.nameRU);
            }
            if(film.nameEN!=null&&film.year!=null){
                tvEnName.setText(film.nameEN+" ("+film.year+")");
            }
            if(film.nameEN!=null&&film.year==null){
                tvEnName.setText(film.nameEN);
            }
            if(film.nameEN==null&&film.year!=null){
                tvEnName.setText("("+film.year+")");
            }
            if(film.genre!=null){
                tvGenre.setText(film.genre);
            }
            if(film.country!=null){
                tvCountryYear.setText(film.country);
            }
            if(film.slogan!=null){
                tvSlogan.setText(film.slogan);
            }
            if(film.ratingAgeLimits!=null){
                tvRatingAgeLimits.setText(film.ratingAgeLimits+"+");
            }
            if(film.description!=null){
                tvDescription.setText(film.description);
            }
            if(film.ratingData!=null){
                if(film.ratingData.rating!=null){
                    tvRating.setText("Рейтинг КиноПоиска: "+film.ratingData.rating);
                }
                if(film.ratingData.ratingVoteCount!=null){
                    tvRatingCount.setText(film.ratingData.ratingVoteCount);
                }

                if(film.ratingData.ratingAwait!=null){
                    tvRatingAwait.setText("Рейтинг ожидания: "+film.ratingData.ratingAwait);
                }
                if(film.ratingData.ratingAwaitCount!=null){
                    tvRatingAwaitCount.setText(film.ratingData.ratingAwaitCount);
                }

                if(film.ratingData.ratingFilmCritics!=null){
                    tvRatingCritics.setText("Рейтинг кинокритиков: "+film.ratingData.ratingFilmCritics);
                }
                if(film.ratingData.ratingFilmCriticsVoteCounts!=null){
                    tvRatingCriticsCount.setText(film.ratingData.ratingFilmCriticsVoteCounts);
                }

                if(film.ratingData.ratingGoodReview!=null){
                    tvRatingGood.setText("Положительные рецензии: "+film.ratingData.ratingGoodReview);
                }
                if(film.ratingData.ratingGoodReviewCount!=null){
                    tvRatingGoodCount.setText(film.ratingData.ratingGoodReviewCount);
                }

            }
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
