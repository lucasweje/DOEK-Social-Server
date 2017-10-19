package server.endpoints;

import javax.ws.rs.Path;

import com.google.gson.Gson;
import com.sun.org.apache.regexp.internal.RE;
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

    //  EventTable eventTable = new EventTable();
/*
    //Har udkommenteret mange linjer kode for at det ikke fejler

    //eventTable skal skiftes til rigtigt variable navn
//    EventTable eventTable = EventTable.getInstance();
//    ArrayList<Event> events = evenTable.getEvents();

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
*/
    @DELETE
    @Path("{idEvent}/studentEvents")
    public Response deleteEvent(String data) throws Exception {

        Gson gson = new Gson();
        Event event = gson.fromJson(data, Event.class);

        if (eventController.deleteEvent(event)) {
            return Response.status(200)
                    .entity("{\"message\":\"Success! Event deleted\"}")
                    .build();

        } else {
            return Response.status(400)
                    .entity("{\"message\":\"failed\"}")
                    .build();
        }
    }

}
/*

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

        @GET
        @Path("{idEventStudents}/students")
        public Response getAttendingStudents(@PathParam("idEventStudents")String idEvent) {

            EventTable eventTable = new EventTable();
            ArrayList foundAttendingStudents = null;

            if (idEvent.isEmpty()) {
                return Response
                        .status(400)
                        .entity("{\"Missing Student ID\":\"true\"}")
                        .build();
            }else{
                try {
                    foundAttendingStudents = eventTable.getAttendingStudents(idEvent);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                // If student not found:
                if (!true) {
                    return Response
                            .status(400)
                            .entity("{\"Student not found\":\"true\"}")
                            .build();
                }
                return Response
                        .status(200)
                        .type("application/json")
                        .entity(new Gson().toJson(foundAttendingStudents))
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

        @PUT
        @Path("/events")
        public Response updateEvent(String eventJson) throws Exception {

        }*/