package com.puranothread.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet("/favoritespage")
public class favoritespagecontroller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    public favoritespagecontroller() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        
        // Get favorites from session or create a new one
        List<Map<String, Object>> favorites = (List<Map<String, Object>>) session.getAttribute("favorites");
        if (favorites == null) {
            favorites = new ArrayList<>();
            session.setAttribute("favorites", favorites);
        }
        
        // Forward to favorites page
        request.getRequestDispatcher("/WEB-INF/pages/favoritespage.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}