package com.app.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.app.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class PostLyricServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("user_id");
        String lyricText = request.getParameter("lyric_text");
        String genre = request.getParameter("genre");
        String mood = request.getParameter("mood");

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Lyrics(user_id, lyric_text, genre, mood, created_at, is_hidden) VALUES(?, ?, ?, ?, NOW(), false)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, lyricText);
            ps.setString(3, genre);
            ps.setString(4, mood);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("UserDashboard");
    }
}


//package com.app.servlets;
//
//import java.io.IOException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//
//import com.app.util.DBConnection;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//public class PostLyricServlet extends HttpServlet {
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession(false);
//        if (session == null || session.getAttribute("user_id") == null) {
//            response.sendRedirect("login.jsp");
//            return;
//        }
//
//        int userId = (int) session.getAttribute("user_id");
//        String lyricText = request.getParameter("lyric_text");
//                
//        try (Connection conn = DBConnection.getConnection()) {
//            String sql = "INSERT INTO Lyrics(user_id, lyric_text) VALUES(?, ?)";
//            PreparedStatement ps = conn.prepareStatement(sql);
//            ps.setInt(1, userId);
//            ps.setString(2, lyricText);
//            ps.executeUpdate();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        response.sendRedirect("UserDashboard");
//    }
//}
