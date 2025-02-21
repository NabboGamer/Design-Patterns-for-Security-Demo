package it.unibas.resource;

import it.unibas.dto.UserDTO;
import it.unibas.model.ErrorMessage;
import it.unibas.service.*;
import it.unibas.service.observer.ISecurityEventObserver;
import it.unibas.service.observer.SecurityLogger;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.AccountLockedException;
import javax.security.sasl.AuthenticationException;
import java.sql.SQLException;

@Path("/dashboard")
public class DashboardResource {

    private static final Logger logger = LoggerFactory.getLogger(DashboardResource.class);
    private final IAuthService secureAuthService;
    private ISecurityEventObserver securityObserver;
    private IDashboardEditService dashboardEditServiceProxy;

    public DashboardResource() {
        IAuthService basicAuthService = new AuthService();
        secureAuthService = new AuthServiceDecorator(basicAuthService);

        securityObserver = new SecurityLogger();

        IDashboardEditService dashboardEditService = new DashboardEditService();
        dashboardEditServiceProxy = new DashboardEditServiceProxy(dashboardEditService);
    }

    @POST
    @Path("/edit/name")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUserName(@FormParam("username") String username,
                                 @FormParam("password") String password,
                                 @FormParam("name")     String name) {
        try {
            UserDTO userDTO = secureAuthService.login(username, password);
            if (userDTO == null) {
                throw new AuthenticationException();
            }

            UserDTO newUserDTO = dashboardEditServiceProxy.editUserName(username, password, userDTO, name);

            return buildSuccessResponse(newUserDTO);
        } catch (AccountLockedException | AuthenticationException ex) {
            return buildErrorResponse(401, "Credenziali non valide");
        } catch (IllegalArgumentException illegalArgumentException) {
            return buildErrorResponse(400, illegalArgumentException.getMessage());
        } catch (SQLException sqlException) {
            return buildErrorResponse(500, sqlException.getMessage());
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

//    @POST
//    @Path("/edit/surname")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response editUserData(@FormParam("username") String username,
//                                 @FormParam("password") String password,
//                                 @FormParam("surname")  String surname) {
//        try {
//            UserDTO userDTO = secureAuthService.login(username, password);
//            if (userDTO == null) {
//                throw new AuthenticationException();
//            }
//
//            // TODO: Inserire codice che permette di modificare il nome del dipendente.
//            //       Nota che il Service che si occuperà di eseguire l'azione sarà un proxy, così effettuerà azioni
//            //       di controllo dei permessi in caso di alcune modifiche come per quella della proprietà level.
//            //       In questo caso non verificherà il ruolo dell'utente ma farà comunque delle verifiche sulla stringa(lunghezza, caratteri speciali, ecc.)
//
//
//        } catch (AccountLockedException | SQLException  | AuthenticationException ex) {
//            return Response.status(401)
//                    .entity("Credenziali non valide")
//                    .build();
//        }
//    }
//
//    @POST
//    @Path("/edit/level")
//    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response editUserData(@FormParam("username") String username,
//                                 @FormParam("password") String password,
//                                 @FormParam("level")    String level) {
//        try {
//            UserDTO userDTO = secureAuthService.login(username, password);
//            if (userDTO == null) {
//                throw new AuthenticationException();
//            }
//
//            // TODO: Inserire codice che permette di modificare il level del dipendente.
//            //       Nota che
//
//
//        } catch (AccountLockedException | SQLException  | AuthenticationException ex) {
//            return Response.status(401)
//                    .entity("Credenziali non valide")
//                    .build();
//        }
//    }
}
