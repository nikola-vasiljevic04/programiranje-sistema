package org.raflab.authservice.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {
    
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder().withoutPadding();

    public static String generateNewKey() {
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        
        return "user_" + base64Encoder.encodeToString(randomBytes);
    }
}