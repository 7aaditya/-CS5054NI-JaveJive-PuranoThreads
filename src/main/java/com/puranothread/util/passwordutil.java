package com.puranothread.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for password-related operations such as hashing.
 */
public class passwordutil {

    /**
     * Hashes a plaintext password using the SHA-256 algorithm.
     *
     * @param password The plaintext password to hash.
     * @return The hashed password as a hexadecimal string.
     * @throws RuntimeException if the SHA-256 algorithm is not available on the platform.
     */
    public static String hashPassword(String password) {
        try {
            // Create a MessageDigest instance for SHA-256 hashing
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Perform the hash on the input password bytes
            byte[] hashedBytes = md.digest(password.getBytes());

            // Convert the hashed byte array into a hexadecimal string
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));  // Format each byte as a two-digit hex value
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // SHA-256 should always be available; this exception is highly unlikely
            throw new RuntimeException("Error hashing password", e);
        }
    }
}