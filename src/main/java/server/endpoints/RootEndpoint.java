package server.endpoints;

import server.utility.Authenticator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class RootEndpoint {

    @GET
    public Response defaultGetMethod(){

        String salt = Authenticator.randomSalt("Filip");
        String hashString = Authenticator.hashWithSalt("Filip", salt);

        return Response.status(200).type("text/plain").entity(hashString).build();
    }
}
