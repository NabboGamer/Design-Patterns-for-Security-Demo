package it.unibas.resource;

import it.unibas.dto.UserDTO;
import it.unibas.exception.InsufficientPermissionException;
import it.unibas.model.ErrorMessage;
import it.unibas.service.*;
import it.unibas.service.observer.SecurityLogger;
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
    private SecurityLogger securityObserver;
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
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/name", "Modifica del nome avvenuta"));
            return buildSuccessResponse(newUserDTO);
        } catch (AccountLockedException | AuthenticationException ex) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/name", "Credenziali non valide"));
            return buildErrorResponse(401, "Credenziali non valide");
        } catch (IllegalArgumentException illegalArgumentException) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/name", illegalArgumentException.getMessage()));
            return buildErrorResponse(400, illegalArgumentException.getMessage());
        } catch (SQLException sqlException) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/name", sqlException.getMessage()));
            return buildErrorResponse(500, sqlException.getMessage());
        }
    }

    @POST
    @Path("/edit/surname")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUserSurname(@FormParam("username") String username,
                                    @FormParam("password") String password,
                                    @FormParam("surname")  String surname) {
        try {
            UserDTO userDTO = secureAuthService.login(username, password);
            if (userDTO == null) {
                throw new AuthenticationException();
            }

            UserDTO newUserDTO = dashboardEditServiceProxy.editUserSurname(username, password, userDTO, surname);
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/surname", "Modifica del cognome avvenuta"));
            return buildSuccessResponse(newUserDTO);
        } catch (AccountLockedException | AuthenticationException ex) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/surname", "Credenziali non valide"));
            return buildErrorResponse(401, "Credenziali non valide");
        } catch (IllegalArgumentException illegalArgumentException) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/surname", illegalArgumentException.getMessage()));
            return buildErrorResponse(400, illegalArgumentException.getMessage());
        } catch (SQLException sqlException) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/name", sqlException.getMessage()));
            return buildErrorResponse(500, sqlException.getMessage());
        }
    }

    @POST
    @Path("/edit/email")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUserEmail(@FormParam("username") String username,
                                  @FormParam("password") String password,
                                  @FormParam("email")    String email) {
        try {
            UserDTO userDTO = secureAuthService.login(username, password);
            if (userDTO == null) {
                throw new AuthenticationException();
            }

            UserDTO newUserDTO = dashboardEditServiceProxy.editUserEmail(username, password, userDTO, email);
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/email", "Modifica della email avvenuta"));
            return buildSuccessResponse(newUserDTO);
        } catch (AccountLockedException | AuthenticationException ex) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/email", "Credenziali non valide"));
            return buildErrorResponse(401, "Credenziali non valide");
        } catch (IllegalArgumentException illegalArgumentException) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/email", illegalArgumentException.getMessage()));
            return buildErrorResponse(400, illegalArgumentException.getMessage());
        } catch (SQLException sqlException) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/email", sqlException.getMessage()));
            return buildErrorResponse(500, sqlException.getMessage());
        }
    }

    @POST
    @Path("/edit/phone")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUserPhone(@FormParam("username") String username,
                                  @FormParam("password") String password,
                                  @FormParam("phone")    String phone) {
        try {
            UserDTO userDTO = secureAuthService.login(username, password);
            if (userDTO == null) {
                throw new AuthenticationException();
            }

            UserDTO newUserDTO = dashboardEditServiceProxy.editUserPhone(username, password, userDTO, phone);
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/phone", "Modifica del numero di telefono avvenuta"));
            return buildSuccessResponse(newUserDTO);
        } catch (AccountLockedException | AuthenticationException ex) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/phone", "Credenziali non valide"));
            return buildErrorResponse(401, "Credenziali non valide");
        } catch (IllegalArgumentException illegalArgumentException) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/phone", illegalArgumentException.getMessage()));
            return buildErrorResponse(400, illegalArgumentException.getMessage());
        } catch (SQLException sqlException) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/phone", sqlException.getMessage()));
            return buildErrorResponse(500, sqlException.getMessage());
        }
    }

    @POST
    @Path("/edit/address")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUserAddress(@FormParam("username") String username,
                                    @FormParam("password") String password,
                                    @FormParam("address")  String address) {
        try {
            UserDTO userDTO = secureAuthService.login(username, password);
            if (userDTO == null) {
                throw new AuthenticationException();
            }

            UserDTO newUserDTO = dashboardEditServiceProxy.editUserAddress(username, password, userDTO, address);
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/address", "Modifica dell'indirizzo avvenuta"));
            return buildSuccessResponse(newUserDTO);
        } catch (AccountLockedException | AuthenticationException ex) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/address", "Credenziali non valide"));
            return buildErrorResponse(401, "Credenziali non valide");
        } catch (IllegalArgumentException illegalArgumentException) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/address", illegalArgumentException.getMessage()));
            return buildErrorResponse(400, illegalArgumentException.getMessage());
        } catch (SQLException sqlException) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/address", sqlException.getMessage()));
            return buildErrorResponse(500, sqlException.getMessage());
        }
    }

    @POST
    @Path("/edit/level")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editUserLevel(@FormParam("username") String username,
                                  @FormParam("password") String password,
                                  @FormParam("level")    String level) {
        try {
            UserDTO userDTO = secureAuthService.login(username, password);
            if (userDTO == null) {
                throw new AuthenticationException();
            }

            UserDTO newUserDTO = dashboardEditServiceProxy.editLevel(username, password, userDTO, level);
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/level", "Modifica del livello avvenuta"));
            return buildSuccessResponse(newUserDTO);
        } catch (AccountLockedException | AuthenticationException ex) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/level", "Credenziali non valide"));
            return buildErrorResponse(401, "Credenziali non valide");
        } catch (IllegalArgumentException illegalArgumentException) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/level", illegalArgumentException.getMessage()));
            return buildErrorResponse(400, illegalArgumentException.getMessage());
        } catch (SQLException sqlException) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/level", sqlException.getMessage()));
            return buildErrorResponse(500, sqlException.getMessage());
        } catch (InsufficientPermissionException insufficientPermissionException) {
            SecurityEventManager.getInstance().notifyEvent(securityObserver.buildLoggingString(username, "edit/level", insufficientPermissionException.getMessage()));
            return buildErrorResponse(403, insufficientPermissionException.getMessage());
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
