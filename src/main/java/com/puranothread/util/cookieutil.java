package com.puranothread.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;

/**
 * Utility class for managing HTTP cookies in a web application.
 * Provides methods to add, retrieve, and delete cookies.
 */
public class cookieutil {

    /**
     * Adds a cookie to the HTTP response with the given name, value, and max age.
     *
     * @param response The HttpServletResponse object to which the cookie will be added.
     * @param name     The name of the cookie.
     * @param value    The value to be stored in the cookie.
     * @param maxAge   The maximum age of the cookie in seconds. Use -1 for a session cookie.
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);      // Sets the duration the cookie should exist
        cookie.setPath("/");           // Makes the cookie accessible throughout the application
        response.addCookie(cookie);    // Adds the cookie to the response
    }

    /**
     * Retrieves a cookie from the HTTP request by its name.
     *
     * @param request The HttpServletRequest object containing the client's cookies.
     * @param name    The name of the cookie to retrieve.
     * @return The Cookie object if found; otherwise, returns null.
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        if (request.getCookies() != null) {
            return Arrays.stream(request.getCookies())
                    .filter(cookie -> name.equals(cookie.getName()))  // Filters by cookie name
                    .findFirst()                                      // Returns the first matching cookie
                    .orElse(null);                                    // Returns null if no match found
        }
        return null;
    }

    /**
     * Deletes a cookie from the client by adding a new cookie with the same name and max age set to 0.
     *
     * @param response The HttpServletResponse object to which the deletion cookie will be added.
     * @param name     The name of the cookie to delete.
     */
    public static void deleteCookie(HttpServletResponse response, String name) {
        Cookie cookie = new Cookie(name, "");
        cookie.setMaxAge(0);          // Setting max age to 0 instructs the browser to delete the cookie
        cookie.setPath("/");          // Ensures the path matches the original cookie
        response.addCookie(cookie);   // Sends the deletion cookie to the client
    }
}