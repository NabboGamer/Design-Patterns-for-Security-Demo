package it.unibas.model;

import it.unibas.util.IdGenerator;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class User {

    //private String id;
    private String username;
    private String password;

    public User(String username, String password) {
        //this.id = IdGenerator.generate(15);
        this.username = username;
        this.password = password;
    }

}
