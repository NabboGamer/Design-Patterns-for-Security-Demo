package it.unibas.util;

import java.security.SecureRandom;

/// Classe di utilità che genera id alfanumerici(privi di caratteri speciali)
public class IdGenerator {
    private static final SecureRandom random = new SecureRandom();
    private static final String ALPHANUM = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    // Genera ID casuale di lunghezza specificata
    public static String generate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for(int i = 0; i < length; i++) {
            sb.append(ALPHANUM.charAt(random.nextInt(ALPHANUM.length())));
        }
        return sb.toString();
    }

    // Genera ID di 10 caratteri (default)
    public static String generate() {
        return generate(10);
    }
}