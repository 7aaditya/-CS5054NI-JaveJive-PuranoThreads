package com.puranothread.controller;

import java.io.IOException;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import com.puranothread.service.profileservice;
import com.puranothread.model.usermodel;

@WebServlet(asyncSupported = true, urlPatterns = { "/profile" })
public class profilecontroller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private profileservice profileService;
    
    public void init() throws ServletException {
        profileService = new profileservice();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("Profile page GET method called");
        
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        
        // If user is not logged in, redirect to login page
        if (username == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        
        try {
            // Fetch user data from database
            usermodel user = profileService.getUserByUsername(username);
            
            if (user != null) {
                // Set user data as request attributes
                request.setAttribute("user", user);
            } else {
                request.setAttribute("errorMessage", "User not found.");
            }
            
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Error fetching user data. Please try again later.");
        }
        
        request.getRequestDispatcher("/WEB-INF/pages/profilepage.jsp").forward(request, response);
    }
}