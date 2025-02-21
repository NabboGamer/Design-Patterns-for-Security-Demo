package it.unibas.dto;

import it.unibas.enums.Level;
import it.unibas.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {

    @NotNull(message="Role may not be null")
    @NotBlank(message="Role may not be blank")
    private UserRole role;

    @NotNull(message="Name may not be null")
    @Size(min = 2, max = 50, message = "Name must have a number of characters between 2 and 50")
    private String name;

    @NotNull(message="Surname may not be null")
    @Size(min = 2, max = 50, message = "Surname must have a number of characters between 2 and 50")
    private String surname;

    @NotNull(message="Level may not be null")
    @NotBlank(message="Level may not be blank")
    private Level level;

    @NotNull(message="Company Identification Code may not be null")
    @Size(min = 2, max = 50, message = "Company Identification Code must have a number of characters between 2 and 50")
    private String companyIdentificationCode;

    @NotNull(message="Image may not be null")
    @NotBlank(message="Image may not be blank")
    private String image; // L'immagine in formato Base64

    private String imageMimeType; // Il MIME type dell'immagine (es. "image/jpeg" o "image/png")

    @Email
    private String email;

    @NotNull(message="Phone may not be null")
    @NotBlank(message="Phone may not be blank")
    private String phone;

    @NotNull(message="Address may not be null")
    @Size(min = 2, max = 50)
    private String address;

}

