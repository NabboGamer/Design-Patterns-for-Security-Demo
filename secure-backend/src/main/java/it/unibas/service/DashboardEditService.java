package it.unibas.service;

import it.unibas.dao.DAOUserSQL;
import it.unibas.dao.IDAOUser;
import it.unibas.dto.UserDTO;
import it.unibas.mapper.UserMapper;
import it.unibas.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class DashboardEditService implements IDashboardEditService{

    private static final Logger logger = LoggerFactory.getLogger(DashboardEditService.class);
    private final IDAOUser daoUser;

    public DashboardEditService() {
        daoUser = new DAOUserSQL();
    }

    @Override
    public UserDTO editUserName(String username, String password, UserDTO userDTO, String newName) throws SQLException {
        userDTO.setName(newName);
        User newUser = UserMapper.fromDTO(username, password, userDTO);
        try {
            daoUser.updateUser(newUser);
        } catch (SQLException sqlException) {
            logger.error("Errore con la connessione al DB", sqlException);
            throw new SQLException("Errore interno al server durante l'aggiornamento della dashboard");
        }
        return userDTO;
    }
}
