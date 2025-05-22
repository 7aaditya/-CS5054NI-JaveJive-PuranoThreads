<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.puranothread.model.productmodel" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel - Unlist/Update Items</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminunlist.css">
    <style>
       
    </style>
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
            <a href="${pageContext.request.contextPath}/admindashboard">Dashboard</a>
            <a href="${pageContext.request.contextPath}/adminlisting" class="active">Listing</a>
            <a href="${pageContext.request.contextPath}/adminorders">Orders</a>
            <a href="${pageContext.request.contextPath}/adminmessages">Messages</a>
        </div>
        <div class="listing-container">
            <h1>Unlist/Update Items</h1>
            <div class="dashboard-controls">
                <a href="${pageContext.request.contextPath}/adminlisting">
                    <button class="control-btn">List</button>
                </a>
                <div class="vertical-divider"></div>
                <a href="${pageContext.request.contextPath}/adminunlist">
                    <button class="control-btn active">Unlist/Update</button>
                </a>
            </div>
            <div class="line-divider"></div>
            <div class="main-listing-content">
                <div class="unlist-panel">
                    <h2 style="font-family: 'Lobster Two', cursive;">Unlist Items</h2>
                    <div class="form-group">
                        <select id="unlistCategory" class="category-dropdown">
                            <option value="">Select Category</option>
                            <option value="Watches">Watches</option>
                            <option value="Jewelry">Jewelry</option>
                            <option value="Jackets">Jackets</option>
                            <option value="Upperwear">Upperwear</option>
                            <option value="Lowerwear">Lowerwear</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <input type="text" id="unlistSearchTerm" placeholder="Search item..." class="search-bar">
                    </div>
                    <button id="unlistSearchBtn" class="curvy-button" style="margin-top: 15px;"><span>SEARCH</span></button>
                    
                    <div id="unlistSearchResults" class="search-results"></div>
                    
                    <button id="confirmUnlistBtn" class="curvy-button" style="margin-top: 15px; display: none;"><span>CONFIRM UNLIST</span></button>
                </div>

                <div class="update-panel">
                    <h2 style="font-family: 'Lobster Two', cursive;">Update Item</h2>
                    <div class="form-group">
                        <select id="updateCategory" class="category-dropdown">
                            <option value="">Select Category</option>
                            <option value="Watches">Watches</option>
                            <option value="Jewelry">Jewelry</option>
                            <option value="Jackets">Jackets</option>
                            <option value="Upperwear">Upperwear</option>
                            <option value="Lowerwear">Lowerwear</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <input type="text" id="updateSearchTerm" placeholder="Search item..." class="search-bar">
                    </div>
                    <button id="updateSearchBtn" class="curvy-button" style="margin-top: 15px;"><span>SEARCH</span></button>
                    
                    <div id="updateSearchResults" class="search-results"></div>
                    
                    <div id="updateForm" style="display: none; margin-top: 15px;">
                        <div class="form-group">
                            <input type="text" id="newName" placeholder="Enter New Name" class="form-input">
                        </div>
                        <div class="form-group">
                            <input type="text" id="newPrice" placeholder="New Price (Rs.)" 
                                oninput="this.value = this.value.replace(/[^0-9]/g, '')"
                                class="form-input">
                        </div>
                        <button id="confirmUpdateBtn" class="curvy-button" style="background: #8a3b8f;"><span>UPDATE ITEM</span></button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    <div id="notification" class="notification"></div>
    
    <script>
        // Global variables to store selected products
        let selectedUnlistProduct = null;
        let selectedUpdateProduct = null;
        
        // Function to show notification
        function showNotification(message, isSuccess) {
            const notification = document.getElementById('notification');
            notification.textContent = message;
            notification.className = 'notification ' + (isSuccess ? 'success' : 'error');
            notification.classList.add('show');
            
            setTimeout(() => {
                notification.classList.remove('show');
            }, 3000);
        }
        
        // Function to search products
        function searchProducts(categoryElement, searchTermElement, resultsElement, isUnlist) {
            const category = document.getElementById(categoryElement).value;
            const searchTerm = document.getElementById(searchTermElement).value;
            const resultsContainer = document.getElementById(resultsElement);
            
            // Clear previous results
            resultsContainer.innerHTML = '';
            
            // Hide confirm buttons
            if (isUnlist) {
                document.getElementById('confirmUnlistBtn').style.display = 'none';
                selectedUnlistProduct = null;
            } else {
                document.getElementById('updateForm').style.display = 'none';
                selectedUpdateProduct = null;
            }
            
            // Make AJAX request to search products
            fetch('${pageContext.request.contextPath}/adminunlist?action=search&category=' + encodeURIComponent(category) + '&searchTerm=' + encodeURIComponent(searchTerm), {
                method: 'GET',
                headers: {
                    'X-Requested-With': 'XMLHttpRequest'
                }
            })
            .then(response => response.json())
            .then(products => {
                if (products.length === 0) {
                    resultsContainer.innerHTML = '<div class="product-item">No products found</div>';
                    return;
                }
                
                products.forEach(product => {
                    const productItem = document.createElement('div');
                    productItem.className = 'product-item';
                    productItem.innerHTML = `
                        <strong>${product.name}</strong> - Rs. ${product.price} (${product.category})
                    `;
                    productItem.dataset.id = product.id;
                    productItem.dataset.name = product.name;
                    productItem.dataset.price = product.price;
                    
                    productItem.addEventListener('click', function() {
                        // Remove selected class from all items
                        resultsContainer.querySelectorAll('.product-item').forEach(item => {
                            item.classList.remove('selected');
                        });
                        
                        // Add selected class to clicked item
                        this.classList.add('selected');
                        
                        if (isUnlist) {
                            selectedUnlistProduct = {
                                id: this.dataset.id,
                                name: this.dataset.name
                            };
                            document.getElementById('confirmUnlistBtn').style.display = 'block';
                        } else {
                            selectedUpdateProduct = {
                                id: this.dataset.id,
                                name: this.dataset.name,
                                price: this.dataset.price
                            };
                            document.getElementById('updateForm').style.display = 'block';
                            document.getElementById('newName').value = this.dataset.name;
                            document.getElementById('newPrice').value = this.dataset.price;
                        }
                    });
                    
                    resultsContainer.appendChild(productItem);
                });
            })
            .catch(error => {
                console.error('Error searching products:', error);
                resultsContainer.innerHTML = '<div class="product-item">Error searching products</div>';
                showNotification('Error searching products: ' + error.message, false);
            });
        }
        
        // Event listeners for search buttons
        document.getElementById('unlistSearchBtn').addEventListener('click', function() {
            searchProducts('unlistCategory', 'unlistSearchTerm', 'unlistSearchResults', true);
        });
        
        document.getElementById('updateSearchBtn').addEventListener('click', function() {
            searchProducts('updateCategory', 'updateSearchTerm', 'updateSearchResults', false);
        });
        
        // Event listener for confirm unlist button
        document.getElementById('confirmUnlistBtn').addEventListener('click', function() {
            if (!selectedUnlistProduct) {
                showNotification('No product selected for unlisting', false);
                return;
            }
            
            // Make AJAX request to delete product
            fetch('${pageContext.request.contextPath}/adminunlist', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'X-Requested-With': 'XMLHttpRequest'
                },
                body: 'action=delete&productId=' + selectedUnlistProduct.id
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    showNotification(data.message, true);
                    // Clear search results and reset selection
                    document.getElementById('unlistSearchResults').innerHTML = '';
                    document.getElementById('confirmUnlistBtn').style.display = 'none';
                    selectedUnlistProduct = null;
                } else {
                    showNotification(data.message, false);
                }
            })
            .catch(error => {
                console.error('Error unlisting product:', error);
                showNotification('Error unlisting product', false);
            });
        });
        
        // Event listener for confirm update button
        document.getElementById('confirmUpdateBtn').addEventListener('click', function() {
            if (!selectedUpdateProduct) {
                showNotification('No product selected for update', false);
                return;
            }
            
            const newName = document.getElementById('newName').value.trim();
            const newPrice = document.getElementById('newPrice').value.trim();
            
            if (!newName) {
                showNotification('Please enter a new name', false);
                return;
            }
            
            if (!newPrice) {
                showNotification('Please enter a new price', false);
                return;
            }
            
            // Make AJAX request to update product
            fetch('${pageContext.request.contextPath}/adminunlist', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'X-Requested-With': 'XMLHttpRequest'
                },
                body: 'action=update&productId=' + selectedUpdateProduct.id + 
                      '&newName=' + encodeURIComponent(newName) + 
                      '&newPrice=' + newPrice
            })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    showNotification(data.message, true);
                    // Clear search results and reset form
                    document.getElementById('updateSearchResults').innerHTML = '';
                    document.getElementById('updateForm').style.display = 'none';
                    selectedUpdateProduct = null;
                } else {
                    showNotification(data.message, false);
                }
            })
            .catch(error => {
                console.error('Error updating product:', error);
                showNotification('Error updating product', false);
            });
        });
        
        // Enter key event listeners for search inputs
        document.getElementById('unlistSearchTerm').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                document.getElementById('unlistSearchBtn').click();
            }
        });
        
        document.getElementById('updateSearchTerm').addEventListener('keypress', function(e) {
            if (e.key === 'Enter') {
                document.getElementById('updateSearchBtn').click();
            }
        });
        
        // Load any existing search results from session
        document.addEventListener('DOMContentLoaded', function() {
            <% 
            List<productmodel> searchResults = (List<productmodel>) session.getAttribute("searchResults");
            if (searchResults != null && !searchResults.isEmpty()) {
            %>
                // Display search results if they exist in session
                const resultsContainer = document.getElementById('unlistSearchResults');
                
                <% for (productmodel product : searchResults) { %>
                    const productItem = document.createElement('div');
                    productItem.className = 'product-item';
                    productItem.innerHTML = `
                        <strong><%= product.getProductName() %></strong> - Rs. <%= product.getProductPrice() %> (<%= product.getProductCategory() %>)
                    `;
                    productItem.dataset.id = '<%= product.getProductID() %>';
                    productItem.dataset.name = '<%= product.getProductName() %>';
                    productItem.dataset.price = '<%= product.getProductPrice() %>';
                    
                    productItem.addEventListener('click', function() {
                        // Remove selected class from all items
                        resultsContainer.querySelectorAll('.product-item').forEach(item => {
                            item.classList.remove('selected');
                        });
                        
                        // Add selected class to clicked item
                        this.classList.add('selected');
                        
                        selectedUnlistProduct = {
                            id: this.dataset.id,
                            name: this.dataset.name
                        };
                        document.getElementById('confirmUnlistBtn').style.display = 'block';
                    });
                    
                    resultsContainer.appendChild(productItem);
                <% } %>
            <% } %>
        });
    </script>
</body>
</html>