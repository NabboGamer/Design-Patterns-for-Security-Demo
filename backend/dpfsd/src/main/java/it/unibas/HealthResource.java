package it.unibas;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("")
public class HealthResource {

    @Path("/health")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String health() {
        return "UP";
    }

}
