package com.kodekinopoiskkasatkin;

/**
 * Created by root on 11.10.16.
 */
public class Film {
    String type;
    String id;
    String nameRU;
    String nameEN;
    String year;
    String cinemaHallCount;
    String is3D;
    String rating;
    String posterURL;
    String filmLength;
    String country;
    String genre;
    String premiereRU;


    public void setCinemaHallCount(String cinemaHallCount) {
        this.cinemaHallCount = cinemaHallCount;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setFilmLength(String filmLength) {
        this.filmLength = filmLength;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setGenre(String genre) {
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

    public void setRating(String rating) {
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

    public String getGenre() {
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

    public String getRating() {
        return rating;
    }

    public String getType() {
        return type;
    }

    public String getYear() {
        return year;
    }
}
