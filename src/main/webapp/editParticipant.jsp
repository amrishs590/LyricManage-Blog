<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.app.model.Participant" %>
<%
    Participant p = (Participant) request.getAttribute("participant");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Participant</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: #e6f0fa;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
        }
        .container {
            background: #fff;
            padding: 30px 40px;
            margin-top: 50px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
            width: 400px;
        }
        h2 {
            text-align: center;
            color: #1e3a8a;
            margin-bottom: 25px;
        }
        form label {
            display: block;
            margin: 12px 0 5px;
            font-weight: bold;
            color: #1e3a8a;
        }
        form input[type="text"],
        form input[type="password"] {
            width: 100%;
            padding: 10px;
            border-radius: 6px;
            border: 1px solid #99b3e6;
            font-size: 0.95rem;
        }
        form button {
            background: #1e40af;
            color: white;
            border: none;
            padding: 10px 18px;
            border-radius: 6px;
            font-weight: bold;
            cursor: pointer;
            width: 100%;
            margin-top: 20px;
            transition: 0.3s;
        }
        form button:hover {
            background: #1e3a8a;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Edit Participant</h2>
        <form action="UpdateParticipantServlet" method="post">
            <input type="hidden" name="user_id" value="<%= p.getId() %>"/>
            
            <label>Username:</label>
            <input type="text" name="username" value="<%= p.getUsername() %>" required/>
            
            <label>Password (optional):</label>
            <input type="password" name="password" placeholder="Leave blank to keep current"/>
            
            <button type="submit">Update</button>
        </form>
    </div>
</body>
</html>
