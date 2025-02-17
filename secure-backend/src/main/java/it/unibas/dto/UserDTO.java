package it.unibas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDTO {
    private String name;
    private String surname;
    private String level;
    private String companyIdentificationCode;

    // L'immagine in formato Base64
    private String image;

    // Il MIME type dell'immagine (es. "image/jpeg" o "image/png")
    private String imageMimeType;
}

