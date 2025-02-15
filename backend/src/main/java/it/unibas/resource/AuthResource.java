package it.unibas.resource;

import it.unibas.service.AuthService;
import it.unibas.service.IAuthService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

@Path("/auth")
public class AuthResource {

    private static final Logger logger = LoggerFactory.getLogger(AuthResource.class);
    private final IAuthService authService;

    public AuthResource() {
        try {
            authService = new AuthService();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthResource(IAuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response login(@FormParam("username") String username, @FormParam("password") String password) {
        try {
            if(username == null || username.isBlank()) {
                return Response.status(400).entity("Username obbligatorio").build();
            }
            if(password == null || password.isBlank()) {
                return Response.status(400).entity("Password obbligatoria").build();
            }
            if (authService.login(username, password)) {
                return Response.status(200).entity("Accesso consentito").build();
            }
            return Response.status(401).entity("Accesso negato").build();
        } catch (Exception e) {
            logger.error("Errore durante l'autenticazione", e);
            return Response.status(500).entity("Errore durante l'autenticazione").build();
        }
    }
}
