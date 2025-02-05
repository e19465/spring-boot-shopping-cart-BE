package com.sasindu.shoppingcart.helpers;

public class Utils {

    public static boolean isPasswordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    public static boolean isEmailValid(String email) {
        return email.matches("^(.+)@(.+)$");
    }

    public static boolean isPasswordStrong(String password) {
        // At least 8 characters
        // At least 1 digit
        // At least 1 lower case letter
        // At least 1 upper case letter
        // At least 1 special character
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    }

}
