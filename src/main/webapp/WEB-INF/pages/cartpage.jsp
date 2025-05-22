<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Shopping Cart - PuranoThreads</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/cartpage.css">
    
    
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
                <button data-tooltip="My Account">
                    <img src="images/accounticon.png" alt="Search">
                </button>
            </a>
            <a href="${pageContext.request.contextPath}/favoritespage">
                <button data-tooltip="My Favourites">
                    <img src="images/wishlisticon.png" alt="User">
                </button>
            </a>
            <a href="${pageContext.request.contextPath}/cartpage">
                <button data-tooltip="Shopping Cart" class="active">
                    <img src="images/carticon.png" alt="Cart">
                </button>
            </a>
        </div>
    </nav>

    <!-- Display success/error messages if any -->
    <c:if test="${not empty sessionScope.message}">
        <div class="${sessionScope.message.contains('Order confirmed') ? 'success-message' : 'message-container'}">
            ${sessionScope.message}
            <c:if test="${sessionScope.message.contains('Order confirmed')}">
                <p style="margin-top: 15px;">
                    <a href="${pageContext.request.contextPath}/shop" class="continue-shopping-btn">Continue Shopping</a>
                </p>
            </c:if>
            <% session.removeAttribute("message"); %>
        </div>
    </c:if>

    <!-- Debug information - hidden by default -->
    <div style="display: none;">
        <h3>Debug Info:</h3>
        <p>Cart is null: ${sessionScope.cart == null}</p>
        <p>Cart is empty: ${empty sessionScope.cart}</p>
        <p>Cart size: ${sessionScope.cart.size()}</p>
    </div>

    <c:choose>
        <c:when test="${empty sessionScope.cart || sessionScope.cart.size() == 0}">
            <!-- Display empty cart message -->
            <div class="empty-cart-message">
                <h2>Your Shopping Cart is Empty</h2>
                <p>Looks like you haven't added any items to your cart yet.</p>
                <a href="${pageContext.request.contextPath}/shop" class="continue-shopping-btn">Continue Shopping</a>
            </div>
        </c:when>
        <c:otherwise>
            <!-- Display cart contents -->
            <div class="cart-container">
                <h1 class="cart-title">Shopping Cart</h1>
                <hr>
                <div class="cart-content">
                    <div class="cart-left">
                        <div class="data-header">
                            <span>Product Name</span>
                            <span>Price</span>
                            <span>Discount</span>
                            <span>Subtotal</span>
                        </div>
                        <hr>
                        <div class="scrollable-content">
                            <c:forEach var="item" items="${sessionScope.cart}">
                                <div class="cart-item">
                                    <div class="product-column">
                                        <form action="${pageContext.request.contextPath}/cartpage" method="post">
                                            <input type="hidden" name="id" value="${item.id}">
                                            <button type="submit" class="remove-btn">x</button>
                                        </form>
                                        <div class="image-box">
                                            <img src="${not empty item.imageUrl ? item.imageUrl : 'images/ImageIcon.png'}" alt="Product Image">
                                        </div>
                                        <span class="product-name">${item.name}</span>
                                    </div>
                                    <span class="price">Rs. ${item.price}</span>
                                    <span class="discount">
                                        <c:choose>
                                            <c:when test="${item.price > 3000}">-15%</c:when>
                                            <c:when test="${item.price > 2000}">-12%</c:when>
                                            <c:when test="${item.price > 1000}">-10%</c:when>
                                            <c:otherwise>x</c:otherwise>
                                        </c:choose>
                                    </span>
                                    <span class="subtotal">
                                        <c:choose>
                                            <c:when test="${item.price > 3000}">Rs. ${item.price * 0.85}</c:when>
                                            <c:when test="${item.price > 2000}">Rs. ${item.price * 0.88}</c:when>
                                            <c:when test="${item.price > 1000}">Rs. ${item.price * 0.90}</c:when>
                                            <c:otherwise>Rs. ${item.price}</c:otherwise>
                                        </c:choose>
                                    </span>
                                </div>
                            </c:forEach>
                        </div>
                    </div>

                    <div class="cart-right">
                        <div class="summary-container">
                            <h2>Order Summary</h2>
                            <hr>
                            <!-- Calculate subtotal -->
                            <c:set var="subtotal" value="0" />
                            <c:forEach var="item" items="${sessionScope.cart}">
                                <c:set var="subtotal" value="${subtotal + item.price}" />
                            </c:forEach>
                            
                            <span><b>Subtotal</b>: Rs. ${subtotal}</span>
                            <form class="delivery-form">
                                <input type="text" id="delivery-location" placeholder="Enter Delivery Location" required>
                            </form>
                            <span><b>Delivery Charge</b>: Rs. 100</span>
                            <hr class="delivery-charge-divider">
                            
                            <!-- Calculate discount -->
                            <c:set var="discount" value="0" />
                            <c:forEach var="item" items="${sessionScope.cart}">
                                <c:choose>
                                    <c:when test="${item.price > 3000}">
                                        <c:set var="discount" value="${discount + (item.price * 0.15)}" />
                                    </c:when>
                                    <c:when test="${item.price > 2000}">
                                        <c:set var="discount" value="${discount + (item.price * 0.12)}" />
                                    </c:when>
                                    <c:when test="${item.price > 1000}">
                                        <c:set var="discount" value="${discount + (item.price * 0.10)}" />
                                    </c:when>
                                </c:choose>
                            </c:forEach>
                            
                            <span class="total">Total: Rs. ${subtotal - discount + 100}</span>
                            <div class="confirmation-tick">✔</div>
                        </div>
                        <div class="payment-container">
                            <h2>Choose Payment Method</h2>
                            <hr>
                            <form id="payment-form" action="${pageContext.request.contextPath}/cartpage" method="post">
                                <input type="hidden" name="action" value="processPayment">
                                <input type="hidden" name="paymentMethod" id="payment-method" value="">
                                <input type="hidden" name="deliveryLocation" id="delivery-location-hidden" value="">
                                
                                <div class="payment-options">
                                    <button type="button" data-method="esewa" class="payment-option">
                                        <div class="image-box">
                                            <img src="images/EsewaLogo.png" alt="Esewa">
                                        </div>
                                    </button>
                                    <button type="button" data-method="khalti" class="payment-option">
                                        <div class="image-box">
                                            <img src="images/KhaltiLogo.png" alt="Khalti">
                                        </div>
                                    </button>
                                    <button type="button" data-method="cod" class="payment-option">
                                        <div class="image-box">
                                            <img src="images/CODLogo.png" alt="Cash on Delivery">
                                        </div>
                                    </button>
                                </div>
                                <button type="button" class="proceed-btn" id="proceed-button" disabled>Proceed</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

    <!-- Payment Processing Modal -->
    <div id="payment-modal" class="modal" style="display: none;">
        <div class="modal-content">
            <span class="close">&times;</span>
            <h2>Processing Payment</h2>
            <div class="loader"></div>
            <p id="payment-status">Please wait while we process your payment...</p>
        </div>
    </div>

    <script>
        // Debug information - will be logged to the console
        console.log("Cart is null:", ${sessionScope.cart == null});
        console.log("Cart is empty:", ${empty sessionScope.cart});
        console.log("Cart size:", ${sessionScope.cart.size()});
        
        // Payment method selection
        const paymentButtons = document.querySelectorAll('.payment-option');
        const paymentMethodInput = document.getElementById('payment-method');
        const proceedButton = document.getElementById('proceed-button');
        const deliveryLocationInput = document.getElementById('delivery-location');
        const deliveryLocationHidden = document.getElementById('delivery-location-hidden');
        const paymentForm = document.getElementById('payment-form');
        const paymentModal = document.getElementById('payment-modal');
        const paymentStatus = document.getElementById('payment-status');
        const closeModalBtn = document.querySelector('.close');

        // Enable/disable proceed button based on form completion
        function checkFormCompletion() {
            if (paymentMethodInput.value && deliveryLocationInput.value) {
                proceedButton.disabled = false;
            } else {
                proceedButton.disabled = true;
            }
        }

        // Initialize payment buttons if they exist
        if (paymentButtons.length > 0) {
            paymentButtons.forEach(button => {
                button.addEventListener('click', () => {
                    // Check if the clicked button is already selected
                    if (button.classList.contains('selected')) {
                        button.classList.remove('selected');
                        paymentMethodInput.value = '';
                    } else {
                        // Remove the 'selected' class from all buttons
                        paymentButtons.forEach(btn => btn.classList.remove('selected'));

                        // Add the 'selected' class to the clicked button
                        button.classList.add('selected');
                        
                        // Set the payment method value
                        paymentMethodInput.value = button.getAttribute('data-method');
                    }
                    
                    checkFormCompletion();
                });
            });
        }
        
        // Initialize delivery location input if it exists
        if (deliveryLocationInput) {
            deliveryLocationInput.addEventListener('input', () => {
                deliveryLocationHidden.value = deliveryLocationInput.value;
                checkFormCompletion();
            });
        }
        
        // Initialize proceed button if it exists
        if (proceedButton) {
            proceedButton.addEventListener('click', () => {
                if (!paymentMethodInput.value) {
                    alert('Please select a payment method.');
                    return;
                }
                
                if (!deliveryLocationInput.value) {
                    alert('Please enter a delivery location.');
                    return;
                }
                
                // Show payment processing modal
                paymentModal.style.display = 'block';
                
                // Simulate payment processing (for demo purposes)
                if (paymentMethodInput.value === 'cod') {
                    // Cash on delivery - process immediately
                    setTimeout(() => {
                        paymentStatus.textContent = 'Order confirmed! Redirecting...';
                        setTimeout(() => {
                            paymentForm.submit();
                        }, 1500);
                    }, 2000);
                } else {
                    // Online payment - simulate gateway
                    setTimeout(() => {
                        paymentStatus.textContent = 'Connecting to payment gateway...';
                        
                        setTimeout(() => {
                            paymentStatus.textContent = 'Payment successful! Confirming order...';
                            
                            setTimeout(() => {
                                paymentStatus.textContent = 'Order confirmed! Redirecting...';
                                setTimeout(() => {
                                    paymentForm.submit();
                                }, 1500);
                            }, 2000);
                        }, 2000);
                    }, 1500);
                }
            });
        }
        
        // Initialize close modal button if it exists
        if (closeModalBtn) {
            closeModalBtn.addEventListener('click', () => {
                paymentModal.style.display = 'none';
            });
            
            // Close modal when clicking outside of it
            window.addEventListener('click', (event) => {
                if (event.target === paymentModal) {
                    paymentModal.style.display = 'none';
                }
            });
        }
    </script>
</body>
</html>