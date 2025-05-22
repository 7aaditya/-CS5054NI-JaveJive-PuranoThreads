package com.puranothread.util;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

/**
 * Utility class for managing HTTP session attributes and lifecycle.
 * Provides methods to set, retrieve, remove attributes, and invalidate or delete sessions.
 */
public class sessionutil {

    /**
     * Sets an attribute in the HTTP session. If the session does not exist, it will be created.
     *
     * @param request The HttpServletRequest from which the session is derived.
     * @param key     The name of the attribute to be stored in the session.
     * @param value   The value of the attribute.
     */
    public static void setAttribute(HttpServletRequest request, String key, Object value) {
        HttpSession session = request.getSession();  // Create session if it doesn't exist
        session.setAttribute(key, value);
    }

    /**
     * Retrieves an attribute from the HTTP session.
     *
     * @param request The HttpServletRequest from which the session is derived.
     * @param key     The name of the attribute to retrieve.
     * @return The value of the attribute if found, otherwise null.
     */
    public static Object getAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);  // Do not create session if it doesn't exist
        if (session != null) {
            return session.getAttribute(key);
        }
        return null;
    }

    /**
     * Removes a specific attribute from the session.
     *
     * @param request The HttpServletRequest from which the session is derived.
     * @param key     The name of the attribute to remove.
     */
    public static void removeAttribute(HttpServletRequest request, String key) {
        HttpSession session = request.getSession(false);  // Do not create session if it doesn't exist
        if (session != null) {
            session.removeAttribute(key);
        }
    }

    /**
     * Invalidates the entire session, removing all stored attributes.
     *
     * @param request The HttpServletRequest from which the session is derived.
     */
    public static void invalidateSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);  // Do not create session if it doesn't exist
        if (session != null) {
            session.invalidate();  // Invalidate the session
        }
    }

    /**
     * Deletes the session by first removing all attributes and then invalidating it.
     * This provides a more thorough cleanup than simple invalidation.
     *
     * @param request The HttpServletRequest from which the session is derived.
     */
    public static void deleteSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);  // Do not create session if it doesn't exist
        if (session != null) {
            // Remove all session attributes
            java.util.Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String attributeName = attributeNames.nextElement();
                session.removeAttribute(attributeName);
            }
            // Invalidate the session
            session.invalidate();
        }
    }
}