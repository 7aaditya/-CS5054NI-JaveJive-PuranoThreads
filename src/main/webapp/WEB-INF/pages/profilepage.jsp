<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Profile - Purano Thread</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/profilepage.css">
</head>
<body>
    <nav>
        <div class="logo">
            <img src="images/SiteLogo.png" alt="Logo">
        </div>
        
        <div class="nav-links">
            <a href="HomePage.html">Home</a>
            <a href="ShopPage.html">Shop</a>
            <a href="AboutUsPage.html">About Us</a>
            <a href="FAQsPage.html">FAQs</a>
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

    <div class="profile-container">
        <div class="side-nav">
            <a href="${pageContext.request.contextPath}/profile" class="active">My Profile</a>
            <a href="${pageContext.request.contextPath}/changeusersettings">Change User Settings</a>
           
        </div>
        <div class="content-container">
            <div class="profile-header">
                <div class="profile-picture-container">
                    <img src="images/default-profile.png" alt="Profile Picture" class="profile-picture">
                </div>
                <h1>My Profile</h1>
            </div>
            <hr class="profile-divider">
            
            <% if(request.getAttribute("errorMessage") != null) { %>
                <div class="error-message">
                    <%= request.getAttribute("errorMessage") %>
                </div>
            <% } %>
            
            <div class="profile-info">
                <div>
                    <span>Username:</span>
                    <span>${user.userName}</span>
                </div>
                <div>
                    <span>Email:</span>
                    <span>${user.userEmail}</span>
                </div>
                <div>
                    <span>Phone Number:</span>
                    <span>${user.userPhone}</span>
                </div>
            </div>
        </div>
    </div>
</body>
</html>