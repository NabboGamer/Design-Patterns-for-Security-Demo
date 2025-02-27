package it.unibas.util;

import it.unibas.dao.DAOUserSQL;
import it.unibas.dao.IDAOUser;
import it.unibas.dto.UserDTO;
import it.unibas.model.User;
import it.unibas.enums.UserRole;
import it.unibas.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TestAuthService {

    private AuthService authService;
    private IDAOUser daoUserMock;

    @BeforeEach
    void setUp() throws SQLException {
        daoUserMock = mock(DAOUserSQL.class);
        authService = new AuthService();
    }

    @Test
    void testLogin_when_user_exists_and_password_is_correct() throws SQLException {
        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testUser");
        user.setPassword("password123");
        user.setRole(UserRole.USER);

        when(daoUserMock.findByUsername("testUser")).thenReturn(user);

        UserDTO result = authService.login("testUser", "password123");

        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    void testLogin_when_user_not_exist() throws SQLException {
        when(daoUserMock.findByUsername("nonExistentUser")).thenReturn(null);

        UserDTO result = authService.login("nonExistentUser", "password123");

        assertNull(result);
    }

    @Test
    void testLogin_when_password_is_incorrect() throws SQLException {
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("correctPassword");

        when(daoUserMock.findByUsername("testUser")).thenReturn(user);

        UserDTO result = authService.login("testUser", "wrongPassword");

        assertNull(result);
    }

    @Test
    void testLogin_when_database_throws_exception() throws SQLException {
        when(daoUserMock.findByUsername("testUser")).thenThrow(new SQLException("Database error"));

        assertThrows(SQLException.class, () -> authService.login("testUser", "password123"));
    }
}
