package server.models;

import java.sql.Timestamp;

public class Event {

    private int price;
    private String eventName, location, description, idEvent, idStudent;
    private Timestamp date;

    public Event(String idEvent, int price, String idStudent, String eventName, String location, String description, Timestamp date) {
        this.idEvent = idEvent;
        this.price = price;
        this.idStudent = idStudent;
        this.eventName = eventName;
        this.location = location;
        this.description = description;
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

    public void setPrice(int price) {
        this.price = price;
    }

    public String getidStudent() {
        return idStudent;
    }

    public void setIdStudent() {
        this.idStudent = idStudent;
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


    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
