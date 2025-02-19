package it.unibas.resource;

import it.unibas.dto.UserDTO;
import it.unibas.model.ErrorMessage;
import it.unibas.service.*;
import it.unibas.service.observer.ISecurityEventObserver;
import it.unibas.service.observer.SecurityLogger;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.security.auth.login.AccountLockedException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Path("/auth")
public class AuthResource {

    private static final Logger logger = LoggerFactory.getLogger(AuthResource.class);
    private final IAuthService secureAuthService;
    private ISecurityEventObserver securityObserver;

    public AuthResource() {
        IAuthService basicAuthService = new AuthService();
        secureAuthService = new AuthServiceDecorator(basicAuthService);
        securityObserver = new SecurityLogger();
    }

    public AuthResource(IAuthService authService) {
        this.secureAuthService = authService;
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

            UserDTO userDTO = secureAuthService.login(username, password);
            if (userDTO != null) {
                SecurityEventManager.getInstance().notifyEvent(buildLoggingString(username, "login", "Login avvenuto"));
                return buildSuccessResponse(userDTO);
            }
            SecurityEventManager.getInstance().notifyEvent(buildLoggingString(username, "login", "Credenziali non valide"));
            return buildErrorResponse(401, "Credenziali non valide");

        } catch (SQLException sqlException) {
            SecurityEventManager.getInstance().notifyEvent(buildLoggingString(username, "login", sqlException.getMessage()));
            return buildErrorResponse(500, sqlException.getMessage());
        } catch (AccountLockedException accountLockedException) {
            SecurityEventManager.getInstance().notifyEvent(buildLoggingString(username, "login", accountLockedException.getMessage()));
            return buildErrorResponse(403, accountLockedException.getMessage());
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

    private String buildLoggingString(String username, String action, String result) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(timestamp).append("]").append(" ")
          .append("UTENTE:").append(username).append(" - ")
          .append("EFFETTUA:").append(action).append(" - ")
          .append("ESITO:").append(result).append("\n");
        return sb.toString();
    }
}
