package server.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Event {

    private int idEvent, price, idStudent;
    private String eventName, location;
    private Date eventDate;
    private Long description;

    public Event(int idEvent, int price, int idStudent, String eventName, String location, Long description, Date eventDate) {

        this.idEvent = idEvent;
        this.price = price;
        this.idStudent = idStudent;
        this.eventName = eventName;
        this.location = location;
        this.description = description;
        this.eventDate = eventDate;
    }

    public Event() {
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

    public int getidStudent() {
        return idStudent;
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

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public Long getDescription() {
        return description;
    }

    public void setDescription(Long description) {
        this.description = description;
    }
}
