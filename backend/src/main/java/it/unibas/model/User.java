package it.unibas.model;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    private String username;
    private String password;
    private String name;
    private String surname;
    private String level;
    private String companyIdentificationCode;
    private String image;

}
