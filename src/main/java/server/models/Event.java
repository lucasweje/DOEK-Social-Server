package server.models;

import java.sql.Timestamp;

public class Event {

    private int price;
    private String eventName, location, description, pictures, idEvent, studentId;
    private Timestamp date;

    public Event(String idEvent, int price, String studentId, String eventName, String location, String description, String pictures, Timestamp date) {
        this.idEvent = idEvent;
        this.price = price;
        this.studentId = studentId;
        this.eventName = eventName;
        this.location = location;
        this.description = description;
        this.pictures = pictures;
        this.date = date;
    }


    public String getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(String idEvent) {
        this.idEvent = idEvent;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId() {
        this.studentId = studentId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPictures() {
        return pictures;
    }

    public void setPictures(String pictures) {
        this.pictures = pictures;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
