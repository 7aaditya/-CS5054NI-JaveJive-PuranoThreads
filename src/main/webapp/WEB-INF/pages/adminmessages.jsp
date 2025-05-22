<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Messages</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminmessages.css">
    <style>
      
    </style>
</head>
<body>
    <nav>
        <div class="logo">
            <img src="images/SiteLogo.png" alt="Logo">
        </div>
        
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/Home">Home</a>
            <a href="${pageContext.request.contextPath}/ShopPage">Shop</a>
            <a href="${pageContext.request.contextPath}/aboutus">About Us</a>
            <a href="${pageContext.request.contextPath}/FAQsPage">FAQs</a>
        </div>
        
        <div class="nav-buttons">
            <a href="UserLoginPage.html">
                <button data-tooltip="My Account" class="active">
                    <img src="images/accounticon.png" alt="Search">
                </button>
            </a>
            <a href="MyFavouritesPage.html">
                <button data-tooltip="My Favourites">
                    <img src="images/wishlisticon.png" alt="User">
                </button>
            </a>
            <a href="ShoppingCartPage.html">
                <button data-tooltip="Shopping Cart">
                    <img src="images/carticon.png" alt="Cart">
                </button>
            </a>
        </div>
    </nav>

    <div class="dashboard-container">
        <div class="side-nav">
            <div class="admin-profile">
                <img src="images/AdminProfile.png" alt="Admin Profile">
                <span>Admin</span>
            </div>
            <a href="${pageContext.request.contextPath}/admindashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/adminlisting">Listing</a>
            <a href="${pageContext.request.contextPath}/adminorders">Orders</a>
            <a href="${pageContext.request.contextPath}/adminmessages" class="active">Messages</a>
        </div>
        
        <div class="messages-container">
            <h1>Messages</h1>
            <div class="line-divider"></div>
            <div class="outer-container" style="height: 400px;">
                <!-- Header Section -->
                <div class="messages-header">
                    <p>RECEIVED MESSAGES</p>
                </div>
                <hr>

                <div class="scrollable-content" id="messages-list">
                    <%
                    // Get the reviews from the request attribute
                    List<Map<String, Object>> reviews = (List<Map<String, Object>>) request.getAttribute("reviews");
                    
                    // Check if there are any reviews
                    if (reviews == null || reviews.isEmpty()) {
                    %>
                        <div class="message-item">
                            <p>No messages found.</p>
                        </div>
                    <%
                    } else {
                        // Loop through the reviews and display them
                        for (Map<String, Object> review : reviews) {
                            int reviewId = (Integer) review.get("reviewID");
                            String reviewName = (String) review.get("reviewName");
                            String reviewType = (String) review.get("reviewType");
                            int reviewTime = (Integer) review.get("reviewTime");
                            
                            // Format the review name for display
                            String displayName = reviewName;
                            if (reviewName.length() > 30) {
                                displayName = reviewName.substring(0, 30) + "...";
                            }
                            
                            // Format the timestamp
                            Date date = new Date(reviewTime * 1000L);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String formattedDate = sdf.format(date);
                    %>
                        <div class="message-item">
                            <div class="message-summary">
                                <p class="message-details">
                                    MESSAGE <%= reviewId %> ➜ <%= displayName %>
                                </p>
                                <button class="toggle-arrow">▼</button>
                            </div>
                            <div class="message-details-view">
                                <p><%= reviewName %></p>
                                <div class="message-meta">
                                    <span>Type: <%= reviewType %></span>
                                    <span>Time: <%= formattedDate %></span>
                                </div>
                            </div>
                        </div>
                    <%
                        }
                    }
                    %>
                </div>
            </div>
        </div>
    </div>
    
    <script>
        // Wait for the DOM to be fully loaded
        document.addEventListener('DOMContentLoaded', function() {
            console.log("DOM loaded, setting up message toggles");
            
            // Get all message items
            var messageItems = document.querySelectorAll('.message-item');
            console.log("Found " + messageItems.length + " message items");
            
            // Add click handlers to each message item
            messageItems.forEach(function(message) {
                var toggle = message.querySelector('.toggle-arrow');
                
                if (toggle) {
                    console.log("Setting up toggle for message");
                    
                    // Add click event to the entire message item
                    message.addEventListener('click', function(e) {
                        console.log("Message clicked");
                        this.classList.toggle('expanded');
                    });
                    
                    // Add click event to the toggle button
                    toggle.addEventListener('click', function(e) {
                        console.log("Toggle clicked");
                        e.stopPropagation(); // Prevent the message click event from firing
                        message.classList.toggle('expanded');
                    });
                }
            });
        });
    </script>
</body>
</html>