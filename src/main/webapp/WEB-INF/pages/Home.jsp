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
            <a href="HomePage.html" class="active">Home</a>
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

    <div class="homepage-container">
        <div class="section section-1">
            <div class="image-container">
                <!-- First Image -->
                <div class="slider-image active" style="background-image: url('images/Sample1.png');"></div>
                <!-- Second Image -->
                <div class="slider-image" style="background-image: url('images/Sample2.png');"></div>
                <!-- Third Image -->
                <div class="slider-image" style="background-image: url('images/Sample3.png');"></div>
                <!-- Fourth Image -->
                <div class="slider-image" style="background-image: url('images/Sample4.png');"></div>
                <div class="slider-image" style="background-image: url('images/Sample5.png');"></div>
                <!-- Shared Button Container -->
                <div class="button-container">
                    <button onclick="location.href='ShopPage.html'">Button 1</button>
                    <button onclick="location.href='AboutUsPage.html'">Button 2</button>
                </div>
            </div>
        </div>
    
        <div class="section section-2">
            <h2 class="section-title">Search By Inpos</h2>
            <hr class="section-divider">
            <div class="image-grid">
                <button class="image-square" onclick="location.href='ShopPage.html'" style="background-image: url('images/Streetwear.png'); background-size: cover; background-position: center;">
                    <span>Streetwear</span>
                </button>
                <button class="image-square" onclick="location.href='ShopPage.html'" style="background-image: url('images/OldMoney.png'); background-size: cover; background-position: center;">
                    <span>Old Money</span>
                </button>
                <button class="image-square" onclick="location.href='ShopPage.html'" style="background-image: url('images/Lanacore.png'); background-size: cover; background-position: center;">
                    <span>Lana-core</span>
                </button>
                <button class="image-square" onclick="location.href='ShopPage.html'" style="background-image: url('images/TylerCore.png'); background-size: cover; background-position: center;">
                    <span>Tyler The Creator</span>
                </button>
                <button class="image-square" onclick="location.href='ShopPage.html'" style="background-image: url('images/GrungeFairy.png'); background-size: cover; background-position: center;">
                    <span>Grunge Fairy</span>
                </button>
                <button class="image-square" onclick="location.href='ShopPage.html'" style="background-image: url('images/CyberCore.png'); background-size: cover; background-position: center;">
                    <span>Cyber</span>
                </button>
                <button class="image-square" onclick="location.href='ShopPage.html'" style="background-image: url('images/RaveCore.png'); background-size: cover; background-position: center;">
                    <span>Rave</span>
                </button>
                <button class="image-square" onclick="location.href='ShopPage.html'" style="background-image: url('images/MatrixMinimalist.png'); background-size: cover; background-position: center;">
                    <span>Matrix Minimalist</span>
                </button>
            </div>
        </div>
    
        <div class="section section-3">
            <h2 class="section-title">Explore Categories</h2>
            <hr class="section-divider">
            <div class="horizontal-grid">
                <button class="square-container" onclick="location.href='ShopPage.html'">
                    <img src="images/Tshirt.png" alt="T-shirt" class="category-image">
                    <span class="category-title">Clothing</span>
                    <hr class="category-divider">
                    <span class="category-subtext">Cloth wears that is associated with y2k, vintage, old money, streetwear, grunge, sleek crops and more.</span>
                </button>
                <button class="square-container" onclick="location.href='ShopPage.html'">
                    <img src="images/Ring.png" alt="Ring" class="category-image">
                    <span class="category-title">Accessories</span>
                    <hr class="category-divider">
                    <span class="category-subtext">Elegant Rings, Cute Vintage Cameras, Eyewears, Bracelets, Hair Clips, Vintage watches and more.</span>
                </button>
                <button class="square-container" onclick="location.href='ShopPage.html'">
                    <img src="images/Shoes.png" alt="Shoes" class="category-image">
                    <span class="category-title">Footwear</span>
                    <hr class="category-divider">
                    <span class="category-subtext">Reworked Streetwear Dunks, Chunky Platforms, Thrifted boots, Retro high-tops, Stylish Loafers, Rugged Timbs and more.</span>
                </button>
            </div>
        </div>
        <div class="footer">
            <div class="footer-section">
                <img src="images/LogowithCircle.png" alt="Logo" class="footer-logo">
        <h1 class="footer-title">PuranoThreads</h1>
        <p>Since 2025.</p>
            </div>
            <div class="footer-divider"></div>
            <div class="footer-section">
                <h1 class="footer-title">Contact Info</h1>
                <p>Email: contact@example.com</p>
            </div>
            <div class="footer-divider"></div>
            <div class="footer-section map">
                <iframe 
                    src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d15983706.473234728!2d9.501785999999999!3d23.416202!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x11e9b1b1b1b1b1b1%3A0x0!2zMjPCsDI1JzAwLjMiTiA5wrAzMCcwMC4wIkU!5e0!3m2!1sen!2sus!4v1680000000000!5m2!1sen!2sus" 
                    width="100%" 
                    height="150" 
                    style="border:0; border-radius: 10px;" 
                    allowfullscreen="" 
                    loading="lazy" 
                    referrerpolicy="no-referrer-when-downgrade">
                </iframe>
            </div>
        </div>


    <script>
        let currentIndex = 0;
        const images = document.querySelectorAll('.slider-image');
    
        function changeSlide() {
            images[currentIndex].classList.remove('active');
            currentIndex = (currentIndex + 1) % images.length;
            images[currentIndex].classList.add('active');
        }
    
        setInterval(changeSlide, 2800);
    </script>

</body>
</html>