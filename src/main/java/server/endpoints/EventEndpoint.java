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
import server.resources.Log;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/events")
public class EventEndpoint {

    EventController eventController = new EventController();
    EventTable eventTable = new EventTable();

    //Skal bruges til at opdatere events (her bruges PUT)
    @PUT
    @Path("{idEvent}/updateEvents")
    public Response updateEvent(@PathParam("idEvent") String eventId, String data) throws Exception {

        Gson gson = new Gson();
        Event event = gson.fromJson(data, Event.class);
        event.setIdEvent(Integer.parseInt(eventId));

        if (eventController.updateEvent(event)) {


            //getIdEvent eller getEventName?
            Log.writeLog(getClass().getName(), this, "Event with ID: " + event.getIdEvent() + " has been updated", 0);

            return Response
                    .status(200)
                    .entity("{\"Message\":\"Success! Event updated\"}")
                    .build();

        } else
            Log.writeLog(getClass().getName(), this, "Event not found", 2);

        return Response

                //Bør det ikke være 404 og ikke 400? jf. https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/400
                //Ikke kun her
                .status(404)
                .entity("{\"Message\":\"Failed. No such event!\"}")
                .build();
    }

    @POST
    public Response createEvent(String eventData) throws SQLException {

        // OBS mangler token, som finder id på 'currentStudent'
        Event event = new Gson().fromJson(eventData, Event.class);

        EventController eventController = new EventController();
        if (eventController.createEvent(event)) {

            Log.writeLog(getClass().getName(), this, event.getEventName() + " created", 0);

            return Response
                    .status(200)
                    .type("application/json")
                    .entity("{message\":\"Success! Event created\"}")
                    .build();
        } else {

            Log.writeLog(getClass().getName(), this, "Not able to create event", 2);


            return Response
                    .status(404)
                    .type("application/json")
                    .entity("{\"message\":\"failed\"}")
                    .build();
        }
    }

    @DELETE
    @Path("{idEvent}/studentEvents")
    public Response deleteEvent(String data) throws Exception {

        EventController eventController = new EventController();
        Gson gson = new Gson();
        Event event = gson.fromJson(data, Event.class);

        if (eventController.deleteEvent(event)) {

            Log.writeLog(getClass().getName(), this, "Event deleted", 0);


            return Response.status(200)
                    .entity("{\"message\":\"Success! Event deleted\"}")
                    .build();

        } else {

            Log.writeLog(getClass().getName(), this, "Event not deleted", 2);

            return Response.status(400)
                    .entity("{\"message\":\"failed\"}")
                    .build();
        }
    }


    @GET
    public Response getEvents() {

        EventController eventController = new EventController();


        //kald en metode der henter alle ?brugere? fra databasen (gemmer dem i en ArrayList??)
        try {

            Log.writeLog(getClass().getName(), this, "All events fetched", 0);

            return Response
                    .status(200)
                    .type("application/json")
                    .entity(new Gson().toJson(eventController.getAllEvents()))
                    .build();
        } catch (SQLException e) {

            ErrorMessage message = new ErrorMessage();
            message.setStatus(500);
            message.setError(e.getMessage());

            Log.writeLog(getClass().getName(), this, "Internal sever error", 2);

            return Response
                    .status(500)
                    .type("application/json")
                    .entity(new Gson().toJson(message))
                    .build();
        }
    }

    @GET
    @Path("{idEventStudents}/students")
    public Response getAttendingStudents(@PathParam("idEventStudents") String idEvent) {

        EventTable eventTable = new EventTable();
        ArrayList<Student> foundAttendingStudents = null;

        if (idEvent.isEmpty()) {

            Log.writeLog(getClass().getName(), this, "Attending students not found", 2);

            return Response
                    .status(400)
                    .entity("{\"Missing Student ID\":\"true\"}")
                    .build();
        } else {
            try {
                foundAttendingStudents = eventTable.getAttendingStudents(idEvent);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // If student not found:
            if (!true) {
                Log.writeLog(getClass().getName(), this, "Student not found", 2);
                return Response
                        .status(400)
                        .entity("{\"Student not found\":\"true\"}")
                        .build();
            }
            Log.writeLog(getClass().getName(), this, "Attending students fetched", 0);
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
            eventController.joinEvent(studentHasEvent.getIdEvent(), studentHasEvent.getStudent_idStudent());

            Log.writeLog(getClass().getName(), this, "Event joined", 0);
            return Response
                    .status(200)
                    .type("application/json")
                    .entity("ok")
                    .build();

        } catch (ResponseException e) {

            ErrorMessage message = new ErrorMessage();
            message.setError(e.getMessage());
            message.setStatus(e.getStatus());

            Log.writeLog(getClass().getName(), this, "Not able to join event - " + e.getStatus() + " " + e.getMessage(), 2);

            return Response
                    .status(e.getStatus())
                    .type("application/json")
                    .entity(new Gson().toJson(message))
                    .build();
        }


    }
}