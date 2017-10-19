package server.endpoints;

import javax.ws.rs.Path;


import com.google.gson.Gson;
import server.controllers.EventController;
import server.models.Event;
import server.providers.EventTable;
import server.providers.StudentTable;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

    @Path("/events")
    public class EventEndpoint {

        EventController eventController = new EventController();

        //Har udkommenteret mange linjer kode for at det ikke fejler

        //eventTable skal skiftes til rigtigt variable navn
//    EventTable eventTable = EventTable.getInstance();
//    ArrayList<Event> events = evenTable.getEvents();

        @GET
        public Response getEvents() throws Exception {

            //kald en metode der henter alle brugere fra databasen (gemmer dem i en ArrayList??)
            ArrayList<Event> events = eventController.getAllEvents();
            if (events != null) {
                return Response
                        .status(200)
                        .type("application/json")
                        .entity(new Gson().toJson(events))
                        .build();
            } else {
                return Response
                        .status(400)
                        .type("application/json")
                        .entity("{\"getEvents\":\"failed\"}")
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
    }
