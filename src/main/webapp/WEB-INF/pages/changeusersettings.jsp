<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Change User Settings</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/changeusersettings.css">
</head>
<body>
    <nav>
        <div class="logo">
            <img src="images/SiteLogo.png" alt="Logo">
        </div>
        
        <div class="nav-links">
            <a href="<%= request.getContextPath() %>/home">Home</a>
			<a href="<%= request.getContextPath() %>/shop">Shop</a>
			<a href="<%= request.getContextPath() %>/aboutus">About Us</a>
			<a href="<%= request.getContextPath() %>/faqs">FAQs</a>
            
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
         <a href="${pageContext.request.contextPath}/profile">My Profile</a>
        <a href="${pageContext.request.contextPath}/changeusersettings class="active">Change User Settings</a>
        <a href="${pageContext.request.contextPath}/logout">Log Out</a>
       
    </div>
    
      <div class="content-container">
          <h1>Change User Settings</h1>
          
          <!-- Display success or error messages if any -->
          <% if(request.getAttribute("successMessage") != null) { %>
              <div class="success-message">
                  <%= request.getAttribute("successMessage") %>
              </div>
          <% } %>
          
          <% if(request.getAttribute("errorMessage") != null) { %>
              <div class="error-message">
                  <%= request.getAttribute("errorMessage") %>
              </div>
          <% } %>
          
          <form action="<%= request.getContextPath() %>/changeusersettings" method="post">
              <div class="form-group">
                  <label for="username">Username</label>
                  <input type="text" id="username" name="username" 
                         value="${user.userName}" placeholder="Enter your username">
              </div>
              <div class="form-group">
                  <label for="email">Email</label>
                  <input type="email" id="email" name="email" 
                         value="${user.userEmail}" placeholder="Enter your email">
              </div>
            
              <div class="form-group">
                  <label for="phone">Phone Number</label>
                  <input type="text" id="phone" name="phone" 
                         value="${user.userPhone}" placeholder="Enter your phone number">
              </div>
              
              <button type="submit" class="confirm-button">Save Changes</button>
          </form>
      </div>
  </div>

</body>
</html>