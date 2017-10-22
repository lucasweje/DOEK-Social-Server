package server.endpoints;


import com.google.gson.Gson;
import server.controllers.MainController;
import server.controllers.StudentController;
import server.controllers.TokenController;
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

    StudentTable studentTable = new StudentTable();
    StudentController controller = new StudentController();
    MainController mainController = new MainController();
    TokenController tokenController = new TokenController();


    @GET
    public Response getStudents() throws IllegalAccessException {

        //TO DO: sql statement.
        String json = new Gson().toJson(studentTable.getStudents());
        String crypted = Crypter.encryptDecrypt(json);

        Log.writeLog(getClass().getName(), this, "Get students", 0);


        //Returnerer Gson til Json.
        return Response
                .status(200)
                .type("application/json")
                .entity(crypted) //skal ændres til connection med databasen når config filen er lavet.
                .build();


    }

    @GET
    @Path("{idStudentEvents}/events")
    public Response getAttendingEvents(@PathParam("idStudentEvents") String idStudent) {

        StudentTable studentTable = new StudentTable();
        ArrayList foundAttendingEvents = null;


        if (idStudent.isEmpty()) {
            Log.writeLog(getClass().getName(), this, "Missing student ID", 2);

            return Response
                    .status(400)
                    .entity("{\"Missing Student ID\":\"true\"}")
                    .build();
        } else {
            try {
                foundAttendingEvents = studentTable.getAttendingEvents(idStudent);

                Log.writeLog(getClass().getName(), this, "Get attending events", 0);

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            // If student not found:
            if (!true) {
                Log.writeLog(getClass().getName(), this, "Student not found therefore no attending events", 2);

                return Response
                        .status(400)
                        .entity("{\"Student not found\":\"true\"}")
                        .build();
            }
            return Response
                    .status(200)
                    .type("application/json")
                    .entity(new Gson().toJson(foundAttendingEvents))
                    .build();
        }
    }

    @POST
    @Path("/logout")
    public Response logout(String idStudent) throws SQLException {
        String id = new Gson().fromJson(idStudent, String.class);

        boolean isLoggedOut = studentTable.deleteToken(id);

        Log.writeLog(getClass().getName(), this, "User logged out", 0);


        return Response.status(200).entity(isLoggedOut).build();
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
