package it.unibas.service;

import it.unibas.dto.UserDTO;

import java.sql.SQLException;

public interface IAuthService {
    UserDTO login(String username, String password) throws SQLException;
}
