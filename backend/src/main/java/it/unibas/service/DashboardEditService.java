package it.unibas.service;

import it.unibas.dao.DAOUserSQL;
import it.unibas.dao.IDAOUser;
import it.unibas.dto.UserDTO;
import it.unibas.enums.Level;
import it.unibas.mapper.UserMapper;
import it.unibas.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;

public class DashboardEditService implements IDashboardEditService{

    private static final Logger logger = LoggerFactory.getLogger(DashboardEditService.class);
    private final IDAOUser daoUser;

    public DashboardEditService() throws SQLException {
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

    @Override
    public UserDTO editUserSurname(String username, String password, UserDTO userDTO, String newSurname) throws SQLException {
        userDTO.setSurname(newSurname);
        User newUser = UserMapper.fromDTO(username, password, userDTO);
        try {
            daoUser.updateUser(newUser);
        } catch (SQLException sqlException) {
            logger.error("Errore con la connessione al DB", sqlException);
            throw new SQLException("Errore interno al server durante l'aggiornamento della dashboard");
        }
        return userDTO;
    }

    @Override
    public UserDTO editUserEmail(String username, String password, UserDTO userDTO, String newEmail) throws SQLException {
        userDTO.setEmail(newEmail);
        User newUser = UserMapper.fromDTO(username, password, userDTO);
        try {
            daoUser.updateUser(newUser);
        } catch (SQLException sqlException) {
            logger.error("Errore con la connessione al DB", sqlException);
            throw new SQLException("Errore interno al server durante l'aggiornamento della dashboard");
        }
        return userDTO;
    }

    @Override
    public UserDTO editUserPhone(String username, String password, UserDTO userDTO, String newPhone) throws SQLException {
        userDTO.setPhone(newPhone);
        User newUser = UserMapper.fromDTO(username, password, userDTO);
        try {
            daoUser.updateUser(newUser);
        } catch (SQLException sqlException) {
            logger.error("Errore con la connessione al DB", sqlException);
            throw new SQLException("Errore interno al server durante l'aggiornamento della dashboard");
        }
        return userDTO;
    }

    @Override
    public UserDTO editUserAddress(String username, String password, UserDTO userDTO, String newAddress) throws SQLException {
        userDTO.setAddress(newAddress);
        User newUser = UserMapper.fromDTO(username, password, userDTO);
        try {
            daoUser.updateUser(newUser);
        } catch (SQLException sqlException) {
            logger.error("Errore con la connessione al DB", sqlException);
            throw new SQLException("Errore interno al server durante l'aggiornamento della dashboard");
        }
        return userDTO;
    }

    @Override
    public UserDTO editLevel(String username, String password, UserDTO userDTO, String newLevel) throws SQLException {
        userDTO.setLevel(Level.valueOf(newLevel));
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
