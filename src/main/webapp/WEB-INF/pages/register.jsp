<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Sign Up</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/register.css">
</head>
<body>
    
    <div class="signup-container">
        <h1>Sign Up</h1>
        <form method="post" action="${pageContext.request.contextPath}/register">
            <input type="text" name="username" placeholder="Enter your username" class="input-field" required>
            <input type="tel" name="contact" placeholder="Enter your contact info" class="input-field" required>
            <input type="email" name="email" placeholder="Enter your email address" class="input-field" required>
            <div class="password-container">
    <input type="password" name="password" placeholder="Enter your Password (9-15 characters)"
           class="input-field" id="password" required minlength="9" maxlength="15">
    <button type="button" class="toggle-password" onclick="togglePasswordVisibility()">
        👁
    </button>
</div>

      
            <div class="terms-section">
                <label>
                    <input type="checkbox" name="terms" required>
                    By ticking the circle, I confirm that I have read and agree to the Terms of Use.
                    <a href="#" class="terms-link">Read Terms of Use</a>
                </label>
            </div>
            <button type="submit" class="confirm-button">
                <span>Confirm</span>
            </button>
        </form>
        <div class="already-account">
            ALREADY HAVE AN ACCOUNT? 
            <a href="${pageContext.request.contextPath}/login"class="button"><span>LOG IN</span></a>
        	
        </div>
    </div>
</body>
</html>