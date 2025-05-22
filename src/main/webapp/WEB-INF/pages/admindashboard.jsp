<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard - Purano Threads</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/admindashboard.css">
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
           <a href="${pageContext.request.contextPath}/admindashboard" class="active">Dashboard</a> <!-- Link to Dashboard -->
           <a href="${pageContext.request.contextPath}/adminlisting">Listing</a> <!-- Link to Listing -->
           <a href="${pageContext.request.contextPath}/adminorders">Orders</a> <!-- Link to Orders -->
           <a href="${pageContext.request.contextPath}/adminmessages">Messages</a> <!-- Link to Messages -->
        </div>
      
      <div class="main-dashboard">
        <h1>Dashboard</h1>
        <div class="dashboard-content">
            <!-- Recent Sales Graph Section -->
            <div class="dashboard-chart">
                <h2>RAS [RECENT AVERAGE SALES]</h2>
                <canvas id="salesChart"></canvas>
            </div>
        
            <!-- Recent Orders Section -->
            <div class="dashboard-card">
                <h2>RECENTLY ORDERED</h2>
                <div class="recent-orders-container">
                    <% 
                    // Use the fully qualified class name to avoid import issues
                    List<com.puranothread.model.order> recentOrders = 
                        (List<com.puranothread.model.order>) request.getAttribute("recentOrders");
                    
                    if (recentOrders != null && !recentOrders.isEmpty()) {
                        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy");
                        for (com.puranothread.model.order orderObj : recentOrders) {
                    %>
                        <div class="order-item">
                            <p><strong>Order ID:</strong> <%= orderObj.getOrderId() %></p>
                            <p><strong>Product:</strong> <%= orderObj.getOrderName() %></p>
                            <% if (orderObj.getOrderDate() != null) { %>
                                <p><strong>Date:</strong> <%= dateFormat.format(orderObj.getOrderDate()) %></p>
                            <% } %>
                            <p><strong>Payment:</strong> $<%= orderObj.getTotalPayment() %></p>
                            <p>
                                <strong>Status:</strong> 
                                <span class="order-status status-<%= orderObj.getStatus().toLowerCase() %>">
                                    <%= orderObj.getStatus() %>
                                </span>
                            </p>
                        </div>
                    <% 
                        }
                    } else {
                    %>
                        <div class="order-item">
                            <p>No recent orders found.</p>
                        </div>
                    <% } %>
                </div>
            </div>
        </div>
  
  <!-- Add Chart.js CDN -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<script>
    // JavaScript for the bar chart
    const salesData = { Sun: 85, Mon: 200, Tue: 150, Wed: 300, Thu: 90, Fri: 220, Sat: 180 };
    
    // Auto-scale Y-axis with 11 ticks (10 intervals)
    const maxSales = Math.max(...Object.values(salesData));
    const step = Math.ceil(maxSales / 10); // 10 intervals = 11 ticks
    const maxY = step * 10; // Adjust maxY to fit 10 intervals
    
    // Create the chart
    new Chart(document.getElementById('salesChart'), {
        type: 'line', // Line chart
        data: {
            labels: Object.keys(salesData),
            datasets: [{
                label: 'Sales',
                data: Object.values(salesData),
                backgroundColor: 'rgba(255, 255, 255, 0.1)', // Transparent fill
                borderColor: '#ffffff', // White line
                borderWidth: 2,
                pointBackgroundColor: '#ffffff', // White points
                pointBorderColor: '#ffffff', // White border for points
                pointRadius: 5, // Larger points
                tension: 0 // Straight lines
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            layout: {
                padding: {
                    top: 20,
                    bottom: 20,
                    left: 20,
                    right: 20
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    max: maxY,
                    ticks: {
                        stepSize: step, // Adjusted step size for 10 intervals
                        color: '#ffffff', // White Y-axis labels
                        font: {
                            family: 'Anek Malayalam',
                            size: 12
                        }
                    },
                    grid: {
                        color: '#5a2a6b', // Purple grid lines
                        lineWidth: 1
                    }
                },
                x: {
                    ticks: {
                        color: '#ffffff', // White X-axis labels
                        font: {
                            family: 'Anek Malayalam',
                            size: 12
                        }
                    },
                    grid: {
                        color: '#5a2a6b', // Purple grid lines
                        lineWidth: 1
                    }
                }
            },
            plugins: {
                legend: {
                    display: false // Hide the legend
                },
                tooltip: {
                    callbacks: {
                        label: function(context) {
                            return ` ${context.raw} Sales`; // Custom tooltip text
                        }
                    }
                }
            }
        }
    });
</script>
</body>
</html>