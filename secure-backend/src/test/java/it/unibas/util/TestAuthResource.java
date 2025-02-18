package it.unibas.util;

import it.unibas.service.*;
import it.unibas.resource.*;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TestAuthResource {
    @Mock
    private IAuthService authService;
    @InjectMocks
    private AuthResource authResource;

    @BeforeEach
    void setUp() {
        authService = mock(IAuthService.class);
        authResource = new AuthResource(authService);
    }

    @Test
    void testLoginSuccess() throws SQLException {
        //when(authService.login("user", "pass")).thenReturn(true);

        Response response = authResource.login("user", "pass");

        assertEquals(200, response.getStatus());
        assertEquals("Accesso consentito", response.getEntity());
        //verify(authService, times(1)).login("user", "pass");
    }

    @Test
    void testLoginFailure() throws SQLException {
        //when(authService.login("user", "pass")).thenReturn(false);

        Response response = authResource.login("user", "pass");

        assertEquals(401, response.getStatus());
        assertEquals("Accesso negato", response.getEntity());
    }

    @Test
    void testLoginSQLException() throws SQLException {
        //when(authService.login(anyString(), anyString())).thenThrow(new SQLException());

        Response response = authResource.login("user", "pass");

        assertEquals(500, response.getStatus());
    }
}
