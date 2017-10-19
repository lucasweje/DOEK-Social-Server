package server.endpoints;


import com.google.gson.Gson;
import server.controllers.MainController;
import server.controllers.StudentController;
import server.models.Student;
import server.providers.StudentTable;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.ArrayList;

@Path("/students")
public class StudentEndpoint {

    StudentController studentController = new StudentController();
    StudentTable studentTable = new StudentTable();
    MainController mainController = new MainController();

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

    StudentController controller = new StudentController();

    @POST
    @Produces("Application/json")
    @Path("/register")
    public Response register(String jsonStudent) throws Exception {

        Gson gson = new Gson();
        Student student;
        try {
            student = gson.fromJson(jsonStudent, Student.class);
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
            return Response.status(400).entity("første try i createStudent virker ikke").build();
        }

        try {
            student = studentController.verifyStudentCreation(student.getFirstName(), student.getLastName(), student.getPassword(), student.getEmail());
        } catch (IllegalArgumentException ee) {
            System.out.print(ee.getMessage());
            //Bør måske ændres til at user ikke kunne verifies pga forkert info om fornavn, efternavn, kodeord eller email
            return Response.status(400).entity("andet try i createStudent virker ikke").build();
        }
        try {
            studentTable.addStudent(student);
        } catch (SQLException e) {
            return Response.status(501).type("text/plain").entity("Server couldn't store the user info (SQL Error) ").build();
        }

        return Response.status(200).entity("{message\":\"Success! Student created\"}").build();
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
'/
/*
    //skal sende information til backend, hvorefter den returnerer true/false eller et student objekt, som så er vores user - snak med Jesper, virker det her nogenlunde korrekt?
    @POST
    @Path("/login")
    public Response login(Student jsonLogin) throws Exception {
        Gson gson = new Gson();
        Student student;
        try {
            student = gson.fromJson(jsonLogin, Student.class);
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
            return Response.status(400).entity("error in from json to gson").build();
        }
        try {
            mainController.authorizeStudent(student.getFirstName(),student.getPassword());
        } catch (IllegalArgumentException ee) {
            System.out.print(ee.getMessage());
            return Response.status(400).entity("wrong info entered").build();
        }

        Response.status(200).entity("You are now logged in!").build();
        Student currentUser = student;
        //??? hvordan skal dette gøres?
    }*/
}
