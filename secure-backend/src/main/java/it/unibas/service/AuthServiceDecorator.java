package it.unibas.service;

import it.unibas.dto.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.security.auth.login.AccountLockedException;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AuthServiceDecorator implements IAuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceDecorator.class);
    @SuppressWarnings("FieldCanBeLocal")
    private final int MAX_ATTEMPTS = 5; // Tentativi prima di un blocco temporaneo
    @SuppressWarnings("FieldCanBeLocal")
    private final long BASE_LOCKOUT_DURATION = 30000; // Tempo di attesa base per un blocco temporaneo(15 sec)
    @SuppressWarnings("FieldCanBeLocal")
    private final int MAX_TEMP_LOCKOUTS = 3; // Blocchi temporanei prima del blocco permanente
    private final Map<String, Integer> failedAttemptsMap    = new ConcurrentHashMap<>();
    private final Map<String, Long>    lockoutTimestampMap  = new ConcurrentHashMap<>();
    private final Map<String, Integer> lockoutCountMap      = new ConcurrentHashMap<>();
    private final Map<String, Boolean> permanentLockMap     = new ConcurrentHashMap<>();
    private final IAuthService wrappedService;

    public AuthServiceDecorator(IAuthService service) {
        this.wrappedService = service;
    }

    @Override
    public UserDTO login(String username, String password) throws SQLException, AccountLockedException {
        long currentTime = System.currentTimeMillis();

        // Controllo blocco permanente
        if (permanentLockMap.containsKey(username)) {
            logger.error("Account {} bloccato permanentemente", username);
            throw new AccountLockedException("Account bloccato permanentemente. Contattare l'amministratore");
        }

        // Controllo blocco temporaneo
        Long lockoutTime = lockoutTimestampMap.get(username);
        if (lockoutTime != null) {
            int lockoutCount = lockoutCountMap.getOrDefault(username, 1);
            long lockoutDuration = BASE_LOCKOUT_DURATION * (long) Math.pow(2, lockoutCount - 1);

            if ((currentTime - lockoutTime) < lockoutDuration) {
                long remaining = (lockoutDuration - (currentTime - lockoutTime)) / 1000;
                logger.warn("Account {} bloccato temporaneamente. Riprovare tra {} secondi", username, remaining);
                throw new AccountLockedException("Account bloccato temporaneamente. Riprovare tra " + remaining + " secondi");
            } else {
                // Sblocca l'account
                lockoutTimestampMap.remove(username);
                failedAttemptsMap.remove(username);
            }
        }

        // Tentativo di login
        UserDTO user = wrappedService.login(username, password);

        if (user == null) {
            // Aggiorna tentativi falliti
            int attempts = failedAttemptsMap.merge(username, 1, Integer::sum);

            // Blocco dopo MAX_ATTEMPTS
            if (attempts >= MAX_ATTEMPTS) {
                int lockoutCount = lockoutCountMap.merge(username, 1, Integer::sum);

                if (lockoutCount > MAX_TEMP_LOCKOUTS) {
                    permanentLockMap.put(username, true);
                    logger.error("Account {} bloccato permanentemente", username);
                    throw new AccountLockedException("Account bloccato permanentemente. Contattare l'amministratore");
                } else {
                    long duration = BASE_LOCKOUT_DURATION * (long) Math.pow(2, lockoutCount - 1);
                    lockoutTimestampMap.put(username, currentTime);
                    logger.warn("Numero massimo di tentativi di login falliti raggiunto per l'account {}", username);
                    throw new AccountLockedException("Numero massimo di tentativi di login falliti raggiunto per l'account " + username);
                }
            }
            // L'utente ha fallito ma non ha superato ancora i MAX_ATTEMPTS
            logger.error("Account {} tentativo di login fallito ({} di {} tentativi)", username, attempts, MAX_ATTEMPTS);
            return null;
        } else {
            // Reset contatori su login riuscito
            failedAttemptsMap.remove(username);
            lockoutTimestampMap.remove(username);
            lockoutCountMap.remove(username);
            return user;
        }
    }
}
