package server.endpoints;

import server.utility.Authenticator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/")
public class RootEndpoint {

    @GET
    public Response defaultGetMethod(){

        //udkommenteret så hashwithsalt er statisk når vi tester login mv
        //String salt = Authenticator.randomSalt("salt");
        String hashString = Authenticator.hashWithSalt("1234", "salt");

        return Response.status(200).type("text/plain").entity(hashString).build();
    }
}
