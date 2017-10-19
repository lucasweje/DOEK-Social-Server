package server.endpoints;

import server.utility.Authenticator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
// denne metode er blevet brugt til test af hashing.
@Path("/")
public class RootEndpoint {

    @GET
    public Response defaultGetMethod(){


        return Response.status(200).type("text/plain").entity("FOO").build();
    }
}
