package com.puranothread.controller;

import com.puranothread.model.usermodel;
import com.puranothread.service.loginservice;
import com.puranothread.util.cookieutil;
import com.puranothread.util.sessionutil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class logincontroller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward to login page on GET request
        request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        // Admin login check
        if ("admin".equals(username) && "admin".equals(password)) {
            cookieutil.addCookie(response, "role", "admin", 5 * 30); // expiration 150 seconds
            sessionutil.setAttribute(request, "username", username);
            sessionutil.setAttribute(request, "role", "admin"); // mark admin role in session
            response.sendRedirect(request.getContextPath() + "/admindashboard.jsp"); // ensure this URL maps admin dashboard
            return; // stop further processing
        }

        // User login - create usermodel using the 2-parameter constructor (username, password)
        usermodel loginModel = new usermodel(username, password);
        loginservice loginService = new loginservice();

        Boolean loginStatus = loginService.loginUser(loginModel);

        System.out.println("Login Status: " + loginStatus);

        if (loginStatus != null && loginStatus) {
            sessionutil.setAttribute(request, "username", username);
            sessionutil.setAttribute(request, "role", "user"); // mark user role
            cookieutil.addCookie(response, "role", "user", 5 * 30); // expiration 150 seconds
            response.sendRedirect(request.getContextPath() + "/home");
        } else {
            // Login failed - prepare error message and forward back to login page
            String errorMessage;
            if (loginStatus == null) {
                errorMessage = "Our server is under maintenance. Please try again later!";
            } else {
                errorMessage = "User credential mismatch. Please try again!";
            }

            request.setAttribute("error", errorMessage);
            request.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(request, response);
            System.out.println("Admin login successful");

        }
    }
}