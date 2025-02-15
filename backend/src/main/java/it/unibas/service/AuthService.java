package it.unibas.service;

import it.unibas.dao.DAOUserSQL;
import it.unibas.dao.IDAOUser;
import it.unibas.model.User;

import java.sql.SQLException;

public class AuthService implements IAuthService {

    private final IDAOUser daoUser;

    public AuthService() throws SQLException {
        daoUser = new DAOUserSQL();
    }

    @Override
    public User login(String username, String password) throws SQLException {
        User user = daoUser.findByUsername(username);
        return (user != null && user.getPassword().equals(password)) ? user : null;
    }
}
