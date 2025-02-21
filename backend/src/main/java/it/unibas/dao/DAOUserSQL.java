package it.unibas.dao;

import it.unibas.enums.Level;
import it.unibas.enums.UserRole;
import it.unibas.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DAOUserSQL implements IDAOUser {

    private final Connection conn;
    private static final Logger logger = LoggerFactory.getLogger(DAOUserSQL.class);

    public DAOUserSQL() throws SQLException {
        conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:54320/dpfsd",
                "appuser",
                "appPassword"
        );
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        Statement stmt = conn.createStatement();
        String sqlQuery = "SELECT * FROM users WHERE username = '" + username + "'";
        stmt.execute(sqlQuery);
        ResultSet rs = stmt.getResultSet();

        if (rs.next()) {
            //logger.info("ResultSet: " + rs.getString("username") + " with Password: " + rs.getString("password"));
            return new User(rs.getString("username"),
                            rs.getString("password"),
                            UserRole.valueOf(rs.getString("role")),
                            rs.getString("name"),
                            rs.getString("surname"),
                            Level.valueOf(rs.getString("level")),
                            rs.getString("companyIdentificationCode"),
                            rs.getString("image"),
                            rs.getString("email"),
                            rs.getString("phone"),
                            rs.getString("address"));
        }
        return null;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String sqlUpdate = "UPDATE users SET name = '" + user.getName() +
                                         "', surname = '" + user.getSurname() +
                                         "', level = '" + user.getLevel() +
                                         "', email = '" + user.getEmail() +
                                         "', phone = '" + user.getPhone() +
                                         "', address = '" + user.getAddress() +
                                         "' WHERE username = '" + user.getUsername() + "'";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sqlUpdate);
        }
    }

}
