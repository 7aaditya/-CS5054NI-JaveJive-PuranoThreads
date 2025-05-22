<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Curved Navbar</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/userlogin.css">
    
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

    <div class="login-container">
        <h1>User Login</h1>
        <p>Sign in or create an account to enjoy a personalized shopping <br> experience, track your orders, and easily manage your purchases.</p>
        <a href="LoginPage.html" class="button"><span>LOG IN</span></a>
        <a href="SignUpPage.html" class="button"><span>SIGN UP</span></a>
        <a href="HomePage.html" class="stay-logged-out">Stay Logged Out</a>
    </div>

</body>
</html>