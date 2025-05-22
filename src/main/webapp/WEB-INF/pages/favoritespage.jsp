<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Curved Navbar</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/favorites.css">
  
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
                <button data-tooltip="My Account">
                    <img src="images/accounticon.png" alt="Search">
                </button>
            </a>
            <a href="MyFavouritesPage.html">
                <button data-tooltip="My Favourites" class="active">
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

    <div class="favourites-container">
      <h1>My Favourites</h1>
      <hr>
      <div class="data-header">
          <span>Product Name</span>
          <span>Price</span>
          <span>Stock Status</span>
          <span>X</span>
      </div>
      <div class="favourites-item">
          <div class="product-column">
              <button class="remove-btn">x</button>
              <div class="image-box" style="background-image: url('images/G121Pants.png'); background-size: cover; background-position: center;"></div>
              <span class="product-name">G121 Side Pocket Washed Baggy Jeans</span>
          </div>
          <span class="price">Rs. 2,499.00</span>
          <span class="stock-status in-stock">In Stock</span>
          <button class="add-to-cart">
            <span>Add to Cart</span>
        </button>
      </div>
      <div class="favourites-item">
          <div class="product-column">
              <button class="remove-btn">x</button>
              <div class="image-box" style="background-image: url('images/ShiningHighWaistStraightTrack.png'); background-size: cover; background-position: center;"></div>
              <span class="product-name">Shining High Waist Straight Track "Galaxy Black"</span>
          </div>
          <span class="price">Rs. 3,499.00</span>
          <span class="stock-status sold-out">Sold Out</span>
          <button class="add-to-cart">
            <span>Add to Cart</span>
        </button>
      </div>
      <div class="favourites-item">
        <div class="product-column">
            <button class="remove-btn">x</button>
            <div class="image-box" style="background-image: url('images/AsicsGelQuantum.png'); background-size: cover; background-position: center;"></div>
            <span class="product-name">Asics Gel Quantum Kinetic "Shamrock Green"</span>
        </div>
        <span class="price">Rs. 5,799.00</span>
        <span class="stock-status in-stock">In Stock</span>
        <button class="add-to-cart">
          <span>Add to Cart</span>
      </button>
    </div>
      <div class="favourites-item">
          <div class="product-column">
              <button class="remove-btn">x</button>
              <div class="image-box" style="background-image: url('images/DoubleBreadedWoolenFurLongCoat'); background-size: cover; background-position: center;"></div>
              <span class="product-name">Double Breaded Woolen Fur Long Over Coat "Cream"</span>
          </div>
          <span class="price">Rs. 8,000.00</span>
          <span class="stock-status sold-out">Sold Out</span>
          <button class="add-to-cart">
            <span>Add to Cart</span>
        </button>
      </div>
  </div>

</body>
</html>