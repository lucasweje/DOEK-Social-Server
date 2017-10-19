package server.endpoints;


import com.google.gson.Gson;
import server.controllers.StudentController;
import server.models.Student;
import server.providers.StudentTable;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/students")
public class StudentEndpoint {

    //Opretter arraylist med students.

 /*   @GET
    public Response getStudents() {

        //Returnerer Gson til Json.
        return Response
                .status(200)
                .type("application/json")
                .entity(new Gson().toJson(new String[]{"student1", "student2"})) //skal ændres til connection med databasen når config filen er lavet.
                .build();
        //debugger kan stoppe program og eksekvere det og se hvad der sker.
    }

    StudentController controller = new StudentController();

    @POST
    @Produces("Application/json")
    public Response create(String data) throws Exception {

        Gson gson = new Gson();
        Student student = gson.fromJson(data, Student.class);

        if (controller.addStudent(student)) {
            return Response
                    .status(200)
                    .entity("{message\":\"Success! Student created\"}")
                    .build();
        }
        else return Response.status(400).entity("{\"message\":\"failed\"}").build();
    }
*/
    @GET
    public Response getAttendingStudents(String idStudent, String idEvent) {

        StudentTable studentTable = new StudentTable();
        ArrayList foundAttendingStudents = null;

        if (idStudent.isEmpty()) {
            return Response
                    .status(400)
                    .entity("{\"Missing Student ID\":\"true\"}")
                    .build();
        }else{
            try {
                foundAttendingStudents = studentTable.getAttendingStudents(idStudent, idEvent);
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

        StudentTable studentTable = new StudentTable();
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
*/
