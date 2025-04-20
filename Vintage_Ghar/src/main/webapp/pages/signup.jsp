<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>Login Page</title>
  <style>
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
      font-family: 'Segoe UI', sans-serif;
    }

    body {
      background-color: #c8ecff;
    }

    .banner {
      background-color: #629bb8;
      color: white;
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 15px 30px;
      border-radius: 10px 10px 0 0;
    }

    .logo {
      font-size: 1.3rem;
      display: flex;
      align-items: center;
      gap: 8px;
    }

    nav a {
      margin: 0 10px;
      text-decoration: none;
      color: white;
      font-weight: bold;
      letter-spacing: 1px;
    }

    .icons {
      display: flex;
      align-items: center;
      gap: 12px;
    }

    .icon {
      width: 20px;
      height: 20px;
      background-color: #d9d9d9;
      border-radius: 50%;
    }

    .icon.active {
      border-bottom: 2px solid white;
    }

    .login-container {
      max-width: 600px;
      margin: 60px auto;
      background-color: white;
      border-radius: 40px;
      padding: 50px 40px;
      display: flex;
      flex-direction: column;
      align-items: center;
    }

    .login-container h2 {
      font-size: 28px;
      font-weight: bold;
      color: #888;
      margin-bottom: 40px;
    }

    .login-container input {
      width: 100%;
      max-width: 450px;
      padding: 15px 20px;
      margin-bottom: 20px;
      border: 2px solid #aaa;
      border-radius: 20px;
      font-size: 16px;
      text-align: center;
      color: #777;
    }

    .login-container .links {
      margin-top: 10px;
      display: flex;
      gap: 10px;
      font-size: 14px;
      color: #789;
    }

    .login-container .links a {
      color: #789;
      text-decoration: none;
    }

    .login-container .links span {
      color: #ccc;
    }
  </style>
</head>
<body>
  <header class="banner">
    <div class="logo">★ LOGO</div>
    <nav>
      <a href="#">PAGE</a>
      <a href="#">PAGE</a>
      <a href="#">PAGE</a>
      <a href="#">PAGE</a>
      <a href="#">PAGE</a>
    </nav>
    <div class="icons">
      <i class="fa-solid fa-magnifying-glass"></i>
      <div class="icon"></div>
      <div class="icon"></div>
      <div class="icon active"></div>
    </div>
  </header>

  <main class="login-container">
    <h2>LOG IN</h2>
    <input type="text" placeholder="ENTER YOUR USERNAME/EMAIL" />
    <input type="password" placeholder="ENTER YOUR PASSWORD" />
    <div class="links">
      <a href="#">Sign up now</a>
      <span>|</span>
      <a href="#">Forgot Password?</a>
    </div>
  </main>
</body>
</html>