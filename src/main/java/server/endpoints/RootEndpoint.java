package server.endpoints;

import server.resources.Log;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

// denne metode er blevet brugt til test af hashing.
@Path("/")
public class RootEndpoint {

    @GET
    public Response defaultGetMethod(){
        Log.writeLog(getClass().getName(), this, "TEST", 0);
        return Response.status(200).type("text/plain").entity("FOO").build();
    }
}
