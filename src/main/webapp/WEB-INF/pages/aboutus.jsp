<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>About Us</title>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/aboutus.css">
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
				<button data-tooltip="My Account">
					<img src="images/accounticon.png" alt="Search">
				</button>
			</a> <a href="MyFavouritesPage.html">
				<button data-tooltip="My Favourites">
					<img src="images/wishlisticon.png" alt="User">
				</button>
			</a> <a href="ShoppingCartPage.html">
				<button data-tooltip="Shopping Cart">
					<img src="images/carticon.png" alt="Cart">
				</button>
			</a>
		</div>
	</nav>

	<div class="banner-section">
		<div class="banner-container">
			<img src="images/BannerImage.png" alt="Banner Image"
				class="banner-image">
			<h1 class="banner-title">Welcome to PuranoThreads</h1>
			<p class="banner-subtitle">Your one-stop shop for timeless
				fashion and style.</p>
			<div class="arrow-container">
				<div class="arrow"></div>
				<div class="arrow"></div>
			</div>
		</div>
	</div>

	<div class="section">
		<h1>What's our aim?</h1>
		<p>
			We strive for a sustainable way of life with responsible consumption.
			We minimize waste by recycling and consuming second-hand products <br>
			for a second opportunity at a new life. We select quality products at
			affordable prices with an eco-friendly attitude. We are <br>
			dedicated to making meaningful decisions, communities, and the planet
			a better place for kids.
		</p>
	</div>

	<div class="curved-container">
		<h1>Our Team</h1>
		<p>
			Get to know our team of individuals who care about sustainable
			fashion and living. Every single one of them is an integral part of
			the team, sorting the <br> gems or assisting customers. We make
			shopping second-hand fun and sustainable for everyone.
		</p>
		<div class="team-images">
			<div class="team-card">
				<div class="role">Database Expert</div>
				<div class="team-photo">
					<img src="images/Aastha.jpg" alt="Aastha Adhikari">
				</div>
				<span class="name">Aastha Adhikari</span>
			</div>
			<div class="team-card">
				<div class="role">Backend Developer</div>
				<div class="team-photo">
					<img src="images/Aarati.jpg" alt="Aarati Dhakal">
				</div>
				<span class="name">Aarati Dhakal</span>
			</div>
			<div class="team-card">
				<div class="role">Web Designer/Dev</div>
				<div class="team-photo">
					<img src="images/Aaditya.jpg" alt="Aaditya Bhattarai">
				</div>
				<span class="name">Aaditya Bhattarai</span>
			</div>
			<div class="team-card">
				<div class="role">Products Analyst</div>
				<div class="team-photo">
					<img src="images/Renid.jpg" alt="Renid Rai">
				</div>
				<span class="name">Renid Rai</span>
			</div>
			<div class="team-card">
				<div class="role">Marketing Specialist</div>
				<div class="team-photo">
					<img src="images/Sadie.jpg" alt="Sadie Mahaji">
				</div>
				<span class="name">Sadie Mahaji</span>
			</div>
		</div>
	</div>

<div class="contact-section">
    <div class="form-container">
        <form action="${pageContext.request.contextPath}/aboutus" method="post">
            <textarea name="reviewContent" placeholder="Type your message here .." required></textarea>
            <div class="send-button-container">
                <button type="submit" class="send-button">➤</button>
            </div>
        </form>
        
        <!-- Display success or error message if available -->
        <c:if test="${not empty reviewSuccess}">
            <div class="success-message">${reviewSuccess}</div>
        </c:if>
        <c:if test="${not empty reviewError}">
            <div class="error-message">${reviewError}</div>
        </c:if>
    </div>
    <div class="info-container">
        <h1>Contact Us</h1>
        <p>
            Got questions or suggestions? We're open to all serious<br>inquiries,
            reach out!
        </p>
    </div>
</div>