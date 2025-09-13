package com.app.servlets;

import com.app.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
//import jakarta.servlet.annotation.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

//@WebServlet("/ToggleLyricVisibilityServlet")
public class ToggleLyricVisibilityServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int lyricId = Integer.parseInt(request.getParameter("lyricId"));
//        boolean currentStatusAuto = Boolean.parseBoolean(request.getParameter("currentStatusAuto"));
//        boolean newStatusAuto = !currentStatusAuto;
        
        boolean currentStatusAdmin = Boolean.parseBoolean(request.getParameter("currentStatusAdmin"));
        boolean newStatusAdmin = !currentStatusAdmin; 

        String sql = "UPDATE Lyrics SET is_override_visible = ? WHERE lyric_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, newStatusAdmin);
            ps.setInt(2, lyricId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("LyricAdminServlet");
    }
}
