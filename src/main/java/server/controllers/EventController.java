package server.controllers;

import com.google.gson.Gson;
import com.sun.java.accessibility.util.EventID;
import server.exceptions.ResponseException;
import server.models.Event;
import server.models.Student;
import server.providers.DBmanager;
import server.endpoints.EventEndpoint;


import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;

import server.providers.EventTable;

/**
 * Created by STFU on 19/10/2017
 */


public class EventController {

    /**
     * EventController klassen forbinder vores endpoints med vores providers,
     * som sender SQL statements til databasen
     */
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
*/


   EventTable eventTable = new EventTable();

    public ArrayList getAttendingStudents(String idEvent) throws IllegalAccessException {
        ArrayList attendingStudents = eventTable.getAttendingStudents(idEvent);
        return attendingStudents;
    }

    /*
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
    /*
    public boolean createEvent(Event data) throws SQLException {
        EventTable db = new EventTable();
        boolean createEvent = db.createEvent(data);
        return createEvent;
    }*/



    public ArrayList<Event> getAllEvents() throws SQLException {
        EventTable allEvents = new EventTable();
        return allEvents.getAllEvents();

    }

    /*
    public boolean deleteEvent(Event event) throws Exception {
        EventTable db = new EventTable();
        boolean deleteEvent = db.deleteEvent(event);
        return deleteEvent;
    }*/

    public boolean joinEvent(String idEvent, String idStudent) throws ResponseException {
        EventTable joinEvent = new EventTable();
        return joinEvent.joinEvent(idEvent, idStudent);
    }
}

