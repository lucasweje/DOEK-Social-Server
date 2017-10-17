package server.endpoints;


import com.google.gson.Gson;
import server.controllers.StudentController;
import server.models.Student;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/students")
public class StudentEndpoint {

    StudentController controller = new StudentController();

    @POST
    @Produces("Application/json")
    public Response create(String data) throws Exception {

        Gson gson = new Gson();
        Student student = gson.fromJson(data, Student.class);

        if (controller.addStudent(student)) {
            return Response
                    .status(200)
                    .entity("{message\":\"Succes! Student added\"}")
                    .build();
        }
        else return Response.status(400).entity("{\"message\":\"failed\"}").build();
    }

    @GET
    public Response getAll(){
        return Response.status(200).entity("Foo").build();
    }

}
