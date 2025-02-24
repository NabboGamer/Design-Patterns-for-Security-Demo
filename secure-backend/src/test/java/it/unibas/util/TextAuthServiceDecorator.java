package it.unibas.util;

import it.unibas.dto.UserDTO;
import it.unibas.service.AuthServiceDecorator;
import it.unibas.service.IAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.AccountLockedException;
import java.sql.SQLException;

import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ATestuthServiceDecorator {
    private static final Logger logger = LoggerFactory.getLogger(ATestuthServiceDecorator.class);

    @Mock
    private IAuthService authService;

    private AuthServiceDecorator authServiceDecorator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authServiceDecorator = new AuthServiceDecorator(authService);
    }

    @Test
    void testLogin_successful() throws SQLException, AccountLockedException {
        UserDTO mockUser = new UserDTO();
        mockUser.setEmail("test@example.com");

        when(authService.login("testuser", "password123")).thenReturn(mockUser);

        UserDTO result = authServiceDecorator.login("testuser", "password123");

        verify(authService, times(1)).login("testuser", "password123");
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void testLogin_failed_attempt() throws SQLException, AccountLockedException {
        when(authService.login("testuser", "wrongpassword")).thenReturn(null);

        UserDTO result = authServiceDecorator.login("testuser", "wrongpassword");

        verify(authService, times(1)).login("testuser", "wrongpassword");
        assertNull(result);
    }

    @Test
    void testLogin_temporary_lockout() throws SQLException, AccountLockedException {
        when(authService.login("testuser", "wrongpassword")).thenReturn(null);

        for (int i = 0; i < 4; i++) {
            authServiceDecorator.login("testuser", "wrongpassword");
        }

        AccountLockedException thrown = assertThrows(AccountLockedException.class, () ->
                authServiceDecorator.login("testuser", "wrongpassword")
        );

        assertTrue(thrown.getMessage().contains("Numero massimo di tentativi di login falliti raggiunto per l'account testuser"));
    }

 /**   @Test
    void testLogin_permanent_Lockout() throws SQLException, AccountLockedException, InterruptedException {
        when(authService.login("testuser", "wrongpassword")).thenReturn(null);

        for (int i = 0; i < 21; i++) {
            try {
                authServiceDecorator.login("testuser", "wrongpassword");
            } catch (AccountLockedException e) {}
            if(i!=0 && i%5==0){
                sleep(30000);
            }
        }

        AccountLockedException thrown = assertThrows(AccountLockedException.class, () ->
                authServiceDecorator.login("testuser", "wrongpassword")
        );

        assertTrue(thrown.getMessage().contains("Account bloccato permanentemente"));
    }
**/

    @Test
    void testLogin_resets_after_success() throws SQLException, AccountLockedException {
        when(authService.login("testuser", "wrongpassword")).thenReturn(null);
        when(authService.login("testuser", "password123")).thenReturn(new UserDTO());

        for (int i = 0; i < 4; i++) {
            authServiceDecorator.login("testuser", "wrongpassword");
        }

        UserDTO result = authServiceDecorator.login("testuser", "password123");

        result = authServiceDecorator.login("testuser", "password123");

        assertNotNull(result);
        verify(authService, times(2)).login("testuser", "password123");
    }

}