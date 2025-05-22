<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shop - PuranoThreads</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/shop.css">
    
</head>
<body>
    <nav>
        <div class="logo">
            <img src="${pageContext.request.contextPath}/images/SiteLogo.png" alt="Logo">
        </div>
        
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/shop" class="active">Shop</a>
            <a href="${pageContext.request.contextPath}/aboutus">About Us</a>
            <a href="${pageContext.request.contextPath}/faqs">FAQs</a>
        </div>
        
        <div class="nav-buttons">
            <a href="${pageContext.request.contextPath}/login">
                <button data-tooltip="My Account">
                    <img src="${pageContext.request.contextPath}/images/accounticon.png" alt="Search">
                </button>
            </a>
            <a href="${pageContext.request.contextPath}/shop/favoritespage">
                <button data-tooltip="My Favourites">
                    <img src="${pageContext.request.contextPath}/images/wishlisticon.png" alt="User">
                </button>
            </a>
            <a href="${pageContext.request.contextPath}/shop/cartpage">
                <button data-tooltip="Shopping Cart">
                    <img src="${pageContext.request.contextPath}/images/carticon.png" alt="Cart">
                </button>
            </a>
        </div>
    </nav>

    <c:if test="${not empty sessionScope.message}">
        <div class="message-container">
            ${sessionScope.message}
            <% session.removeAttribute("message"); %>
        </div>
    </c:if>

    <!-- Debug information (hidden by default) -->
    <div class="debug-info">
        <h3>Debug Information</h3>
        <p>Featured Product: ${featuredProduct != null ? featuredProduct.name : 'null'}</p>
        <p>Total Products: ${products != null ? products.size() : '0'}</p>
        <c:if test="${not empty products}">
            <p>First Product: ${products[0].name}</p>
        </c:if>
    </div>

    <div class="shop-container">
        <!-- Left Section -->
        <div class="left-section">
            <!-- Search by Inspos -->
            <div class="search-container">
                <h2>Search by Inspos</h2>
                <div class="search-grid">
                    <button class="search-button">Streetwear</button>
                    <button class="search-button">Old Money</button>
                    <button class="search-button">Lana-core</button>
                    <button class="search-button">Tyler The Creator</button>
                    <button class="search-button">Grunge Fairy</button>
                    <button class="search-button">Cyber</button>
                    <button class="search-button">Rave</button>
                    <button class="search-button">Matrix Minimalist</button>
                    <button class="search-button">Goth</button>
                    <button class="search-button">Skater</button>
                    <button class="search-button">Denimwear</button>
                    <button class="search-button">Business</button>
                </div>
            </div>

            <!-- Featured Product -->
            <div class="featured-product">
                <c:choose>
                    <c:when test="${not empty featuredProduct}">
                        <div class="product-image" style="background-image: url('${pageContext.request.contextPath}/${not empty featuredProduct.imageUrl ? featuredProduct.imageUrl : 'images/ImageIcon.png'}'); background-size: cover; background-position: center;"></div>
                        <div class="product-details">
                            <h3 class="product-title">${featuredProduct.name}</h3>
                            <p class="product-price">Rs. ${featuredProduct.price}</p>
                            <p class="product-description">${featuredProduct.description}</p>
                            <div class="product-buttons">
                                <form action="${pageContext.request.contextPath}/shop/cartpage" method="post">
                                    <input type="hidden" name="productId" value="${featuredProduct.id}">
                                    <input type="hidden" name="productName" value="${featuredProduct.name}">
                                    <input type="hidden" name="productPrice" value="${featuredProduct.price}">
                                    <input type="hidden" name="quantity" value="1">
                                    <button type="submit" class="product-button">Add to Cart</button>
                                </form>
                                <form action="${pageContext.request.contextPath}/shop/favoritespage" method="post">
                                    <input type="hidden" name="productId" value="${featuredProduct.id}">
                                    <input type="hidden" name="productName" value="${featuredProduct.name}">
                                    <input type="hidden" name="productPrice" value="${featuredProduct.price}">
                                    <button type="submit" class="product-button">Favourite it!</button>
                                </form>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="product-image" style="background-image: url('${pageContext.request.contextPath}/images/ImageIcon.png'); background-size: cover; background-position: center;"></div>
                        <div class="product-details">
                            <h3 class="product-title">No Product Selected</h3>
                            <p class="product-price">Rs. 0.00</p>
                            <p class="product-description">Please select a product from the grid to view details.</p>
                            <div class="product-buttons">
                                <button class="product-button" disabled>Add to Cart</button>
                                <button class="product-button" disabled>Favourite it!</button>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <!-- Right Section -->
        <div class="right-section">
            <!-- Header Container -->
            <div class="headerContainer">
                <h1>Shop</h1>
                <div class="toggleButtonGroup">
                    <div class="toggleButton" id="maleToggle" data-gender="Male">
                        <img src="${pageContext.request.contextPath}/images/male.png" alt="Male" />
                    </div>
                    <div class="toggleDivider"></div>
                    <div class="toggleButton" id="femaleToggle" data-gender="Female">
                        <img src="${pageContext.request.contextPath}/images/female.png" alt="Female" />
                    </div>
                </div>
            </div>
    
            <!-- Category Container -->
            <div class="categoryContainer">
                <div class="categoryButton" data-category="Clothing">Clothing</div>
                <div class="categoryButton" data-category="Accessories">Accessories</div>
                <div class="categoryButton" data-category="Footwear">Footwear</div>
            </div>
    
            <!-- Subcategory Scroll Container -->
            <div class="subCategoryScrollContainer">
                <div class="subCategoryItem active" data-subcategory="All">All</div>
                <div class="subCategoryItem" data-subcategory="Shirts">Shirts</div>
                <div class="subCategoryItem" data-subcategory="Pants">Pants</div>
                <div class="subCategoryItem" data-subcategory="Jackets">Jackets</div>
                <div class="subCategoryItem" data-subcategory="T-Shirts">T-Shirts</div>
                <div class="subCategoryItem" data-subcategory="Cardigans">Cardigans</div>
                <div class="subCategoryItem" data-subcategory="Sweaters">Sweaters</div>
                <div class="subCategoryItem" data-subcategory="Hoodies">Hoodies</div>
            </div>
    
            <!-- Product Grid Container -->
            <div class="productGridContainer">
                <c:choose>
                    <c:when test="${not empty products}">
                        <c:forEach var="product" items="${products}">
                            <div class="productItem" data-product-id="${product.id}">
                                <div style="width: 50px; height: 50px; background-image: url('${pageContext.request.contextPath}/${not empty product.imageUrl ? product.imageUrl : 'images/ImageIcon.png'}'); background-size: cover; background-position: center; border-radius: 5px; margin-bottom: 5px;"></div>
                                ${product.name}
                                <div style="font-size: 12px; color: #DA498D; margin: 5px 0;">Rs. ${product.price}</div>
                                <div style="display: flex; gap: 5px; margin-top: 5px;">
                                    <form action="${pageContext.request.contextPath}/shop/cartpage" method="post">
                                        <input type="hidden" name="productId" value="${product.id}">
                                        <input type="hidden" name="productName" value="${product.name}">
                                        <input type="hidden" name="productPrice" value="${product.price}">
                                        <input type="hidden" name="quantity" value="1">
                                        <button type="submit" class="product-button" style="padding: 5px 10px; font-size: 12px;">Add</button>
                                    </form>
                                    <form action="${pageContext.request.contextPath}/shop/favoritespage" method="post">
                                        <input type="hidden" name="productId" value="${product.id}">
                                        <input type="hidden" name="productName" value="${product.name}">
                                        <input type="hidden" name="productPrice" value="${product.price}">
                                        <button type="submit" class="product-button" style="padding: 5px 10px; font-size: 12px;">♥</button>
                                    </form>
                                </div>
                            </div>
                        </c:forEach>
                    </c:when>
                    <c:otherwise>
                        <div style="grid-column: span 4; text-align: center; padding: 20px;">
                            No products found. Please try a different category or filter.
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

