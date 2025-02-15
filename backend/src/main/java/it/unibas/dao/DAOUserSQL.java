package it.unibas.dao;

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
            return new User(rs.getString("username"), rs.getString("password"));
        }
        return null;
    }

}
