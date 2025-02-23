package it.unibas.service.observer;

import it.unibas.service.SecurityEventManager;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SecurityLogger implements ISecurityEventObserver {

    private static final String logBaseDir = ConfigProvider.getConfig().getValue("app.log.base-dir", String.class);
    private static final Logger logger = LoggerFactory.getLogger(SecurityLogger.class);

    public SecurityLogger() {
        SecurityEventManager.getInstance().subscribe(this);
    }

    @Override
    public void update(String event) {
        String absoluteLogPath = Paths.get(logBaseDir, "journal.txt").toAbsolutePath().toString();
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(absoluteLogPath, true);
            fileWriter.write(event);
            fileWriter.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String buildLoggingString(String username, String action, String result) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(timestamp).append("]").append(" ")
                .append("UTENTE:").append(username).append(" - ")
                .append("EFFETTUA:").append(action).append(" - ")
                .append("ESITO:").append(result).append("\n");
        return sb.toString();
    }
}
