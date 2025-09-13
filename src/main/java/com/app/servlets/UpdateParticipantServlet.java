package com.app.servlets;

import com.app.util.DBConnection;
import java.io.IOException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/UpdateParticipantServlet")
public class UpdateParticipantServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("user_id"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            String sql;
            PreparedStatement ps;

            if (password != null && !password.isEmpty()) {
                sql = "UPDATE Users SET username=?, password_hash=? WHERE user_id=? AND role='Participant'";
                ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setString(2, password); // hash later if needed
                ps.setInt(3, userId);
            } else {
                sql = "UPDATE Users SET username=? WHERE user_id=? AND role='Participant'";
                ps = conn.prepareStatement(sql);
                ps.setString(1, username);
                ps.setInt(2, userId);
            }

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("LyricAdminServlet");
    }
}
