package it.unibas.service;

import it.unibas.dao.DAOUserSQL;
import it.unibas.dao.IDAOUser;
import it.unibas.dto.UserDTO;
import it.unibas.mapper.UserMapper;
import it.unibas.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.AccountLockedException;
import java.sql.SQLException;

public class AuthService implements IAuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final IDAOUser daoUser;

    public AuthService() {
        daoUser = new DAOUserSQL();
    }

    @Override
    public UserDTO login(String username, String password) throws SQLException, AccountLockedException {
        User user = daoUser.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return UserMapper.toDTO(user);
        }
        return null;
    }
}
