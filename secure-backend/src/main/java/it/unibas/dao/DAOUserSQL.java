package it.unibas.dao;

import it.unibas.model.User;
import it.unibas.enums.Level;
import it.unibas.enums.UserRole;
import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class DAOUserSQL implements IDAOUser {

    private final PGSimpleDataSource dataSource;
    private static final Logger logger = LoggerFactory.getLogger(DAOUserSQL.class);

    public DAOUserSQL() {
        dataSource = new PGSimpleDataSource() ;
        dataSource.setServerNames(new String[]{"localhost"});
        dataSource.setPortNumbers(new int[]{54321});
        dataSource.setDatabaseName( "dpfsd-1" );
        dataSource.setUser( "appuser" );
        dataSource.setPassword( "appPassword" );
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        String sqlQuery = "SELECT * FROM users WHERE username = ?";
        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sqlQuery)) {
            ps.setString(1, username);
            logger.info("PS: {}", ps);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
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
            }
        }
        return null;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String sqlUpdate = "UPDATE users SET name = ?, surname = ?, level = ?, email = ?, phone = ?, address = ? WHERE username = ?";

        try (Connection conn = dataSource.getConnection(); PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getLevel().toString());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPhone());
            ps.setString(6, user.getAddress());

            ps.setString(7, user.getUsername());

            ps.executeUpdate();
        }
    }


}
