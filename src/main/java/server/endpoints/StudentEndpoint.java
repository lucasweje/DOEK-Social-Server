package server.endpoints;


import com.google.gson.Gson;
import server.controllers.MainController;
import server.controllers.StudentController;
import server.controllers.TokenController;
import server.models.Student;
import server.providers.StudentTable;
import server.utility.Authenticator;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/students")
public class StudentEndpoint {

    StudentTable studentTable = new StudentTable();
    StudentController controller = new StudentController();
    MainController mainController = new MainController();
    TokenController tokenController = new TokenController();

    //Opretter arraylist med students.

    @GET
    public Response getStudents() {

        //Returnerer Gson til Json.
        return Response
                .status(200)
                .type("application/json")
                .entity(new Gson().toJson(new String[]{"student1", "student2"})) //skal ændres til connection med databasen når config filen er lavet.
                .build();
        //debugger kan stoppe program og eksekvere det og se hvad der sker.
    }
/*
    @POST
    @Produces("Application/json")
    public Response create(String data) throws Exception {

        Gson gson = new Gson();
        Student student = gson.fromJson(data, Student.class);

        if (studentController.addStudent(student)) {
            return Response
                    .status(200)
                    .entity("{message\":\"Success! Student created\"}")
                    .build();
        } else return Response.status(400).entity("{\"message\":\"failed\"}").build();
    }
}
*/

 //Skal laves om til feature 10.
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
/*
   @GET
    public Response getAll(){
        return Response.status(200).entity("Foo").build();
    }*/

//getStudentById udkommenteret da vi sandsynligvis ikke skal bruge den.
/*
    @GET
    @Path("{idStudent}")
    public Response getStudentById(@PathParam("idStudent") String idStudent) {

        Student foundStudent = null;

        // If else statement tjekker om parametren er tom.
        if (idStudent.isEmpty()) {

            return Response
                    .status(400)
                    .entity("{\"Missing Student ID\":\"true\"}")
                    .build();
        }

        try {
            foundStudent = studentTable.getStudentById(idStudent);
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

        //Returnerer Gson til Json.
        return Response
                .status(200)
                .type("application/json")
                .entity(new Gson().toJson(foundStudent))
                .build();
    }

    /* skal laves - hvordan sender man folk rundt normalt?
    //skal sende brugeren til oprettelses formularen
    @GET
    @Path("/register")
    public Response register() throws Exception {

    }*/

    /* MANGLER AT TESTES
    //skal terminere nuværende session og omdirigere til /
    @GET
    public Response logout(Student currentUser) throws Exception {
           try {
               mainController.logout(currentUser);
           } catch (IllegalArgumentException e) {
               System.out.print(e.getMessage());
               return Response.status(400).entity("No one was logged in, please log in before logging out").build();
        }
        return Response.status(303).entity("You've been logged out successfully").build();
    }
*/

}
