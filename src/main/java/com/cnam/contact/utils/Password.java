package com.cnam.contact.utils;

import com.cnam.contact.bean.User;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;


public class Password {
    /**
     * saveHashedPassword
     *
     * Generates salt and hashes the given clear text password. Both the hashed password
     * and salt are encoded in Base64 and saved to the user database.
     *
     * Note: In this development version, the password and salt Base64 strings are
     * printed to the console for manual pasting into the appropriate database columns.
     *
     * @param clearTextPassword The clear text password to be hashed and salted
     */
    public static void saveHashedPassword (User user, String clearTextPassword) {
        String hashedPassword;
        byte[] saltBytes;
        String salt;

        saltBytes = getSalt();
        hashedPassword = hashAndSaltPassword(clearTextPassword, saltBytes);
        salt = Base64.getEncoder().encodeToString(saltBytes);
        // persist salt and hash or return them to delegate this task to other component
        // System.out.println(hashedPassword.length() + "\tpwd \t" + hashedPassword);
        user.setPasswordHash(hashedPassword);
        user.setSalt(salt);
        //return user;
    }

    public static String getToken() {
        UUID uuid = UUID.randomUUID();

        return uuid.toString();
    }


    /**
     * hashAndSaltPassword
     *
     * Generates a hashed and salted Base64 password from a clear text password and
     * salt byte array.
     *
     * @param clearTextPassword The clear text password to hash and salt
     * @param salt The byte array salt value to use in hashing
     * @return The hashed and salted password
     */
    private static String hashAndSaltPassword(String clearTextPassword, byte[] salt) {
        byte[] hashedBytes;
        String hashedPassword = "";

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-512");
            digest.update(salt);
            hashedBytes = digest.digest(clearTextPassword.getBytes(StandardCharsets.UTF_8));
            hashedPassword = Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }

        return hashedPassword;
    }

    /**
     * getSalt
     *
     * Generates a salt byte array
     *
     * @return A byte array salt value
     */
    private static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
}
