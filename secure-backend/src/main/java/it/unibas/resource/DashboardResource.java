package it.unibas.resource;

import it.unibas.dto.UserDTO;
import it.unibas.exception.InsufficientPermissionException;
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

            return buildSuccessResponse(newUserDTO);
        } catch (AccountLockedException | AuthenticationException ex) {
            return buildErrorResponse(401, "Credenziali non valide");
        } catch (IllegalArgumentException illegalArgumentException) {
            return buildErrorResponse(400, illegalArgumentException.getMessage());
        } catch (SQLException sqlException) {
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

            return buildSuccessResponse(newUserDTO);
        } catch (AccountLockedException | AuthenticationException ex) {
            return buildErrorResponse(401, "Credenziali non valide");
        } catch (IllegalArgumentException illegalArgumentException) {
            return buildErrorResponse(400, illegalArgumentException.getMessage());
        } catch (SQLException sqlException) {
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

            return buildSuccessResponse(newUserDTO);
        } catch (AccountLockedException | AuthenticationException ex) {
            return buildErrorResponse(401, "Credenziali non valide");
        } catch (IllegalArgumentException illegalArgumentException) {
            return buildErrorResponse(400, illegalArgumentException.getMessage());
        } catch (SQLException sqlException) {
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

            return buildSuccessResponse(newUserDTO);
        } catch (AccountLockedException | AuthenticationException ex) {
            return buildErrorResponse(401, "Credenziali non valide");
        } catch (IllegalArgumentException illegalArgumentException) {
            return buildErrorResponse(400, illegalArgumentException.getMessage());
        } catch (SQLException sqlException) {
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

            return buildSuccessResponse(newUserDTO);
        } catch (AccountLockedException | AuthenticationException ex) {
            return buildErrorResponse(401, "Credenziali non valide");
        } catch (IllegalArgumentException illegalArgumentException) {
            return buildErrorResponse(400, illegalArgumentException.getMessage());
        } catch (SQLException sqlException) {
            return buildErrorResponse(500, sqlException.getMessage());
        } catch (InsufficientPermissionException insufficientPermissionException) {
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
