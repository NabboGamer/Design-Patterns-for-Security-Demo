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
                    dto.setImage(encodedImage);
                    String mimeType = URLConnection.guessContentTypeFromName(imageFile.getName());
                    dto.setImageMimeType(mimeType != null ? mimeType : "application/octet-stream");
                } catch (IOException e) {
                    logger.error("Errore nella lettura dell'immagine", e);
                }
            } else {
                logger.warn("Immagine non trovata per il path: {}", imageFile.getAbsolutePath());
            }
        }

        return dto;
    }
}
