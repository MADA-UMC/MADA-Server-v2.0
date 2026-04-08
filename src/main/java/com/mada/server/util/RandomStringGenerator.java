package com.mada.server.util;

import java.security.SecureRandom;
import java.util.Base64;

public class RandomStringGenerator {
    public static String generate(){
        byte[] bytes = new byte[32];
        new SecureRandom().nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }
}
