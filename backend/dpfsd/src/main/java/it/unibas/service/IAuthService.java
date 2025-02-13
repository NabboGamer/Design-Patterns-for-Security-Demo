package it.unibas.service;

import java.sql.SQLException;

public interface IAuthService {
    boolean login(String username, String password) throws SQLException;
}
