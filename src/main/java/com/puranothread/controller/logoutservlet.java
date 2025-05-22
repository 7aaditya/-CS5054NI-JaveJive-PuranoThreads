package com.puranothread.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.puranothread.util.cookieutil;
import com.puranothread.util.sessionutil;
/**
 * Servlet implementation class logoutservlet
 */
@WebServlet("/logout")
public class logoutservlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Invalidate the session
		sessionutil.invalidateSession(request);
		
		// Delete authentication cookies
		cookieutil.deleteCookie(response, "user_session");
		// If you have multiple cookies to delete, add them here
		cookieutil.deleteCookie(response, "remember_me");
		cookieutil.deleteCookie(response, "JSESSIONID");
		
		// Redirect to login page or home page after logout
		response.sendRedirect(request.getContextPath() + "/login");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Process POST requests by calling doGet
		doGet(request, response);
	}

}