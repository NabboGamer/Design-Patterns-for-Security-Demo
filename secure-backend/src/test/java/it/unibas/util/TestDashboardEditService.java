package it.unibas.util;

import it.unibas.dao.DAOUserSQL;
import it.unibas.dao.IDAOUser;
import it.unibas.dto.UserDTO;
import it.unibas.enums.Level;
import it.unibas.mapper.UserMapper;
import it.unibas.model.User;
import it.unibas.service.DashboardEditService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TestDashboardEditService {


    private IDAOUser daoUser;

    private DashboardEditService dashboardEditService;

    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        daoUser = mock(DAOUserSQL.class);
        dashboardEditService = new DashboardEditService();

        userDTO = new UserDTO();
        userDTO.setName("OldName");
        userDTO.setSurname("OldSurname");
        userDTO.setEmail("old@example.com");
        userDTO.setPhone("1234567890");
        userDTO.setAddress("Old Address");
        userDTO.setLevel(Level.OPERAIO);
    }

    @Test
    void testEditUserName() throws SQLException {
        String newName = "NewName";
        User mockUser = mock(User.class);

        try (MockedStatic<UserMapper> mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.fromDTO(anyString(), anyString(), any(UserDTO.class))).thenReturn(mockUser);
            doNothing().when(daoUser).updateUser(any(User.class));

        UserDTO updatedUser = dashboardEditService.editUserName("user", "password", userDTO, newName);

        assertEquals(newName, updatedUser.getName());
        verify(daoUser, times(1)).updateUser(any(User.class));
        }
    }

    @Test
    void testEditUserSurname() throws SQLException {
        String newSurname = "NewSurname";
        User mockUser = mock(User.class);

        try (MockedStatic<UserMapper> mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.fromDTO(anyString(), anyString(), any(UserDTO.class))).thenReturn(mockUser);
            doNothing().when(daoUser).updateUser(any(User.class));

            UserDTO updatedUser = dashboardEditService.editUserSurname("user", "password", userDTO, newSurname);

            assertEquals(newSurname, updatedUser.getSurname());
            verify(daoUser, times(1)).updateUser(any(User.class));
        }
    }

    @Test
    void testEditUserEmail() throws SQLException {
        String newEmail = "new@example.com";
        User mockUser = mock(User.class);

        try (MockedStatic<UserMapper> mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.fromDTO(anyString(), anyString(), any(UserDTO.class))).thenReturn(mockUser);

            doNothing().when(daoUser).updateUser(any(User.class));

            UserDTO updatedUser = dashboardEditService.editUserEmail("user", "password", userDTO, newEmail);

            assertEquals(newEmail, updatedUser.getEmail());
            verify(daoUser, times(1)).updateUser(any(User.class));
        }
    }

    @Test
    void testEditUserPhone() throws SQLException {
        String newPhone = "0987654321";
        User mockUser = mock(User.class);

        try (MockedStatic<UserMapper> mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.fromDTO(anyString(), anyString(), any(UserDTO.class))).thenReturn(mockUser);
            doNothing().when(daoUser).updateUser(any(User.class));

            UserDTO updatedUser = dashboardEditService.editUserPhone("user", "password", userDTO, newPhone);

            assertEquals(newPhone, updatedUser.getPhone());
            verify(daoUser, times(1)).updateUser(any(User.class));
        }
    }

    @Test
    void testEditUserAddress() throws SQLException {
        String newAddress = "New Address";
        User mockUser = mock(User.class);

        try (MockedStatic<UserMapper> mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.fromDTO(anyString(), anyString(), any(UserDTO.class))).thenReturn(mockUser);
            doNothing().when(daoUser).updateUser(any(User.class));

            UserDTO updatedUser = dashboardEditService.editUserAddress("user", "password", userDTO, newAddress);

            assertEquals(newAddress, updatedUser.getAddress());
            verify(daoUser, times(1)).updateUser(any(User.class));
        }
    }

    @Test
    void testEditLevel() throws SQLException {
        String newLevel = Level.AMMINISTRATORE_DELEGATO.name();
        User mockUser = mock(User.class);

        try (MockedStatic<UserMapper> mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.fromDTO(anyString(), anyString(), any(UserDTO.class))).thenReturn(mockUser);
            doNothing().when(daoUser).updateUser(any(User.class));

            UserDTO updatedUser = dashboardEditService.editLevel("user", "password", userDTO, newLevel);

            assertEquals(Level.AMMINISTRATORE_DELEGATO, updatedUser.getLevel());
            verify(daoUser, times(1)).updateUser(any(User.class));
        }
    }
}
