//package com.puranothread.filter;
//
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.HashSet;
//import java.util.Set;
//
//import com.puranothread.util.cookieutil;
//import com.puranothread.util.sessionutil;
//
//@WebFilter(asyncSupported = true, urlPatterns = "/*")
//public class authentication implements Filter {
//
//    // Public pages accessible to all users
//    private static final Set<String> PUBLIC_PAGES = new HashSet<>(Arrays.asList(
//        "/login", "/register", "/home", "/", "/aboutus", "/shop"
//    ));
//    
//    // Static resources that should bypass authentication
//    private static final Set<String> STATIC_RESOURCES = new HashSet<>(Arrays.asList(
//        ".css", ".js", ".png", ".jpg", ".jpeg", ".gif", ".svg", ".ico"
//    ));
//    
//    // Admin-only pages
//    private static final Set<String> ADMIN_PAGES = new HashSet<>(Arrays.asList(
//        "/admindashboard", "/adminorders", "/adminproducts", "/adminusers"
//    ));
//    
//    // User-only pages
//    private static final Set<String> USER_PAGES = new HashSet<>(Arrays.asList(
//        "/profile", "/orderlist", "/cartpage", "/favoritespage", "/checkout"
//    ));
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        // Initialization logic, if required
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) request;
//        HttpServletResponse res = (HttpServletResponse) response;
//        HttpSession session = req.getSession(false);
//
//        String uri = req.getRequestURI();
//        String contextPath = req.getContextPath();
//        
//        // Remove context path from URI for easier matching
//        String path = uri.substring(contextPath.length());
//        
//        // Allow access to static resources
//        if (isStaticResource(path)) {
//            chain.doFilter(request, response);
//            return;
//        }
//        
//        // Check if user is logged in
//        boolean isLoggedIn = (session != null && sessionutil.getAttribute(req, "username") != null);
//        
//        // Get user role - preferably from session, not cookies for security
//        String userRole = isLoggedIn ? 
//                (String) sessionutil.getAttribute(req, "role") : 
//                (cookieutil.getCookie(req, "role") != null ? cookieutil.getCookie(req, "role").getValue() : null);
//        
//        // Check if the requested page is public
//        boolean isPublicPage = isPublicPage(path);
//        
//        // Logic for authentication and authorization
//        if (isPublicPage) {
//            // Public pages are accessible to everyone
//            chain.doFilter(request, response);
//            return;
//        }
//        
//        if (!isLoggedIn) {
//            // User is not logged in and trying to access protected page
//            // Store the original request URL for redirect after login
//            if (session != null) {
//                sessionutil.setAttribute(req, "redirectUrl", uri);
//            }
//            res.sendRedirect(contextPath + "/login");
//            return;
//        }
//        
//        // User is logged in, check role-based access
//        if ("admin".equals(userRole)) {
//            // Admin can access admin pages and user pages
//            chain.doFilter(request, response);
//        } else if ("user".equals(userRole)) {
//            // User can access user pages but not admin pages
//            if (isAdminPage(path)) {
//                res.sendRedirect(contextPath + "/home");
//            } else {
//                chain.doFilter(request, response);
//            }
//        } else {
//            // Unknown role - treat as not authenticated
//            res.sendRedirect(contextPath + "/login");
//        }
//    }
//
//    @Override
//    public void destroy() {
//        // Cleanup logic, if required
//    }
//    
//    /**
//     * Check if the requested resource is a static file
//     */
//    private boolean isStaticResource(String path) {
//        return STATIC_RESOURCES.stream().anyMatch(path::endsWith);
//    }
//    
//    /**
//     * Check if the requested page is publicly accessible
//     */
//    private boolean isPublicPage(String path) {
//        return PUBLIC_PAGES.contains(path) || 
//               PUBLIC_PAGES.stream().anyMatch(page -> path.startsWith(page + "/"));
//    }
//    
//    /**
//     * Check if the requested page is admin-only
//     */
//    private boolean isAdminPage(String path) {
//        return ADMIN_PAGES.contains(path) || 
//               ADMIN_PAGES.stream().anyMatch(page -> path.startsWith(page + "/"));
//    }
//    
//    /**
//     * Check if the requested page is user-only
//     */
//    private boolean isUserPage(String path) {
//        return USER_PAGES.contains(path) || 
//               USER_PAGES.stream().anyMatch(page -> path.startsWith(page + "/"));
//    }
//}