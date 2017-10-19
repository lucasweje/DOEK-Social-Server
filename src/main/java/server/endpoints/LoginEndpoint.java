package server.endpoints;

import com.google.gson.Gson;
import server.models.Student;
import server.providers.StudentTable;
import server.utility.Authenticator;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/login")
public class LoginEndpoint {

    StudentTable studentTable = new StudentTable();

    //skal sende information til backend, hvorefter den returnerer true/false eller et student objekt, som så er vores user - snak med Jesper, virker det her nogenlunde korrekt?
    @POST
    public Response login(String jsonLogin) throws Exception {

        Gson gson = new Gson();
        Student foundStudent;

        Student needAuthStudent = gson.fromJson(jsonLogin, Student.class);
        try {
            foundStudent = studentTable.getStudentByEmail(needAuthStudent.getEmail());
        } catch (Exception notFound) {
            return Response.status(401).type("plain/text").entity("Email does not exist").build();
        }

        String doHash = Authenticator.hashWithSalt(needAuthStudent.getPassword(), (needAuthStudent.getEmail()+foundStudent.getCreatedTime()));

        if (doHash.equals(foundStudent.getPassword())) {
            Student currentStudent = foundStudent;
            return Response.status(200).type("plain/text").entity("You are now logged in!").build();
        } else {
            return Response.status(401).type("plain/text").entity("password not correct").build();
        }
    }
}
