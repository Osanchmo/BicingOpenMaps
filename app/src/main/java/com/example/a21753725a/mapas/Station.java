package com.example.a21753725a.mapas;

/**
 * Created by 21753725a on 31/01/17.
 */

public class Station {
    private String id;
    private boolean mecanic;
    private String lat;
    private String lon;
    private String streetName;
    private String altitude;
    private String slots;
    private String bikes;
    private String status;

    public String getBikes() {
        return bikes;
    }

    public void setBikes(String bikes) {
        this.bikes = bikes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }

    public String getAltitude() {
        return altitude;
    }

    public void setAltitude(String altitude) {
        this.altitude = altitude;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public boolean isMecanic() {
        return mecanic;
    }

    public void setMecanic(boolean mecanic) {
        this.mecanic = mecanic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
