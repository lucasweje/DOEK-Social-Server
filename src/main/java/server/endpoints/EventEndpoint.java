package server.endpoints;

import javax.ws.rs.Path;


import com.google.gson.Gson;
import server.controllers.EventController;
import server.exceptions.ErrorMessage;
import server.exceptions.ResponseException;
import server.models.Event;
import server.models.Student;
import server.models.StudentHasEvent;
import server.providers.EventTable;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/events")
public class EventEndpoint {

    EventController eventController = new EventController();
    EventTable eventTable = new EventTable();

    //Har udkommenteret mange linjer kode for at det ikke fejler

    //eventTable skal skiftes til rigtigt variable navn
//    EventTable eventTable = EventTable.getInstance();
//    ArrayList<Event> events = evenTable.getEvents();

    @GET
    public Response getEvents() {

        //kald en metode der henter alle brugere fra databasen (gemmer dem i en ArrayList??)
        try {
            return Response
                    .status(200)
                    .type("application/json")
                    .entity(new Gson().toJson(eventController.getAllEvents()))
                    .build();
        } catch (SQLException e) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(500);
            message.setError(e.getMessage());

            return Response
                    .status(500)
                    .type("application/json")
                    .entity(new Gson().toJson(message))
                    .build();
        }


    }

    @POST
    @Path("/join")
    public Response joinEvent(String eventJson) {

        EventController eventController = new EventController();
        StudentHasEvent studentHasEvent = new Gson().fromJson(eventJson, StudentHasEvent.class);

        try {
            eventController.joinEvent(studentHasEvent.getEvent_idEvent(), studentHasEvent.getStudent_idStudent());
            return Response
                    .status(200)
                    .type("application/json")
                    .entity("ok")
                    .build();

        } catch (ResponseException e) {

            ErrorMessage message = new ErrorMessage();
            message.setError(e.getMessage());
            message.setStatus(e.getStatus());

            return Response
                    .status(e.getStatus())
                    .type("application/json")
                    .entity(new Gson().toJson(message))
                    .build();
        }


    }
        //Skal bruges til at opdatere events (her bruges PUT)
        /*
        @PUT
        @Path("/events")
        public Response updateEvent(String eventJson) throws Exception {

        }*/
}


