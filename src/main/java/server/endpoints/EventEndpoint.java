package server.endpoints;

import javax.ws.rs.Path;

import com.google.gson.Gson;
import server.controllers.EventController;
import server.controllers.MainController;
import server.exceptions.ErrorMessage;
import server.exceptions.ResponseException;
import server.models.Event;
import server.models.Student;
import server.models.StudentHasEvent;
import server.providers.EventTable;
import server.resources.Log;
import server.utility.Crypter;
import server.utility.CurrentStudentContext;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/events")
public class EventEndpoint {

    EventController eventController = new EventController();
    EventTable eventTable = new EventTable();
    MainController mainController = new MainController();


    //Skal bruges til at opdatere events (her bruges PUT)
    @PUT
    @Path("{idEvent}/update-event")
    public Response updateEvent(@HeaderParam("Authorization") String token, @PathParam("idEvent") int eventId, String data) throws Exception {

        CurrentStudentContext student = mainController.getStudentFromTokens(token);
        Student currentStudent = student.getCurrentStudent();
        if (student.getCurrentStudent() != null) {

            Gson gson = new Gson();
            Event event = gson.fromJson(data, Event.class);
            event.setIdEvent(eventId);

            if (eventController.updateEvent(event, currentStudent)) {


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
                    .status(403)
                    .entity("Either the event wasn't found, or you aren't the owner of the event and therefore you cannot update it")
                    .build();
        } else {
            return Response
                    .status(403)
                    .type("plain/text")
                    .entity("{You are not logged in - please log in before attempting to update an event}")
                    .build();
        }

    }

    @POST
    public Response createEvent(@HeaderParam("Authorization") String token, String eventData) throws SQLException {

        CurrentStudentContext student = mainController.getStudentFromTokens(token);

        if (student.getCurrentStudent() != null) {

            // OBS mangler token, som finder id på 'currentStudent'
            Event event = new Gson().fromJson(eventData, Event.class);

            EventController eventController = new EventController();
            if (eventController.createEvent(event, student.getCurrentStudent())) {

                Log.writeLog(getClass().getName(), this, event.getEventName() + " created", 0);

                return Response
                        .status(200)
                        .type("application/json")
                        .entity("{message\":\"Success! Event created\"}")
                        .build();
            } else {

                Log.writeLog(getClass().getName(), this, "Not able to create event", 2);


                return Response
                        .status(403)
                        .type("application/json")
                        .entity("{\"message\":\"failed\"}")
                        .build();
            }
        }
        return Response
                .status(403)
                .type("application/json")
                .entity("{You are not logged in - please log in before attempting to create an event}")
                .build();
    }

    @PUT
    @Path("{idEvent}/delete-event")
    public Response deleteEvent(@PathParam("idEvent") String eventId, String data) throws Exception {

        Gson gson = new Gson();
        Event event = gson.fromJson(data, Event.class);
        event.setIdEvent(Integer.parseInt(eventId));

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

        String json = new Gson().toJson(eventController.getAllEvents());

        String crypted = Crypter.encryptDecrypt(json);


        //kald en metode der henter alle ?brugere? fra databasen (gemmer dem i en ArrayList??)
        try {

            Log.writeLog(getClass().getName(), this, "All events fetched", 0);

            return Response
                    .status(200)
                    .type("application/json")
                    .entity(crypted)
                    .build();
        } catch (Exception e) {

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
    @Path("{idStudents}/students")
    public Response getAttendingStudents(@PathParam("idEventStudents") String idEvent) throws SQLException, IllegalAccessException {

        EventTable eventTable = new EventTable();
        ArrayList<Student> foundAttendingStudents = null;

        if (idEvent.isEmpty()) {

            Log.writeLog(getClass().getName(), this, "Event not found", 2);

            return Response
                    .status(400)
                    .entity("{\"Missing Event ID\":\"true\"}")
                    .build();
        } else {
            foundAttendingStudents = eventTable.getAttendingStudents(idEvent);
            // If student not found:
            if (foundAttendingStudents.isEmpty()) {
                Log.writeLog(getClass().getName(), this, "No attending students at event", 2);
                return Response
                        .status(400)
                        .entity("{No attending students}")
                        .build();
            } else {
                String json = new Gson().toJson(foundAttendingStudents);
                String crypted = Crypter.encryptDecrypt(json);
                Log.writeLog(getClass().getName(), this, "Attending students fetched", 0);
                return Response
                        .status(200)
                        .type("application/json")
                        .entity(crypted)
                        .build();
            }
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