package it.unibas.service.observer;

import it.unibas.service.SecurityEventManager;
import org.eclipse.microprofile.config.ConfigProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

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
}
