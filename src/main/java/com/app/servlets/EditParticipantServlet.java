package com.app.servlets;

import com.app.model.Participant;
import com.app.util.DBConnection;
import java.io.IOException;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/EditParticipantServlet")
public class EditParticipantServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int userId = Integer.parseInt(request.getParameter("user_id"));
        Participant p = null;

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT user_id, username FROM Users WHERE user_id=? AND role='Participant'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                p = new Participant(rs.getInt("user_id"), rs.getString("username"), "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("participant", p);
        request.getRequestDispatcher("editParticipant.jsp").forward(request, response);
    }
}
