package it.unibas.model;

import it.unibas.enums.Level;
import it.unibas.enums.UserRole;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private String username;
    private String password;

    private UserRole role;
    private String name;
    private String surname;
    private Level  level;
    private String companyIdentificationCode;
    private String image;
    private String email;
    private String phone;
    private String address;

}
