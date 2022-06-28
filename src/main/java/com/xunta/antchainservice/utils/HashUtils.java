package com.xunta.antchainservice.utils;

import java.math.BigInteger;
import java.security.MessageDigest;

public class HashUtils {
    public static String generateSHA256String(String input) {
        try {
            MessageDigest msg = MessageDigest.getInstance("SHA-256");
            msg.update(input.getBytes(), 0, input.length());
            return new BigInteger(1, msg.digest()).toString(16);
        } catch (Exception e) {
            return "";
        }
    }
}
