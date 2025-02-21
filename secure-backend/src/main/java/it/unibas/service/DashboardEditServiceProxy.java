package it.unibas.service;

import it.unibas.dto.UserDTO;

import java.sql.SQLException;

public class DashboardEditServiceProxy implements IDashboardEditService {

    private IDashboardEditService dashboardEditService;

    public DashboardEditServiceProxy(IDashboardEditService dashboardEditService) {
        this.dashboardEditService = dashboardEditService;
    }

    @Override
    public UserDTO editUserName(String username, String password, UserDTO userDTO, String newName) throws IllegalArgumentException, SQLException {
        if (newName == null || newName.length() < 2 || newName.length() > 50 || !isValidString(newName)) {
            throw new IllegalArgumentException("Nuovo nome non valido!");
        }
        return dashboardEditService.editUserName(username, password, userDTO, newName);
    }

    private boolean isValidString(String inputString) {
        String regex = "^(?=.*\\p{L})(?!.*'.*')[\\p{L}']+$";
        return inputString.matches(regex);
    }
}
