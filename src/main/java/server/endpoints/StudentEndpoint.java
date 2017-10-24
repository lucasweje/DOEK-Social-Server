package server.endpoints;


import com.google.gson.Gson;
import server.controllers.MainController;
import server.controllers.StudentController;
import server.controllers.TokenController;
import server.models.Event;
import server.models.Student;
import server.providers.StudentTable;
import server.resources.Log;
import server.utility.Authenticator;
import server.utility.CurrentStudentContext;
import sun.applet.Main;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

import server.utility.Crypter;

@Path("/students")
public class StudentEndpoint {

    private StudentController studentController = new StudentController();
    private MainController mainController = new MainController();
    private TokenController tokenController = new TokenController();

    @GET
    @Path("{idStudent}/events")
    public Response getAttendingEvents(@PathParam("idStudent") String idStudent) throws SQLException, IllegalAccessException {

        ArrayList<Event> foundAttendingEvents = null;

        if (idStudent.isEmpty()) {

            Log.writeLog(getClass().getName(), this, "Student not found", 2);

            return Response
                    .status(400)
                    .entity("{\"Missing Student ID\":\"true\"}")
                    .build();
        } else {
            foundAttendingEvents = studentController.getAttendingEvents(idStudent);
            // if event not found
            if (foundAttendingEvents.isEmpty()) {
                Log.writeLog(getClass().getName(), this, "Student has no attending events", 2);
                return Response
                        .status(400)
                        .entity("{no attending events}")
                        .build();
            } else {
                String json = new Gson().toJson(foundAttendingEvents);
                String crypted = Crypter.encryptDecrypt(json);
                Log.writeLog(getClass().getName(), this, "Attending events fetched", 0);
                return Response
                        .status(200)
                        .type("application/json")
                        .entity(crypted)
                        .build();
            }
        }
    }

    @POST
    @Path("/logout")
    public Response logout(@HeaderParam("Authorization") String idStudent) throws SQLException {
        if(tokenController.deleteToken(idStudent)) {
            Log.writeLog(getClass().getName(), this, "User logged out", 0);
            return Response.status(200).entity("You are now logged out").build();
        } else {
            return Response.status(404).entity("There was an error").build();
        }
    }

    @GET
    @Path("/profile")
    public Response get(@HeaderParam("Authorization") String token) throws SQLException {

        CurrentStudentContext student = mainController.getStudentFromTokens(token);

        if (student.getCurrentStudent() != null) {

            Log.writeLog(getClass().getName(), this, "Current student found: " + student.getCurrentStudent(), 0);

            return Response
                    .status(200)
                    .type("application/json")
                    .entity(new Gson().toJson(student))
                    .build();
        } else {
            Log.writeLog(getClass().getName(), this, "Current student not found - 404", 2);

            return Response
                    .status(404)
                    .type("application/json")
                    .entity("fejl")
                    .build();
        }
    }

}
