package com.puranothread.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;

import com.puranothread.service.registerservice;
import com.puranothread.util.validationutil;
import com.puranothread.model.usermodel;
import com.puranothread.util.passwordutil;

/**
 * Servlet controller to handle user registration.
 * Processes input validation, password hashing, and calls the registration service.
 */
@WebServlet("/register")
public class rregisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private registerservice registerService;

	/**
	 * Initializes the servlet and creates an instance of the registration service.
	 */
	@Override
	public void init() throws ServletException {
		registerService = new registerservice();
	}

	/**
	 * Handles the HTTP POST request for user registration.
	 * Validates input fields, hashes the password, and stores user data.
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {

	    // Retrieve form input values
	    String username = request.getParameter("username");
	    String email = request.getParameter("email");
	    String password = request.getParameter("password");
	    String contact = request.getParameter("contact");

	    // Hash the password before storing it in the database
	    String hashedPassword = passwordutil.hashPassword(password);

	    // Validate user inputs using the utility class
	    String errormessage = null;
	    if (!validationutil.isValidUsername(username)) {
	        errormessage = "Invalid username (must start with a letter, 5–20 characters).";
	    } else if (!validationutil.isValidEmail(email)) {
	        errormessage = "Invalid email format.";
	    } else if (!validationutil.isValidPassword(password)) {
	        errormessage = "Password must be 9–15 characters, include an uppercase letter, number, and special symbol.";
	    } else if (!validationutil.isValidContact(contact)) {
	        errormessage = "Invalid contact number (should follow Nepal format: 98XXXXXXXX).";
	    }

	    // If there is any validation error, return user to registration form with input data
	    if (errormessage != null) {
	        request.setAttribute("Error message", errormessage);
	        request.setAttribute("username", username);
	        request.setAttribute("contactnumber", contact);
	        request.setAttribute("email", email);
	        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	        return;
	    }

	    // Create a new user object and populate it with validated and processed data
	    usermodel newuser = new usermodel();
	    newuser.setUserName(username);
	    newuser.setUserPhone(contact);
	    newuser.setUserEmail(email);
	    newuser.setUserPassword(hashedPassword);

	    // Attempt to register the user using the service class
	    try {
	        boolean success = registerService.userRegister(newuser);

	        if (success) {
	            // If registration successful, set session attribute and redirect to login page
	            request.getSession().setAttribute("registrationsuccess", "success");
	            response.sendRedirect(request.getContextPath() + "/login");
	        } else {
	            // If registration fails, forward back with error message
	            request.setAttribute("Error message", "Registration failed. Please try again.");
	            request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	        }

	    } catch (SQLException | ClassNotFoundException e) {
	        // Handle server-side exceptions gracefully
	        e.printStackTrace();
	        request.setAttribute("Error message", "Server error. Please try again later.");
	        request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	    }
	}

	/**
	 * Handles the HTTP GET request to display the registration form.
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    request.getRequestDispatcher("/WEB-INF/pages/register.jsp").forward(request, response);
	}
}