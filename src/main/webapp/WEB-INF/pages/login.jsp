<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css">
    <title>Curved Navbar</title>
    
</head>
<body>
    
    <div class="login-container">
        <h1>Log In</h1>
         <form action="login" method="post">
             <input type="text" name="username" placeholder="ENTER YOUR USERNAME/EMAIL" class="input-field">
            <div class="password-container">
                 <input type="password" name="password" placeholder="ENTER YOUR PASSWORD" class="input-field" id="password">
                <button type="button" class="toggle-password" onclick="this.previousElementSibling.type = this.previousElementSibling.type === 'password' ? 'text' : 'password'">
                    👁
                </button>
            </div>
            <div class="remember-me">
    <label>
        <input type="checkbox" class="custom-checkbox">
        <span>Remember me</span>
    </label>
</div>
            <button type="submit" class="continue-button">
                <span>Continue</span>
            </button>
        </form>
        <div class="divider">
        <a href="${pageContext.request.contextPath}/register"class="button"><span>Sign up</span></a>
            <div class="vertical-line"></div>
            <a href="ForgotPassword.html" class="forgot-password-link">Forgot Password?</a>
        </div>
    </div>

</body>
</html>