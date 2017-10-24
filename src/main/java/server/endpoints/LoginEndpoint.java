package server.endpoints;

import com.google.gson.Gson;
import server.models.Student;
import server.providers.StudentTable;
import server.resources.Log;
import server.utility.Authenticator;
import server.controllers.MainController;
import server.utility.CurrentStudentContext;

import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;


@Path("/login")
public class LoginEndpoint {

    private StudentTable studentTable = new StudentTable();
    private MainController mainController = new MainController();
    private Gson gson = new Gson();

    @POST
    public Response login(@HeaderParam("Authorization") String token, String jsonLogin) throws Exception {

        CurrentStudentContext student = mainController.getStudentFromTokens(token);
        Student currentStudent = student.getCurrentStudent();
        if (currentStudent != null) {
            return Response
                    .status(400)
                    .type("plain/text")
                    .entity("You are already logged in, no need to login again.")
                    .build();
        } else {
            Student foundStudent;
            Student needAuthStudent = gson.fromJson(jsonLogin, Student.class);
            try {
                foundStudent = studentTable.getStudentByEmail(needAuthStudent.getEmail());
                //Mht. sikkerhed er det så dumt at kalde "foundstudent" i logfilen?
                Log.writeLog(getClass().getName(), this, foundStudent + " logged in", 0);

            } catch (Exception notFound) {
                Log.writeLog(getClass().getName(), this, "Email not found/not existing", 2);

                return Response
                        .status(401)
                        .type("plain/text")
                        .entity("Email does not exist")
                        .build();
            }

            String doHash = Authenticator.hashWithSalt(needAuthStudent.getPassword(), (needAuthStudent.getEmail() + foundStudent.getCreatedTime()));

            if (doHash.equals(foundStudent.getPassword())) {
                //sets the token for the student
                mainController.setToken(foundStudent);
                Log.writeLog(getClass().getName(), this, "Password hashed", 0);
                return Response
                        .status(200)
                        .type("plain/text")
                        .entity("You are now logged in! :)")
                        .build();
            } else {
                Log.writeLog(getClass().getName(), this, "Password incorect", 2);
                return Response
                        .status(403)
                        .type("plain/text")
                        .entity("Password not correct")
                        .build();
            }
        }
    }
}
