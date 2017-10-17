package server.controllers;

import com.google.gson.Gson;
import server.models.Event;
import server.providers.DBmanager;
import server.endpoints.EventEndpoint;


import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import server.providers.EventTable;


public class EventController {
/*

    //Udkommenteret indtil det virker således at DBManager mv kan testes
    //Controller klasse for event. Laver kald til databasen.
    //API forbindes med database.

    //Hedder vores database connector "DBConnector"?
    public ArrayList<Event> getEvent() throws Exception {
        DBConnector db = new DBConnector();
        ArrayList<Event> events = db.getEvents();
        db.close();
        return events;
    }



    public Event getEvent(int id) throws Exception {
        DBConnnector db = new DBConnector();
        Event event = db.getEvent(id);
        db.close();
        return event;

    }


}

    //Hvilke parametre skal updateEvent indeholde?
    public Boolean updateEvent(int id, String xxx, String xxx) throws Exception {
        DBConnector db = new DBConnector();
        //Indsæt parametre
        boolean updateEvent = db.updateEvent(id, xxx);
        db.close();
        return updateEvent;
    }

*/
    public boolean createEvent(Event data) throws SQLException {
        EventTable db = new EventTable();
        boolean createEvent = db.createEvent(data);
        return createEvent;
    }

    /*public Event getAllEvents(int id) throws Exception {
        DBmanager db = new DBmanager();
        Event allEvents = db.getAllEvents(id);
        return allEvents;
    }

    public ArrayList<Event> getAllEvents() throws Exception {
        EventTable allEvents = new EventTable();
        return allEvents.getAllEvents();

    }*/

    public boolean deleteEvent(Event event) throws Exception {
        EventTable db = new EventTable();
        boolean deleteEvent = db.deleteEvent(event);
        return deleteEvent;


    }
}

