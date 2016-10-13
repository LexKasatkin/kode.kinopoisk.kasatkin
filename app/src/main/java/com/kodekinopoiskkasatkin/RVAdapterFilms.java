package com.kodekinopoiskkasatkin;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by lex on 10.05.16.
 */
class RVAdapterFilms extends RecyclerView.Adapter<RVAdapterFilms.FilmsViewHolder>{
    public static class FilmsViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView filmName;
        TextView filmID;
        TextView filmYear;
        TextView filmDesc;
        ImageView ivPoster;
        TextView filmRate;
        FilmsViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            filmName = (TextView)itemView.findViewById(R.id.tvFilmName);
            filmID = (TextView)itemView.findViewById(R.id.tvFilmID);
            filmYear=(TextView)itemView.findViewById(R.id.tvFilmYear);
            filmRate=(TextView)itemView.findViewById(R.id.tvRate);
            ivPoster=(ImageView)itemView.findViewById(R.id.iv);
            filmDesc=(TextView)itemView.findViewById(R.id.tvFilmDescription);
        }
    }


    ArrayList<Film> films;
    RVAdapterFilms(ArrayList<Film> films){
        this.films = films;
    }


    @Override
    public int getItemCount() {
        return films.size();
    }


    @Override
    public FilmsViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_context,  viewGroup, false);
        FilmsViewHolder pvh = new FilmsViewHolder(v);
        return pvh;
    }


    @Override
    public void onBindViewHolder(final FilmsViewHolder filmsViewHolder, int i) {
        if(films.get(i).getNameRU()!=null) {
            filmsViewHolder.filmName.setText(films.get(i).getNameRU());
        }
        if(films.get(i).getYear()!=null&&films.get(i).getNameEN()!=null) {
            filmsViewHolder.filmYear.setText(films.get(i).getNameEN()+" ("+films.get(i).getYear()+")");
        }
        if(films.get(i).genre!=null) {
            filmsViewHolder.filmDesc.setText(films.get(i).genre);
        }
        if(films.get(i).rating!=0) {
            filmsViewHolder.filmRate.setText(String.valueOf(films.get(i).rating));
        } else if(films.get(i).rating==0){
            filmsViewHolder.filmRate.setText("Нет рейтинга");
        }
        filmsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id=filmsViewHolder.filmID.getText().toString();
                Context context = v.getContext();
                Intent intent = new Intent(context, FilmActivity.class);
                intent.putExtra("id", id);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}