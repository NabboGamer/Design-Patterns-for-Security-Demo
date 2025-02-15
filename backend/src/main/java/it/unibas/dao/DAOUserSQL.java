package it.unibas.dao;

import it.unibas.model.User;

import java.sql.*;

public class DAOUserSQL implements IDAOUser {

    private final Connection conn;

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
        ResultSet rs = stmt.executeQuery(sqlQuery);

        if (rs.next()) {
            return new User(
                    rs.getString("username"),
                    rs.getString("password")
            );
        }
        return null;
    }

}
