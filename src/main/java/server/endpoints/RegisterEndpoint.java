package server.endpoints;

import com.google.gson.Gson;
import server.controllers.StudentController;
import server.models.Student;
import server.providers.StudentTable;
import server.resources.Log;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.sql.SQLException;


@Path("/register")
public class RegisterEndpoint {

    StudentController studentController = new StudentController();
    StudentTable studentTable = new StudentTable();

    @POST
    @Produces("Application/json")
    public Response register(String jsonStudent) throws Exception {

        Gson gson = new Gson();
        Student student;
        try {
            student = gson.fromJson(jsonStudent, Student.class);
        } catch (IllegalArgumentException e) {
            System.out.print(e.getMessage());
            //Den her response giver ikke nogen mening??

            Log.writeLog(getClass().getName(), this, "User couldn't be registered", 2);

            return Response
                    .status(400)
                    .entity("første try i createStudent virker ikke")
                    .build();
        }

        try {
            studentController.verifyStudentCreation(student.getFirstName(), student.getLastName(), student.getPassword(), student.getEmail());
        } catch (IllegalArgumentException ee) {
            System.out.print(ee.getMessage());
            //Bør måske ændres til at user ikke kunne verifies pga forkert info om fornavn, efternavn, kodeord eller email

            Log.writeLog(getClass().getName(), this, "User couldn't be registered", 2);

            return Response
                    .status(400)
                    .entity("andet try i createStudent virker ikke").build();
        }
        try {
            studentTable.addStudent(student);
        } catch (SQLException e) {

            Log.writeLog(getClass().getName(), this, "User already exists", 2);

            return Response
                    .status(404)
                    .type("text/plain")
                    .entity("This user already exists, please log-in.").build();
        }
        Log.writeLog(getClass().getName(), this, student + " registered", 0);
        return Response
                .status(200)
                .entity("{message\":\"Success! Student created\"}")
                .build();
    }
}
