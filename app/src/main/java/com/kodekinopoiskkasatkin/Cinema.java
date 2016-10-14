package com.kodekinopoiskkasatkin;

import java.util.ArrayList;

/**
 * Created by root on 14.10.16.
 */
public class Cinema {
    String cinemaID;
    String address;
    String lon;
    String lat;
    String cinemaName;
    ArrayList<String> time;

    Cinema(){
        time=new ArrayList<String>();
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCinemaID(String cinemaID) {
        this.cinemaID = cinemaID;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public void setTime(ArrayList<String> time) {
        this.time = time;
    }

    public ArrayList<String> getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public String getCinemaID() {
        return cinemaID;
    }

    public String getCinemaName() {
        return cinemaName;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
