package com.appdevelopers.seanwade.familymap.model;

import java.io.Serializable;

/**
 * Created by seanwade on 3/16/16.
 */
public class Event implements Serializable {

    private String eventId;
    private String personId;
    private String latitude;
    private String longitude;
    private String country;
    private String city;
    private String description;
    private String year;
    private String descendant;


    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDescendant() {
        return descendant;
    }

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    @Override
    public String toString() {
        return description + ": " + country + ", " + city + "(" + String.valueOf(year) + ")";
    }
}
