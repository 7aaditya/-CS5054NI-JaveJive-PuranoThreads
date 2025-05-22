<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Curved Navbar</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/faqs.css">
    
</head>
<body>
    <nav>
        <div class="logo">
            <img src="images/SiteLogo.png" alt="Logo">
        </div>
        
        <div class="nav-links">
             <a href="${pageContext.request.contextPath}/Home">Home</a>
		    <a href="${pageContext.request.contextPath}/ShopPage">Shop</a>
		    <a href="${pageContext.request.contextPath}/AboutUs">About Us</a>
		    <a href="${pageContext.request.contextPath}/FAQsPage">FAQs</a>
        </div>
        
        <div class="nav-buttons">
            <a href="UserLoginPage.html">
                <button data-tooltip="My Account">
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

    <div class="faqs-container">
      <h1>FAQs</h1>
      <hr>
      <div class="faq-item">
          <h2>What is the return policy?</h2>
          <p>Our return policy allows you to return items within 30 days of purchase. Items must be in their original condition and packaging.</p>
      </div>
      <div class="faq-item">
          <h2>How can I track my order?</h2>
          <p>You can track your order by logging into your account and visiting the "Order History" section. A tracking link will be provided for your shipment.</p>
      </div>
      <div class="faq-item">
          <h2>Do you offer international shipping?</h2>
          <p>Yes, we offer international shipping to select countries. Shipping charges and delivery times may vary based on your location.</p>
      </div>
      <div class="faq-item">
          <h2>What payment methods are accepted?</h2>
          <p>We accept credit cards, debit cards, PayPal, and other secure payment methods. All transactions are encrypted for your safety.</p>
      </div>
      <div class="faq-item">
          <h2>How can I contact customer support?</h2>
          <p>You can contact our customer support via contact us panel which is located in Abous Us page.</p>
      </div>
  </div>

</body>
</html>