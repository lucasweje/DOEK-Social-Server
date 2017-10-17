package server.endpoints;

import javax.ws.rs.Path;


import com.google.gson.Gson;
import com.sun.org.apache.regexp.internal.RE;
import server.controllers.EventController;
import server.models.Event;
import server.models.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/events")
public class EventEndpoint {

    //Har udkommenteret mange linjer kode for at det ikke fejler

    //eventTable skal skiftes til rigtigt variable navn
//    EventTable eventTable = EventTable.getInstance();
//    ArrayList<Event> events = evenTable.getEvents();

   /* @GET*/
   /* public Response getEvents() throws Exception {*/
/*
*/

        //kald en metode der henter alle brugere fra databasen (gemmer dem i en ArrayList??)
   /*     ArrayList<Event> events = eventController.getAllEvents();*/
   /*     if (events != null) {*/
   /*         return Response*/
   /*                 .status(200)*/
   /*                 .type("application/json")*/
   /*                 .entity(new Gson().toJson(events))*/
   /*                 .build();*/
   /*     } else {*/
   /*         return Response*/
   /*                 .status(400)*/
   /*                 .type("application/json")*/
   /*                 .entity("{\"getEvents\":\"failed\"}")*/
   /*                 .build();*/
   /*     }*/
   /* }*/

    EventController eventcontroller = new EventController();

    @POST
    @Produces("Application/json")
    public Response createEvent(String data) throws SQLException {

        Gson gson = new Gson();
        Event event = gson.fromJson(data, Event.class);

        EventController eventController = new EventController();
        if (eventController.createEvent(event)) {
            return Response
                    .status(200)
                    .entity("{message\":\"Success! Event created\"}")
                    .build();
        } else {
            return Response.status(400).entity("{\"message\":\"failed\"}").build();
        }
    }

    @DELETE
    public Response deleteEvent (String data) throws Exception {

        Gson gson = new Gson();
        Event event = gson.fromJson(data, Event.class);

        if (eventcontroller.deleteEvent(event)) {
            return Response.status(200).entity("{\"message\":\"Success! Event deleted\"}").build();

        } else return Response.status(400).entity("{\"message\":\"failed\"}").build();

        }

    }

}