package com.puranothread.controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.puranothread.service.changeusersettingsservice;
import com.puranothread.model.usermodel;

/**
 * Controller servlet that handles viewing and updating user account settings.
 * It manages user data retrieval and update actions based on the session user.
 */
@WebServlet(asyncSupported = true, urlPatterns = { "/changeusersettings" })
public class changeusersettingscontroller extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private changeusersettingsservice userSettingsService;

    /**
     * Initializes the service used to handle user settings changes.
     */
    @Override
    public void init() throws ServletException {
        userSettingsService = new changeusersettingsservice();
    }

    /**
     * Handles HTTP GET requests to load current user settings from the database.
     * Redirects to login page if no user is logged in.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Change user settings GET method called");

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        // Check if user is logged in
        if (username == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        try {
            // Fetch user details using username from session
            usermodel user = userSettingsService.getUserByUsername(username);

            if (user != null) {
                request.setAttribute("user", user); // Set user data to display on JSP
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error fetching user data. Please try again later.");
        }

        // Forward to user settings JSP page
        request.getRequestDispatcher("/WEB-INF/pages/changeusersettings.jsp").forward(request, response);
    }

    /**
     * Handles HTTP POST requests to update the user's settings.
     * Retrieves form data, updates the database, and reflects changes in the session.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Change user settings POST method called");

        HttpSession session = request.getSession();
        String currentUsername = (String) session.getAttribute("username");

        // Check if user is logged in
        if (currentUsername == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        // Get updated values from form submission
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");

        System.out.println("Updating user: " + currentUsername + " to new username: " + username);

        // Set updated values in user model
        usermodel updatedUser = new usermodel();
        updatedUser.setUserName(username);
        updatedUser.setUserEmail(email);
        updatedUser.setUserPhone(phone);

        try {
            // Update user settings in the database
            boolean success = userSettingsService.updateUserSettings(updatedUser, currentUsername);

            if (success) {
                // Update session username if it has changed
                if (!username.equals(currentUsername)) {
                    session.setAttribute("username", username);
                }
                request.setAttribute("successMessage", "User settings updated successfully!");
            } else {
                request.setAttribute("errorMessage", "Failed to update user settings. Please try again.");
            }

            // Re-fetch updated user data for display
            usermodel user = userSettingsService.getUserByUsername(username);
            request.setAttribute("user", user);

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error updating user data. Please try again later.");
        }

        // Forward to user settings JSP page
        request.getRequestDispatcher("/WEB-INF/pages/changeusersettings.jsp").forward(request, response);
    }
}