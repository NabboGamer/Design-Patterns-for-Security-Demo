package it.unibas.mapper;

import it.unibas.dto.UserDTO;
import it.unibas.model.User;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

public class UserMapper {

    private static final Logger logger = LoggerFactory.getLogger(UserMapper.class);
    private static final String baseImageDir = ConfigProvider.getConfig().getValue("app.image.base-dir", String.class);

    public static UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setRole(user.getRole());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setLevel(user.getLevel());
        dto.setCompanyIdentificationCode(user.getCompanyIdentificationCode());

        if (user.getImage() != null && !user.getImage().isBlank()) {
            String absoluteImagePath = Paths.get(baseImageDir, user.getImage()).toAbsolutePath().toString();
//            logger.info("Image path: {}", absoluteImagePath);

            File imageFile = new File(absoluteImagePath);
            if (imageFile.exists() && imageFile.isFile()) {
                try {
                    byte[] imageBytes = Files.readAllBytes(imageFile.toPath());
                    String encodedImage = Base64.getEncoder().encodeToString(imageBytes);
                    String mimeType = URLConnection.guessContentTypeFromName(imageFile.getName());

                    dto.setImage(encodedImage);
                    dto.setImageMimeType(mimeType != null ? mimeType : "application/octet-stream");
                } catch (IOException e) {
                    logger.error("Errore nella lettura dell'immagine", e);
                }
            } else {
                logger.warn("Immagine non trovata per il path: {}", imageFile.getAbsolutePath());
            }
        }
        dto.setEmail(user.getEmail());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        return dto;
    }

    public static User fromDTO(String username, String password, UserDTO dto) {
        if (dto == null) {
            return null;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(dto.getRole());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setLevel(dto.getLevel());
        user.setCompanyIdentificationCode(dto.getCompanyIdentificationCode());

        // Gestione dell'immagine: se il DTO contiene l'immagine in Base64,
        // decodifica e salva il file, quindi imposta il nome del file nel modello
        if (dto.getImage() != null && !dto.getImage().isBlank()) {
            try {
                byte[] imageBytes = Base64.getDecoder().decode(dto.getImage());

                String extension = "jpg"; // default
                if (dto.getImageMimeType() != null) {
                    if (dto.getImageMimeType().equalsIgnoreCase("image/png")) {
                        extension = "png";
                    } else if (dto.getImageMimeType().equalsIgnoreCase("image/jpeg")) {
                        extension = "jpeg";
                    }
                }
                String fileName = user.getUsername() + "." + extension;
                java.nio.file.Path filePath = java.nio.file.Paths.get(UserMapper.baseImageDir, fileName);

                // Salva l'immagine nel file system
                java.nio.file.Files.write(filePath, imageBytes);
                user.setImage(fileName);
            } catch (IOException e) {
                logger.error("Errore durante il salvataggio dell'immagine");
            }
        }
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setAddress(dto.getAddress());
        return user;
    }

}
