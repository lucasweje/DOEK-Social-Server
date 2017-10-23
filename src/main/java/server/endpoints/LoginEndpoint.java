package server.endpoints;

import com.google.gson.Gson;
import server.models.Student;
import server.models.Token;
import server.providers.StudentTable;
import server.resources.Log;
import server.utility.Authenticator;
import server.controllers.MainController;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/login")
public class LoginEndpoint {

    StudentTable studentTable = new StudentTable();
    MainController mainController = new MainController();

    @POST
    public Response login(String jsonLogin) throws Exception {

        Gson gson = new Gson();
        Student foundStudent;

        Student needAuthStudent = gson.fromJson(jsonLogin, Student.class);
        try {
            foundStudent = studentTable.getStudentByEmail(needAuthStudent.getEmail());

            //Mht. sikkerhed er det så dumt at kalde "foundstudent" i logfilen?
            Log.writeLog(getClass().getName(), this, foundStudent + " logged in", 0);

        } catch (Exception notFound) {
            Log.writeLog(getClass().getName(), this, "Email not found/not existing", 2);

            return Response.status(401).type("plain/text").entity("Email does not exist").build();
        }

        String doHash = Authenticator.hashWithSalt(needAuthStudent.getPassword(), (needAuthStudent.getEmail() + foundStudent.getCreatedTime()));

        if (doHash.equals(foundStudent.getPassword())) {
            String token = mainController.setToken(foundStudent);

            Log.writeLog(getClass().getName(), this, "Password hashed", 0);

            return Response.status(200).entity(new Gson().toJson("You are now logged in! :)")).build();
        } else {

            Log.writeLog(getClass().getName(), this, "Password incorect", 2);


            return Response.status(401).type("plain/text").entity("password not correct").build();
        }
    }
}
