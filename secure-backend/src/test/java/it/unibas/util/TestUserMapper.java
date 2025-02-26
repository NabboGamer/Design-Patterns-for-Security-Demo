package it.unibas.util;

import it.unibas.dto.UserDTO;
import it.unibas.mapper.UserMapper;
import it.unibas.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;
public class TestUserMapper {
    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("securePass");
        user.setName("Mario");
        user.setSurname("Rossi");
        user.setEmail("mario.rossi@example.com");
        user.setPhone("1234567890");
        user.setAddress("Via Roma 10");
        user.setImage("testImage.png");

        userDTO = new UserDTO();
        userDTO.setName("Mario");
        userDTO.setSurname("Rossi");
        userDTO.setEmail("mario.rossi@example.com");
        userDTO.setPhone("1234567890");
        userDTO.setAddress("Via Roma 10");
    }

    @Test
    void test_toDTO_when_user_is_valid() {
        UserDTO dto = UserMapper.toDTO(user);
        assertNotNull(dto);
        assertEquals(user.getName(), dto.getName());
        assertEquals(user.getSurname(), dto.getSurname());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getPhone(), dto.getPhone());
        assertEquals(user.getAddress(), dto.getAddress());
    }

    @Test
    void test_toDTO_when_user_is_null() {
        assertNull(UserMapper.toDTO(null));
    }

    @Test
    void test_fromDTO_when_DTO_is_valid() {
        User newUser = UserMapper.fromDTO("newUser", "newPass", userDTO);
        assertNotNull(newUser);
        assertEquals("newUser", newUser.getUsername());
        assertEquals("newPass", newUser.getPassword());
        assertEquals(userDTO.getName(), newUser.getName());
        assertEquals(userDTO.getSurname(), newUser.getSurname());
        assertEquals(userDTO.getEmail(), newUser.getEmail());
        assertEquals(userDTO.getPhone(), newUser.getPhone());
        assertEquals(userDTO.getAddress(), newUser.getAddress());
    }

    @Test
    void test_fromDTO_when_DTO_is_Null() {
        assertNull(UserMapper.fromDTO("username", "password", null));
    }

    @Test
    void test_fromDTO_with_Image() {
        String base64Image = Base64.getEncoder().encodeToString("testImageContent".getBytes());
        userDTO.setImage(base64Image);
        userDTO.setImageMimeType("image/png");
        User newUser = UserMapper.fromDTO("imageUser", "pass", userDTO);
        assertNotNull(newUser);
        assertNotNull(newUser.getImage());
        assertTrue(newUser.getImage().endsWith(".png"));
    }
}
