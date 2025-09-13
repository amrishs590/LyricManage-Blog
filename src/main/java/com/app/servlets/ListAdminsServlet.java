package com.app.servlets;

import com.app.model.Admin;
import com.app.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ListAdminsServlet")
public class ListAdminsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Admin> admins = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT user_id, username FROM Users WHERE role='Admin'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String username = rs.getString("username");
                admins.add(new Admin(id, username));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("adminList", admins);
        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }
}
