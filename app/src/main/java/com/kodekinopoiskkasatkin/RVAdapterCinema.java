package com.kodekinopoiskkasatkin;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
class RVAdapterCinema extends RecyclerView.Adapter<RVAdapterCinema.CinemaViewHolder>{
    public static class CinemaViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView filmName;
        TextView filmID;
        TextView filmYear;
        TextView filmDesc;
        ImageView ivMap;
        TextView filmRate;
        CinemaViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            filmName = (TextView)itemView.findViewById(R.id.tvFilmName);
            filmID = (TextView)itemView.findViewById(R.id.tvFilmID);
            filmYear=(TextView)itemView.findViewById(R.id.tvFilmYear);
            filmRate=(TextView)itemView.findViewById(R.id.tvRate);
            ivMap=(ImageView)itemView.findViewById(R.id.ivMap);
            filmDesc=(TextView)itemView.findViewById(R.id.tvFilmDescription);
            filmDesc.setBackgroundColor(R.color.colorPrimary);
            filmDesc.setTextSize(20);
            filmDesc.setTextColor(Color.GREEN);
        }
    }


    ArrayList<Cinema> cinemaArrayList;
    RVAdapterCinema(ArrayList<Cinema> cinemaArrayList){
        this.cinemaArrayList = cinemaArrayList;
    }


    @Override
    public int getItemCount() {
        return cinemaArrayList.size();
    }


    @Override
    public CinemaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_context,  viewGroup, false);
        CinemaViewHolder pvh = new CinemaViewHolder(v);
        return pvh;
    }


    @Override
    public void onBindViewHolder(final CinemaViewHolder cinemaViewHolder, int i) {
        cinemaViewHolder.ivMap.setImageResource(R.drawable.map);
        if(cinemaArrayList.get(i).getCinemaName()!=null) {
            cinemaViewHolder.filmName.setText(cinemaArrayList.get(i).getCinemaName());
        }
        if(cinemaArrayList.get(i).getAddress()!=null) {
            cinemaViewHolder.filmYear.setText(cinemaArrayList.get(i).getAddress());
        }
        if(cinemaArrayList.get(i).getLon()!=null) {
            cinemaViewHolder.filmID.setText(cinemaArrayList.get(i).getLon());
        }
        if(cinemaArrayList.get(i).getLat()!=null) {
            cinemaViewHolder.filmRate.setText(cinemaArrayList.get(i).getLat());
            cinemaViewHolder.filmRate.setTextColor(Color.WHITE);
        }
        String s=new String();
        for(int j=0;j<cinemaArrayList.get(i).getTime().size();j++){
            s=s+"   "+cinemaArrayList.get(i).getTime().get(j)+"   ";
        }
        cinemaViewHolder.filmDesc.setText(s);
//        cinemaViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String id=cinemaViewHolder.filmID.getText().toString();
//                Context context = v.getContext();
//                Intent intent = new Intent(context, FilmActivity.class);
//                intent.putExtra("id", id);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}