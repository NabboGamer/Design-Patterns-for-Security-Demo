package it.unibas.resource;

import it.unibas.service.AuthService;
import it.unibas.service.IAuthService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.sql.SQLException;

@Path("/auth")
public class AuthResource {
    private final IAuthService authService;

    public AuthResource() {
        try {
            authService = new AuthService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        try {
            if (authService.login(username, password)) {
                return Response.ok("Accesso consentito").build();
            }
            return Response.status(401).entity("Accesso negato").build();
        } catch (SQLException e) {
            return Response.serverError().build();
        }
    }
}
