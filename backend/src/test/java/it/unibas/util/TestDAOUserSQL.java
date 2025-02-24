package it.unibas.util;

import it.unibas.dao.DAOUserSQL;
import it.unibas.enums.Level;
import it.unibas.enums.UserRole;
import it.unibas.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;


public class TestDAOUserSQL {
    private DAOUserSQL daoUserSQL;
    private static final Logger logger = LoggerFactory.getLogger(TestDAOUserSQL.class);

    @BeforeEach
    public void setUp() throws SQLException {
        daoUserSQL = new DAOUserSQL();
    }

    @Test
    void test_findByUsername_when_user_exist() throws SQLException {
        String username = "mario.rossi";

        User result = daoUserSQL.findByUsername(username);
        assertEquals(result.getUsername(), username);
    }

    @Test
    void test_findByUsername_when_user_not_exist() throws SQLException {
        String username = "mario.verdi";

        User result = daoUserSQL.findByUsername(username);
        assertNull(result);
    }

    @Test
    void test_SQL_Injection_In_FindByUsername() throws SQLException {
        String maliciousUsername = "admin' OR '1'='1";

        User result = daoUserSQL.findByUsername(maliciousUsername);

        assertNull(result, "Il risultato atteso è null. Al contrario l'applicazione è vulnerabile alla SQL Injection");
    }

    @Test
    void test_multiple_SQL_Injection_In_FindByUsername() throws SQLException {
        String maliciousUsername = "test' OR '1'='1'; UPDATE users SET password = 'ciao' WHERE username = 'test' or '1'='1";

        User result = daoUserSQL.findByUsername(maliciousUsername);

        assertNull(result, "Il risultato atteso è null. Al contrario l'applicazione è vulnerabile alla SQL Injection");
    }

    @Test
    void test_updateUser_when_user_exist() throws SQLException {
        User user = new User(
                "mario.rossi",
                "ciao",
                UserRole.USER,
                "Mario",
                "Rossi",
                Level.AMMINISTRATORE_DELEGATO,
                "test",
                "mario.rossi.png",
                "mario.rossi@gmail.com",
                "333666777",
                "Via Mazzini n.36, Potenza");

        daoUserSQL.updateUser(user);
        User result = daoUserSQL.findByUsername(user.getUsername());

        assertEquals(Level.AMMINISTRATORE_DELEGATO, result.getLevel());
    }

    @Test
    void test_updateUser_when_user_not_exist() throws SQLException {
        User user = new User(
                "mario.verdi",
                "ciao",
                UserRole.USER,
                "Mario",
                "Rossi",
                Level.OPERAIO,
                "test",
                "mario.rossi.png",
                "mario.rossi@gmail.com",
                "333666777",
                "Via Mazzini n.36, Potenza");

        User result = daoUserSQL.findByUsername(user.getUsername());
        assertNull(result);

        daoUserSQL.updateUser(user);

        result = daoUserSQL.findByUsername(user.getUsername());
        assertNull(result, "Il metodo update non crea un nuovo utente");
    }

    @Test
    void test_sqlInjection_in_updateUser() throws SQLException {
        User user = new User(
                "mario.rossi",
                "password",
                UserRole.USER,
                "Mario",
                "Rossi",
                Level.OPERAIO,
                "test",
                "mario.rossi.png",
                "mario.rossi@gmail.com",
                "333666777",
                "test', password='test' WHERE '1'='1'--");

        daoUserSQL.updateUser(user);

        User result = daoUserSQL.findByUsername(user.getUsername());
        assertNotNull(result);
        assertEquals("password", result.getPassword(), "Il risultato atteso è la password utente inalterata. Al contrario l'applicazione è vulnerabile alla SQL Injection");
    }

    @Test
    void test_updateUser_with_null_or_empty_values() throws SQLException {
        User user = new User(
                "mario.rossi",
                "password",
                UserRole.USER,
                "Test",
                "User",
                Level.OPERAIO,
                "test",
                "test.png",
                "",
                "333666777",
                "Via Mazzini n.36, Potenza");

        user.setPhone(null);

        daoUserSQL.updateUser(user);

        User result = daoUserSQL.findByUsername(user.getUsername());

        assertNotNull(result);
        assertNotNull(result.getPhone(), "Il risultato atteso è il valore non null");
        assertFalse(result.getEmail().isBlank(), "Il risultato atteso è una stringa non vuota");
    }

    @Test
    void test_updateUser_with_too_long_values() throws SQLException {
        User user = new User(
                "mario.rossi",
                "password",
                UserRole.USER,
                "Test",
                "User",
                Level.OPERAIO,
                "test",
                "test.png",
                "",
                "333666777",
                "A".repeat(300));

        Exception exception = assertThrows(SQLException.class, () -> daoUserSQL.updateUser(user));

        assertTrue(exception.getMessage().contains("too long") || exception.getMessage().contains("value too large"),
                "Il risultato atteso è un'eccezione per valori troppo lunghi");
    }


}
