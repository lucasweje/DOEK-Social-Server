package server.models;

import java.sql.Date;
import java.sql.Timestamp;

public class Event {

    private int idEvent, price, idStudent;
    private String eventName, location, eventDate;
    private String description;

    public Event(int idEvent, int price, int idStudent, String eventName, String location, String description, String eventDate) {
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
      public void setPrice(int price) {
        this.price = price;
    }

    public int getidStudent() {
        return idStudent;
    }
  
      public void setIdStudent(int idStudent) {
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

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
