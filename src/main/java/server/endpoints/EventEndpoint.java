package server.endpoints;

import javax.ws.rs.Path;


import com.google.gson.Gson;
import server.controllers.EventController;
import server.models.Event;
import server.models.Student;
import server.models.StudentHasEvent;
import server.providers.EventTable;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
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
        public Response getEvents() throws Exception {

            //kald en metode der henter alle events fra databasen (gemmer dem i en ArrayList??)
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

        @POST
        @Path("/join")
        public Response joinEvent(String eventJson) throws Exception {

            EventController eventController = new EventController();
            StudentHasEvent studentHasEvent = new Gson().fromJson(eventJson, StudentHasEvent.class);

            if(eventController.joinEvent(studentHasEvent.getEvent_idEvent(), studentHasEvent.getStudent_idStudent())){
                return Response
                        .status(200)
                        .type("application/json")
                        .entity("ok")
                        .build();
            } else {
                return Response
                        .status(404)
                        .type("application/json")
                        .entity("not found")
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


