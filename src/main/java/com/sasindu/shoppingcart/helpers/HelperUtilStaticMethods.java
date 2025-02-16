package com.sasindu.shoppingcart.helpers;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;

public class HelperUtilStaticMethods {

    /**
     * Check if the password and confirm password are the same
     *
     * @param password        password
     * @param confirmPassword confirm password
     * @return boolean
     */
    public static boolean isPasswordMatch(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }


    /**
     * Check if the email is valid
     *
     * @param email email
     * @return boolean
     */
    public static boolean isEmailValid(String email) {
        return email.matches("^(.+)@(.+)$");
    }


    /**
     * Check if the password is strong::
     * at least 8 characters,
     * at least 1 digit,
     * at least 1 lower case letter,
     * at least 1 upper case letter,
     * at least 1 special character.
     *
     * @param password password
     * @return boolean
     */
    public static boolean isPasswordStrong(String password) {
        return password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$");
    }


    /**
     * Convert byte array to hex string
     *
     * @param hex hex from byte array
     * @return hex string
     */
    public static byte[] hexStringToByteArray(String hex) {
        int length = hex.length();
        byte[] data = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i + 1), 16));
        }
        return data;
    }


    /**
     * This method is used to get the cookies from a request
     *
     * @param request The request object
     * @return The cookies as a map
     */
    public static String getCookieFromRequest(HttpServletRequest request, String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .filter(cookie -> cookie.getName().equals(cookieName))
                    .findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        }
        return null;
    }

}
