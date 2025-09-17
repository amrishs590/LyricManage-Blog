package com.app.servlets;

import com.app.util.DBConnection;
import com.app.util.PasswordUtil;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/AddAdminServlet")
public class AddAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Users (username, password_hash, role) VALUES (?, ?, 'Admin')";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                String hashedPassword = PasswordUtil.hashPassword(password);
                ps.setString(1, username);
                ps.setString(2, hashedPassword);
                ps.executeUpdate();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("LyricAdminServlet");
    }
}
