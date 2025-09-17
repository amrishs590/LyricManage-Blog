package com.app.servlets;

import java.io.IOException;
import java.sql.*;
import com.app.util.DBConnection;
import com.app.util.PasswordUtil;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null) username = "";
        if (password == null) password = "";

        username = username.trim();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT user_id, role, password_hash FROM Users WHERE username=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);

                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        request.setAttribute("errorMessage", "Invalid username or password!");
                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        rd.forward(request, response);
                        return;
                    }
                    int userId = rs.getInt("user_id");
                    String role = rs.getString("role");
                    String storedHash = rs.getString("password_hash");
                    String enteredHash = PasswordUtil.hashPassword(password);
                    // Case 1: hashed password matches
                    if (storedHash != null && storedHash.equalsIgnoreCase(enteredHash)) {
                        HttpSession session = request.getSession();
                        session.setAttribute("user_id", userId);
                        session.setAttribute("username", username);
                        session.setAttribute("role", role == null ? "" : role.toLowerCase());

                        if ("admin".equalsIgnoreCase(role)) {
                            response.sendRedirect("LyricAdminServlet");
                        } else {
                            response.sendRedirect("UserDashboard");
                        }
                        return;
                    } else {
                        request.setAttribute("errorMessage", "Invalid username or password!");
                        RequestDispatcher rd = request.getRequestDispatcher("login.jsp");
                        rd.forward(request, response);
                        return;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
