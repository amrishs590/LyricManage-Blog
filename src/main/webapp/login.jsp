<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Login</title>
<style>
    body {
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        background: #e6f0fa;
        margin: 0;
        padding: 0;
        display: flex;
        justify-content: center;
        align-items: center;
        min-height: 100vh;
    }
    .login-container {
        background: #fff;
        padding: 40px 30px;
        border-radius: 10px;
        box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        width: 350px;
    }
    h1 {
        text-align: center;
        color: #1e3a8a;
        margin-bottom: 25px;
    }
    form label {
        display: block;
        margin: 10px 0 5px;
        font-weight: bold;
        color: #1e3a8a;
    }
    form input[type="text"],
    form input[type="password"] {
        width: 93%;
        padding: 10px;
        border-radius: 6px;
        border: 1px solid #99b3e6;
        margin-bottom: 15px;
        font-size: 0.95rem;
    }
    form input[type="submit"] {
        width: 100%;
        padding: 10px;
        background: #1e40af;
        border: none;
        border-radius: 6px;
        color: white;
        font-weight: bold;
        cursor: pointer;
        transition: 0.3s;
    }
    form input[type="submit"]:hover {
        background: #1e3a8a;
    }
</style>
</head>
<body>
    <div class="login-container">
        <h1>Login</h1>
        <form method="post" action="LoginServlet">
            <label>Username:</label>
            <input type="text" name="username" required>

            <label>Password:</label>
            <input type="password" name="password" required>

            <input type="submit" value="Login">
        </form>
    </div>
</body>
</html>
