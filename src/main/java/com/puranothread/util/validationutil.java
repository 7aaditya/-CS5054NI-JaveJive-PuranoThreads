package com.puranothread.util;

import java.util.regex.Pattern;

/**
 * Utility class for validating user input such as username, email, password, and contact number.
 */
public class validationutil {

    /**
     * Checks if a string is null or empty after trimming.
     *
     * @param value The string to check.
     * @return true if the string is null or empty; otherwise, false.
     */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    /**
     * Validates a username.
     * Rules: Must start with a letter and be 5 to 20 characters long. 
     * Can contain letters, digits, and underscores.
     *
     * @param username The username to validate.
     * @return true if valid; otherwise, false.
     */
    public static boolean isValidUsername(String username) {
        return username != null && username.matches("^[a-zA-Z][a-zA-Z0-9_]{4,19}$");
    }

    /**
     * Validates an email address using a basic regex pattern.
     *
     * @param email The email to validate.
     * @return true if the email format is valid; otherwise, false.
     */
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    /**
     * Validates a password.
     * Rules: 9 to 15 characters, at least one lowercase letter, one uppercase letter,
     * one digit, and one special character.
     *
     * @param password The password to validate.
     * @return true if the password meets the complexity requirements; otherwise, false.
     */
    public static boolean isValidPassword(String password) {
        String passwordRegex = 
            "^(?=.[a-z])(?=.[A-Z])(?=.\\d)(?=.[@$!%?&])[A-Za-z\\d@$!%?&]{9,15}$";
        return password != null && Pattern.matches(passwordRegex, password);
    }

    /**
     * Validates a Nepali mobile contact number.
     * Rules: Must be 10 digits and start with "98".
     *
     * @param contact The contact number to validate.
     * @return true if valid; otherwise, false.
     */
    public static boolean isValidContact(String contact) {
        return contact != null && contact.matches("^98\\d{8}$");
    }

    /**
     * Validates whether the terms and conditions checkbox is checked.
     *
     * @param isChecked Boolean indicating whether the checkbox was selected.
     * @return true if checked; otherwise, false.
     */
    public static boolean isTermsAccepted(boolean isChecked) {
        return isChecked;
    }

    /**
     * Placeholder for checking if a string is alphanumeric and starts with a letter.
     * Currently returns false by default. Needs implementation if used.
     *
     * @param username The string to validate.
     * @return true if valid; otherwise, false.
     */
    public static boolean isAlphanumericStartingWithLetter(String username) {
        // TODO: Implement this method if required
        return false;
    }
}