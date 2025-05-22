<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Listing - Purano Threads</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/adminlisting.css">
    
</head>
<body>
    <nav>
        <div class="logo">
            <img src="images/SiteLogo.png" alt="Logo">
        </div>
        
        <div class="nav-links">
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/shop">Shop</a>
            <a href="${pageContext.request.contextPath}/aboutus">About Us</a>
            <a href="${pageContext.request.contextPath}/faqs">FAQs</a>
        </div>
        
        <div class="nav-buttons">
            <a href="${pageContext.request.contextPath}/login">
                <button data-tooltip="My Account" class="active">
                    <img src="images/accounticon.png" alt="Search">
                </button>
            </a>
            <a href="${pageContext.request.contextPath}/shop/favoritespage">
                <button data-tooltip="My Favourites">
                    <img src="images/wishlisticon.png" alt="User">
                </button>
            </a>
            <a href="${pageContext.request.contextPath}/shop/cartpage">
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
            <h1>Listings</h1>
            <div class="dashboard-controls">
                <button class="control-btn active">List</button>
                <div class="vertical-divider"></div>
                <button class="control-btn">Unlist/Update</button>
            </div>
            <div class="line-divider"></div>
            
            <!-- Display success or error messages if any -->
            <c:if test="${not empty successMessage}">
                <div class="message-container" style="background-color: #4CAF50;">
                    ${successMessage}
                </div>
            </c:if>
            <c:if test="${not empty errorMessage}">
                <div class="message-container" style="background-color: #f44336;">
                    ${errorMessage}
                </div>
            </c:if>
            
            <div class="main-listing-content">
                <!-- Add Product Form -->
                <form action="${pageContext.request.contextPath}/adminlisting" method="post" enctype="multipart/form-data" id="addProductForm">
                    <input type="hidden" name="action" value="add">
                    
                    <div class="outer-container">
                        <!-- Container 1 - Product Details -->
                        <div class="container1">
                            <h2>LIST ITEMS</h2>
                            <input type="text" name="productName" placeholder="Enter Item Name" required>
                            <input type="text" name="productPrice" placeholder="Enter Item Price (in Rs.)" 
                                   oninput="this.value = this.value.replace(/[^0-9]/g, '')" required>
                            
                            <select name="productCategory" required>
                                <option value="" disabled selected>Choose Item Category</option>
                                <option value="Clothing">Clothing</option>
                                <option value="Accessories">Accessories</option>
                                <option value="Footwear">Footwear</option>
                            </select>
                            
                            <select name="subCategory" required>
                                <option value="" disabled selected>Choose Sub-Category</option>
                                <optgroup label="Clothing">
                                    <option value="T-Shirts">T-Shirts</option>
                                    <option value="Jeans">Jeans</option>
                                    <option value="Jackets">Jackets</option>
                                    <option value="Shirts">Shirts</option>
                                    <option value="Dresses">Dresses</option>
                                </optgroup>
                                <optgroup label="Accessories">
                                    <option value="Watches">Watches</option>
                                    <option value="Jewelry">Jewelry</option>
                                    <option value="Bags">Bags</option>
                                    <option value="Hats">Hats</option>
                                </optgroup>
                                <optgroup label="Footwear">
                                    <option value="Sneakers">Sneakers</option>
                                    <option value="Boots">Boots</option>
                                    <option value="Sandals">Sandals</option>
                                    <option value="Heels">Heels</option>
                                </optgroup>
                            </select>
                        </div>
                    
                        <!-- Container 2 - Image Upload -->
                        <div class="container2">
                            <div class="drop-image-container" id="dropArea">
                                <img src="images/imageIcon.png" alt="Image Icon" id="previewImage">
                                <p>1:1 ratio</p>
                            </div>
                            <p>Drop Item's<br>Picture Above</p>
                            <p style="font-size: 14px;">━━━━ or ━━━━</p>
                            <button type="button" class="upload-btn" onclick="document.getElementById('fileInput').click()">UPLOAD ↑</button>
                            <input type="file" name="productImage" id="fileInput" style="display: none;" accept="image/*" onchange="handleFileUpload(event)" required>
                        </div>
                    
                        <!-- Container 3 - Description and Condition -->
                        <div class="container3">
                            <textarea name="productDescription" placeholder="Enter Item Description Here.." required></textarea>
                            <div class="condition-container">
                                <p style="font-size: 14px; font-weight: bold; font-family: 'Anek Malayalam', sans-serif;">CONDITION :</p>
                                <div class="condition-buttons">
                                    <button type="button" class="condition-btn pastel-green" data-condition="New" onclick="selectCondition(this)"></button>
                                    <button type="button" class="condition-btn pastel-orange" data-condition="Good" onclick="selectCondition(this)"></button>
                                    <button type="button" class="condition-btn pastel-yellow" data-condition="Fair" onclick="selectCondition(this)"></button>
                                    <button type="button" class="condition-btn pastel-blue" data-condition="Used" onclick="selectCondition(this)"></button>
                                </div>
                            </div>
                            <input type="hidden" name="productCondition" id="productCondition" value="">
                            <button type="submit" class="curvy-button"><span>CONFIRM</span></button>
                        </div>
                    </div>
                </form>
                
                <!-- Product List -->
                <div class="product-list-container">
                    <h2>Current Products</h2>
                    <div class="product-list">
                        <c:forEach var="product" items="${products}">
                            <div class="product-card">
                                <img src="${not empty product.imagePath ? product.imagePath : 'images/imageIcon.png'}" alt="${product.productName}" class="product-image">
                                <div class="product-info">
                                    <div class="product-name">${product.productName}</div>
                                    <div class="product-category">${product.productCategory} - ${product.subCategory}</div>
                                    <div class="product-price">Rs. ${product.productPrice}</div>
                                </div>
                            </div>
                        </c:forEach>
                        
                        <c:if test="${empty products}">
                            <div style="grid-column: 1 / -1; text-align: center; padding: 20px;">
                                No products found. Add your first product above!
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        // Handle condition button selection
        function selectCondition(button) {
            // Remove selected class from all buttons
            document.querySelectorAll('.condition-btn').forEach(btn => {
                btn.classList.remove('selected');
            });
            
            // Add selected class to clicked button
            button.classList.add('selected');
            
            // Set the condition value in the hidden input
            document.getElementById('productCondition').value = button.getAttribute('data-condition');
        }
        
        // Handle file upload and preview
        function handleFileUpload(event) {
            const file = event.target.files[0];
            if (file && file.type.startsWith('image/')) {
                const reader = new FileReader();
                const previewImage = document.getElementById('previewImage');
                
                reader.onload = function(e) {
                    previewImage.src = e.target.result;
                    previewImage.classList.add('preview');
                };
                
                reader.readAsDataURL(file);
            }
        }
        
        // Drag & Drop for image upload
        const dropArea = document.getElementById('dropArea');
        const fileInput = document.getElementById('fileInput');
        
        dropArea.addEventListener('dragover', (e) => {
            e.preventDefault();
            dropArea.style.borderColor = '#ea5acd';
            dropArea.style.background = 'rgba(234,90,205,0.08)';
        });
        
        dropArea.addEventListener('dragleave', () => {
            dropArea.style.borderColor = '#ffffff';
            dropArea.style.background = '';
        });
        
        dropArea.addEventListener('drop', (e) => {
            e.preventDefault();
            dropArea.style.borderColor = '#ffffff';
            dropArea.style.background = '';
            
            const files = e.dataTransfer.files;
            if (files && files[0] && files[0].type.startsWith('image/')) {
                fileInput.files = files;
                
                const reader = new FileReader();
                const previewImage = document.getElementById('previewImage');
                
                reader.onload = function(e) {
                    previewImage.src = e.target.result;
                    previewImage.classList.add('preview');
                };
                
                reader.readAsDataURL(files[0]);
            }
        });
        
        // Form validation before submit
        document.getElementById('addProductForm').addEventListener('submit', function(e) {
            const conditionValue = document.getElementById('productCondition').value;
            
            if (!conditionValue) {
                e.preventDefault();
                alert('Please select a product condition');
                return false;
            }
            
            return true;
        });
        
        // Category dropdown change handler
        document.querySelector('select[name="productCategory"]').addEventListener('change', function() {
            const category = this.value;
            const subCategorySelect = document.querySelector('select[name="subCategory"]');
            
            // Reset sub-category dropdown
            subCategorySelect.innerHTML = '<option value="" disabled selected>Choose Sub-Category</option>';
            
            // Add appropriate options based on selected category
            if (category === 'Clothing') {
                const clothingOptions = ['T-Shirts', 'Jeans', 'Jackets', 'Shirts', 'Dresses'];
                const optgroup = document.createElement('optgroup');
                optgroup.label = 'Clothing';
                
                clothingOptions.forEach(option => {
                    const optionElement = document.createElement('option');
                    optionElement.value = option;
                    optionElement.textContent = option;
                    optgroup.appendChild(optionElement);
                });
                
                subCategorySelect.appendChild(optgroup);
            } else if (category === 'Accessories') {
                const accessoriesOptions = ['Watches', 'Jewelry', 'Bags', 'Hats'];
                const optgroup = document.createElement('optgroup');
                optgroup.label = 'Accessories';
                
                accessoriesOptions.forEach(option => {
                    const optionElement = document.createElement('option');
                    optionElement.value = option;
                    optionElement.textContent = option;
                    optgroup.appendChild(optionElement);
                });
                
                subCategorySelect.appendChild(optgroup);
            } else if (category === 'Footwear') {
                const footwearOptions = ['Sneakers', 'Boots', 'Sandals', 'Heels'];
                const optgroup = document.createElement('optgroup');
                optgroup.label = 'Footwear';
                
                footwearOptions.forEach(option => {
                    const optionElement = document.createElement('option');
                    optionElement.value = option;
                    optionElement.textContent = option;
                    optgroup.appendChild(optionElement);
                });
                
                subCategorySelect.appendChild(optgroup);
            }
        });
    </script>
</body>
</html>
