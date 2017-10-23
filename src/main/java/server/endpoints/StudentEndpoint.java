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

        //Returnerer Gson til Json.
        return Response
                .status(200)
                .type("application/json")
                .entity(crypted) //skal ændres til connection med databasen når config filen er lavet.
                .build();

    }

@GET
 @Path("{idStudentEvents}/events")
 public Response getAttendingEvents(@PathParam("idStudentEvents")String idStudent) {

     StudentTable studentTable = new StudentTable();
     ArrayList foundAttendingEvents = null;

     if (idStudent.isEmpty()) {
         return Response
                 .status(400)
                 .entity("{\"Missing Student ID\":\"true\"}")
                 .build();
     }else{
         try {
             foundAttendingEvents = studentTable.getAttendingEvents(idStudent);
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
                 .entity(new Gson().toJson(foundAttendingEvents))
                 .build();
     }
 }

    @POST
    @Path("/logout")
    public Response logout (String idStudent) throws SQLException {
        int idStudentGson = new Gson().fromJson(idStudent, Integer.class);

        if(studentTable.deleteToken(idStudentGson)) {
            return Response.status(200).entity("You are now logged out").build();
        } else {
            return Response.status(500).entity("There was an error").build();
        }
    }

    @GET
    @Path("/profile")
    public Response get(@HeaderParam("Authorization") String token) throws SQLException {

        CurrentStudentContext student = mainController.getStudentFromTokens(token);

        if (student.getCurrentStudent() != null) {
            return Response
                    .status(200)
                    .type("application/json")
                    .entity(new Gson().toJson(student))
                    .build();
        } else {
            return Response
                    .status(404)
                    .type("application/json")
                    .entity("fejl")
                    .build();
        }
    }

}
