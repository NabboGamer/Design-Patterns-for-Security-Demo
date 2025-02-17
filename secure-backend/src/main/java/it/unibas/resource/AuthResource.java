package it.unibas.resource;

import it.unibas.dto.UserDTO;
import it.unibas.model.ErrorMessage;
import it.unibas.model.User;
import it.unibas.service.AuthService;
import it.unibas.service.IAuthService;
import jakarta.ws.rs.*;
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
        authService = new AuthService();
    }

    public AuthResource(IAuthService authService) {
        this.authService = authService;
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@FormParam("username") String username,
                          @FormParam("password") String password) {
        try {
            if(username == null || username.isBlank()) {
                return buildErrorResponse(400, "Username obbligatorio");
            }
            if(password == null || password.isBlank()) {
                return buildErrorResponse(400, "Password obbligatoria");
            }

            UserDTO userDTO = authService.login(username, password);
            if (userDTO != null) {
                return buildSuccessResponse(userDTO);
            }
            return buildErrorResponse(401, "Credenziali non valide");

        } catch (Exception e) {
            logger.error("Errore durante l'autenticazione", e);
            return buildErrorResponse(500, "Errore interno del server");
        }
    }

    private Response buildSuccessResponse(UserDTO userDTO) {
        return Response.status(200)
                .entity(userDTO)
                .build();
    }

    private Response buildErrorResponse(int status, String message) {
        return Response.status(status)
                .entity(new ErrorMessage(message))
                .build();
    }
}
