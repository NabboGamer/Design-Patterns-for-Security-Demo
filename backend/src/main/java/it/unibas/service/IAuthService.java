package it.unibas.service;

import it.unibas.model.User;

import java.sql.SQLException;

public interface IAuthService {
    User login(String username, String password) throws SQLException;
}
