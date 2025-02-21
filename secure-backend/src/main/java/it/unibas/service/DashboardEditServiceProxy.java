package it.unibas.service;

import it.unibas.dto.UserDTO;
import it.unibas.enums.Level;
import it.unibas.enums.UserRole;
import it.unibas.exception.InsufficientPermissionException;

import javax.management.relation.Role;
import java.sql.SQLException;

public class DashboardEditServiceProxy implements IDashboardEditService {

    private IDashboardEditService dashboardEditService;

    public DashboardEditServiceProxy(IDashboardEditService dashboardEditService) {
        this.dashboardEditService = dashboardEditService;
    }

    @Override
    public UserDTO editUserName(String username, String password, UserDTO userDTO, String newName) throws IllegalArgumentException, SQLException {
        if (newName == null || newName.length() < 2 || newName.length() > 50 || !isValidString(newName)) {
            throw new IllegalArgumentException("Nome non valido!");
        }
        return dashboardEditService.editUserName(username, password, userDTO, newName);
    }

    @Override
    public UserDTO editUserSurname(String username, String password, UserDTO userDTO, String newSurname) throws IllegalArgumentException, SQLException {
        if (newSurname == null || newSurname.length() < 2 || newSurname.length() > 50 || !isValidString(newSurname)) {
            throw new IllegalArgumentException("Cognome non valido!");
        }
        return dashboardEditService.editUserSurname(username, password, userDTO, newSurname);
    }

    @Override
    public UserDTO editUserEmail(String username, String password, UserDTO userDTO, String newEmail) throws IllegalArgumentException, SQLException {
        if (newEmail == null || newEmail.length() < 2 || newEmail.length() > 50 || !isValidEmail(newEmail)) {
            throw new IllegalArgumentException("Email non valida!");
        }
        return dashboardEditService.editUserEmail(username, password, userDTO, newEmail);
    }

    @Override
    public UserDTO editUserPhone(String username, String password, UserDTO userDTO, String newPhone) throws IllegalArgumentException, SQLException {
        if (newPhone == null || newPhone.length() < 2 || newPhone.length() > 50 || !isValidPhoneNumber(newPhone)) {
            throw new IllegalArgumentException("Numero di telefono non valido!");
        }
        return dashboardEditService.editUserPhone(username, password, userDTO, newPhone);
    }

    @Override
    public UserDTO editUserAddress(String username, String password, UserDTO userDTO, String newAddress) throws IllegalArgumentException, SQLException {
        if (newAddress == null || newAddress.length() < 2 || newAddress.length() > 50 || !isValidAddress(newAddress)) {
            throw new IllegalArgumentException("Indirizzo non valido!");
        }
        return dashboardEditService.editUserAddress(username, password, userDTO, newAddress);
    }

    @Override
    public UserDTO editLevel(String username, String password, UserDTO userDTO, String newLevel) throws IllegalArgumentException, InsufficientPermissionException, SQLException {
        if (newLevel == null || newLevel.length() < 2 || newLevel.length() > 50 || !isValidLevel(newLevel)) {
            throw new IllegalArgumentException("Livello non valido!");
        }
        if(userDTO.getRole() != UserRole.ADMIN){
            throw new InsufficientPermissionException("Permessi insufficienti per modificare questo campo");
        }
        return dashboardEditService.editLevel(username, password, userDTO, newLevel);
    }

    private boolean isValidString(String inputString) {
        String regex = "^(?=.*\\p{L})(?!.*'.*')[\\p{L}'\\s]+$";
        return inputString.matches(regex);
    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(regex);
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        String regex = "^(?:(?:\\+39|0039)\\s?)?3\\d{2}\\s?\\d{3}\\s?\\d{4}$";
        return phoneNumber.matches(regex);
    }

    private boolean isValidAddress(String address) {
        String regex = "^(?=.*\\p{L})[\\p{L}\\p{N}\\s,.'-]+(?:\\s*,?\\s+\\d+[a-zA-Z]?)?$";
        return address.matches(regex);
    }

    private boolean isValidLevel(String newLevel) throws IllegalArgumentException {
        try{
            Level.valueOf(newLevel);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new IllegalArgumentException("Livello non valido!");
        }
        return Level.OPERAIO == Level.valueOf(newLevel) ||
               Level.CAPO_REPARTO == Level.valueOf(newLevel) ||
               Level.CAPO_SEZIONE == Level.valueOf(newLevel) ||
               Level.AMMINISTRATORE_DELEGATO == Level.valueOf(newLevel);
    }

}
