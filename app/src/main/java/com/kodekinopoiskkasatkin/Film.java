package com.kodekinopoiskkasatkin;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.util.Objects;

/**
 * Created by root on 11.10.16.
 */
public class Film  implements Comparable{
    String type;
    String id;
    String nameRU;
    String nameEN;
    String year;
    String cinemaHallCount;
    String is3D;
    Double rating;
    String posterURL;
    String filmLength;
    String country;
    ArrayList<String> genre;
    String premiereRU;
    String description;
    String slogan;
    String ratingAgeLimits;
    RatingData ratingData;
    String ratingMPAA;


    public void setCinemaHallCount(String cinemaHallCount) {
        this.cinemaHallCount = cinemaHallCount;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setRatingMPAA(String ratingMPAA) {
        this.ratingMPAA = ratingMPAA;
    }

    public void setFilmLength(String filmLength) {
        this.filmLength = filmLength;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGenre(ArrayList<String> genre) {
        this.genre = genre;
    }

    public void setIs3D(String is3D) {
        this.is3D = is3D;
    }

    public void setNameEN(String nameEN) {
        this.nameEN = nameEN;
    }

    public void setNameRU(String nameRU) {
        this.nameRU = nameRU;
    }

    public void setPosterURL(String posterURL) {
        this.posterURL = posterURL;
    }

    public void setPremiereRU(String premiereRU) {
        this.premiereRU = premiereRU;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getCinemaHallCount() {
        return cinemaHallCount;
    }

    public String getCountry() {
        return country;
    }

    public String getFilmLength() {
        return filmLength;
    }

    public ArrayList<String> getGenre() {
        return genre;
    }

    public String getId() {
        return id;
    }

    public String getIs3D() {
        return is3D;
    }

    public String getNameEN() {
        return nameEN;
    }

    public String getNameRU() {
        return nameRU;
    }

    public String getPosterURL() {
        return posterURL;
    }

    public String getPremiereRU() {
        return premiereRU;
    }

    public Double getRating() {
        return rating;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRatingAgeLimits(String ratingAgeLimits) {
        this.ratingAgeLimits = ratingAgeLimits;
    }

    public void setRatingData(RatingData ratingData) {
        this.ratingData = ratingData;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public RatingData getRatingData() {
        return ratingData;
    }

    public String getDescription() {
        return description;
    }

    public String getRatingAgeLimits() {
        return ratingAgeLimits;
    }

    public String getSlogan() {
        return slogan;
    }

    public String getType() {
        return type;
    }

    public String getYear() {
        return year;
    }

    public int compareTo(Object o1) {
        int i ;
        Film l=(Film) o1;
        if (this.rating ==l.rating)
            i= 0;
        else if (this.rating > l.rating)
            i= 1;
        else
            i= -1;
        return i;
    }
}