<script>
document.addEventListener('DOMContentLoaded', function() {
    // Get current URL parameters to determine active elements
    const urlParams = new URLSearchParams(window.location.search);
    const currentCategory = urlParams.get('name');
    const currentGender = urlParams.get('type');
    const currentSubcategory = urlParams.get('name');
    const currentPath = window.location.pathname;
    const currentProductId = urlParams.get('id');
    
    console.log('Current URL:', window.location.href);
    console.log('Current path:', currentPath);
    console.log('Current category:', currentCategory);
    console.log('Current subcategory:', currentSubcategory);
    console.log('Current gender:', currentGender);
    console.log('Current product ID:', currentProductId);
    
    // Category button selection
    const categoryButtons = document.querySelectorAll('.categoryButton');
    categoryButtons.forEach(btn => {
        // Set active class based on URL
        if (currentCategory && btn.getAttribute('data-category') === currentCategory) {
            categoryButtons.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
        }
        
        btn.addEventListener('click', function() {
            categoryButtons.forEach(b => b.classList.remove('active'));
            this.classList.add('active');
            
            const category = this.getAttribute('data-category');
            window.location.href = '${pageContext.request.contextPath}/shop/category?name=' + category;
        });
    });

    // Toggle button selection (Male/Female)
    const toggleButtons = document.querySelectorAll('.toggleButtonGroup .toggleButton');
    toggleButtons.forEach(btn => {
        // Set active class based on URL
        if (currentGender && btn.getAttribute('data-gender') === currentGender) {
            toggleButtons.forEach(b => b.classList.remove('active'));
            btn.classList.add('active');
        }
        
        btn.addEventListener('click', function() {
            toggleButtons.forEach(b => b.classList.remove('active'));
            this.classList.add('active');
            
            const gender = this.getAttribute('data-gender');
            window.location.href = '${pageContext.request.contextPath}/shop/gender?type=' + gender;
        });
    });

    // Subcategory button selection
    const subCategoryItems = document.querySelectorAll('.subCategoryItem');
    subCategoryItems.forEach(item => {
        // Set active class based on URL
        const subcategory = item.getAttribute('data-subcategory');
        if (currentPath.includes('/shop/subcategory') && currentSubcategory === subcategory) {
            subCategoryItems.forEach(i => i.classList.remove('active'));
            item.classList.add('active');
        } else if (currentPath === '${pageContext.request.contextPath}/shop' && subcategory === 'All') {
            subCategoryItems.forEach(i => i.classList.remove('active'));
            item.classList.add('active');
        }
        
        item.addEventListener('click', function() {
            subCategoryItems.forEach(i => i.classList.remove('active'));
            this.classList.add('active');
            
            const subcategory = this.getAttribute('data-subcategory');
            if (subcategory !== 'All') {
                window.location.href = '${pageContext.request.contextPath}/shop/subcategory?name=' + subcategory;
            } else {
                window.location.href = '${pageContext.request.contextPath}/shop';
            }
        });
    });
    
    // Product item selection
    const productItems = document.querySelectorAll('.productItem');
    
    productItems.forEach(item => {
        // Set active class based on URL
        if (currentProductId && item.getAttribute('data-product-id') === currentProductId) {
            productItems.forEach(i => i.classList.remove('active'));
            item.classList.add('active');
        }
        
        item.addEventListener('click', function(e) {
            // Only handle clicks on the item itself, not on buttons
            if (e.target.tagName.toLowerCase() !== 'button' && 
                e.target.tagName.toLowerCase() !== 'form') {
                productItems.forEach(i => i.classList.remove('active'));
                this.classList.add('active');
                
                const productId = this.getAttribute('data-product-id');
                window.location.href = '${pageContext.request.contextPath}/shop/product?id=' + productId;
            }
        });
    });
    
    // Toggle debug info (for development)
    // Uncomment this to show debug info during development
    // document.querySelector('.debug-info').style.display = 'block';
});
</script>
</body>
</html>