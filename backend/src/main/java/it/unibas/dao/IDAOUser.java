package it.unibas.dao;

import it.unibas.model.User;

import java.sql.SQLException;

public interface IDAOUser {
    User findByUsername(String username) throws SQLException;
    void updateUser(User user) throws SQLException;
}
