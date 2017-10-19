package server.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Event {

    private int idEvent, price, studentId;
    private String eventName, location, description;
    private Date eventDate;

    public Event(int idEvent, int price, int studentId, String eventName, String location, String description, Date eventDate) {
        this.idEvent = idEvent;
        this.price = price;
        this.studentId = studentId;
        this.eventName = eventName;
        this.location = location;
        this.description = description;
        this.eventDate = eventDate;
    }


    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStudentId() {
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


    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }
}
