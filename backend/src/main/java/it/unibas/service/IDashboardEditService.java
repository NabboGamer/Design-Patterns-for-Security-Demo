package it.unibas.service;

import it.unibas.dto.UserDTO;
import it.unibas.exception.InsufficientPermissionException;

import java.sql.SQLException;

public interface IDashboardEditService {

    UserDTO editUserName(String username, String password, UserDTO userDTO, String newName) throws IllegalArgumentException, SQLException;

    UserDTO editUserSurname(String username, String password, UserDTO userDTO, String surname) throws IllegalArgumentException, SQLException;

    UserDTO editUserEmail(String username, String password, UserDTO userDTO, String email) throws IllegalArgumentException, SQLException;

    UserDTO editUserPhone(String username, String password, UserDTO userDTO, String phone) throws IllegalArgumentException, SQLException;

    UserDTO editUserAddress(String username, String password, UserDTO userDTO, String address) throws IllegalArgumentException, SQLException;

    UserDTO editLevel(String username, String password, UserDTO userDTO, String level) throws IllegalArgumentException, InsufficientPermissionException, SQLException;
}
