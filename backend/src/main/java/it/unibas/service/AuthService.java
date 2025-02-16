package it.unibas.service;

import it.unibas.dao.DAOUserSQL;
import it.unibas.dao.IDAOUser;
import it.unibas.dto.UserDTO;
import it.unibas.mapper.UserMapper;
import it.unibas.model.User;

import java.sql.SQLException;

public class AuthService implements IAuthService {

    private final IDAOUser daoUser;

    public AuthService() throws SQLException {
        daoUser = new DAOUserSQL();
    }

    @Override
    public UserDTO login(String username, String password) throws SQLException {
        User user = daoUser.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return UserMapper.toDTO(user);
        }
        return null;
    }
}
