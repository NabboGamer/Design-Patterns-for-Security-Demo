package it.unibas.dao;

import it.unibas.model.User;
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
                                    rs.getString("name"),
                                    rs.getString("surname"),
                                    rs.getString("level"),
                                    rs.getString("companyIdentificationCode"),
                                    rs.getString("image"));
                }
            }
        }
        return null;
    }

}
