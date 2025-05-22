<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Orders - Purano Threads</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminorders.css">
   
       
</head>
<body>
    <nav>
        <div class="logo">
            <img src="images/SiteLogo.png" alt="Logo">
        </div>
        
        <div class="nav-links">
           <a href="${pageContext.request.contextPath}/Home">Home</a> <a
				href="${pageContext.request.contextPath}/ShopPage">Shop</a> <a
				href="${pageContext.request.contextPath}/AboutUs">About Us</a> <a
				href="${pageContext.request.contextPath}/FAQsPage">FAQs</a>
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
            <a href="admindashboard">Dashboard</a>
            <a href="adminlisting">Listing</a>
            <a href="adminorders" class="active">Orders</a>
            <a href="adminmessages">Messages</a>
        </div>
        <div class="orders-container">
            <h1>Orders</h1>
            <div class="line-divider"></div>
            <div class="outer-container" style="height: 400px;">
                <!-- Header Section -->
                <div class="orders-header">
                    <p>RECENT ORDERS</p>
                    <form action="adminorders" method="get">
                        <input type="text" name="searchId" placeholder="SEARCH BY ID ..." class="search-bar" value="${param.searchId}">
                        <button type="submit" style="display: none;">Search</button>
                    </form>
                    <p>SORTED BY ➜ DATE</p>
                </div>
                <hr>
                
                <!-- Error Message Section -->
                <c:if test="${not empty errorMessage}">
                    <div class="error-message">${errorMessage}</div>
                </c:if>
            
                <!-- Orders Section -->
                <div class="scrollable-content">
                    <!-- Debug information to verify data is being passed -->
                    <c:if test="${empty orders}">
                        <div class="error-message">No orders found in the database. Check your database connection.</div>
                    </c:if>
                    
                    <!-- Display orders from database -->
                    <c:forEach var="order" items="${orders}">
                        <div class="order-item" data-order-id="${order.orderId}">
                            <div class="order-summary">
                                <button class="order-close-btn" title="Remove Order" data-order-id="${order.orderId}">×</button>
                                <p class="order-details">
                                    ORDER ${order.orderId} (${order.orderName}) ➜ Method: ${order.orderMethod}, Time: ${order.orderTime}
                                </p>
                                <button class="toggle-arrow">▼</button>
                            </div>
                            <div class="order-details-view">
                                <p><strong>Order Date:</strong> <fmt:formatDate value="${order.orderDate}" pattern="yyyy-MM-dd"/></p>
                                <p><strong>Order Method:</strong> ${order.orderMethod}</p>
                                <p><strong>Order Time:</strong> ${order.orderTime}</p>
                                <c:if test="${not empty order.userName}">
                                    <p><strong>User:</strong> ${order.userName} (ID: ${order.userId})</p>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <script>
    document.querySelectorAll('.order-item').forEach(order => {
        const toggle = order.querySelector('.toggle-arrow');
        
        order.addEventListener('click', (e) => {
            // Toggle expansion on entire order item click
            order.classList.toggle('expanded');
        });
    
        // Prevent double trigger when clicking the arrow
        toggle.addEventListener('click', (e) => {
            e.stopPropagation();
            order.classList.toggle('expanded');
        });
    });
    </script>
</body>
</html>