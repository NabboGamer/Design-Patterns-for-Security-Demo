package it.unibas.service;

import it.unibas.dto.UserDTO;

import java.sql.SQLException;

public interface IDashboardEditService {

    UserDTO editUserName(String username, String password, UserDTO userDTO, String newName) throws SQLException;
}
