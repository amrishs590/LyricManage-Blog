package com.app.servlets;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.app.model.Participant;
import com.app.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ListParticipantsServlet")
public class ListParticipantsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Participant> participants = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT user_id, username FROM Users WHERE role='Participant'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("user_id");
                String username = rs.getString("username");
                participants.add(new Participant(id, username, "")); 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("participantsList", participants);
        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }
}
