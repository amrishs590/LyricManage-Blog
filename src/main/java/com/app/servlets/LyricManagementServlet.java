//package com.app.servlets;
//import java.io.IOException;
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.app.util.DBConnection;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/LyricManagementServlet")
//public class LyricManagementServlet extends HttpServlet {
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        List<Lyric> lyricsList = new ArrayList<>();
//        try (Connection conn = DBConnection.getConnection()) {
//            String query = "SELECT l.lyric_id, u.username, l.lyric_text, l.is_hidden, l.is_override_visible, l.created_at " +
//                           "FROM Lyrics l JOIN Users u ON l.user_id = u.user_id";
//            PreparedStatement ps = conn.prepareStatement(query);
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                Lyric lyric = new Lyric(
//                    rs.getInt("lyric_id"),
//                    rs.getString("username"),
//                    rs.getString("lyric_text"),
//                    rs.getBoolean("is_hidden"),
//                    rs.getBoolean("is_override_visible"),
//                    rs.getTimestamp("created_at")
//                );
//                lyricsList.add(lyric);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        request.setAttribute("lyricsList", lyricsList);
//        RequestDispatcher rd = request.getRequestDispatcher("adminLyrics.jsp");
//        rd.forward(request, response);
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        String action = request.getParameter("action");
//        int lyricId = Integer.parseInt(request.getParameter("lyricId"));
//
//        try (Connection conn = DBConnection.getConnection()) {
//            if ("hide".equals(action)) {
//                PreparedStatement ps = conn.prepareStatement("UPDATE Lyrics SET is_hidden = TRUE WHERE lyric_id = ?");
//                ps.setInt(1, lyricId);
//                ps.executeUpdate();
//            } else if ("show".equals(action)) {
//                PreparedStatement ps = conn.prepareStatement("UPDATE Lyrics SET is_hidden = FALSE, is_override_visible = TRUE WHERE lyric_id = ?");
//                ps.setInt(1, lyricId);
//                ps.executeUpdate();
//            } else if ("delete".equals(action)) {
//                PreparedStatement ps = conn.prepareStatement("DELETE FROM Lyrics WHERE lyric_id = ?");
//                ps.setInt(1, lyricId);
//                ps.executeUpdate();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        response.sendRedirect("LyricManagementServlet");
//    }
//}
//
//class Lyric {
//    private int lyricId;
//    private String username;
//    private String lyricText;
//    private boolean hidden;
//    private boolean overrideVisible;
//    private Timestamp createdAt;
//
//    public Lyric(int lyricId, String username, String lyricText, boolean hidden, boolean overrideVisible, Timestamp createdAt) {
//        this.lyricId = lyricId;
//        this.username = username;
//        this.lyricText = lyricText;
//        this.hidden = hidden;
//        this.overrideVisible = overrideVisible;
//        this.createdAt = createdAt;
//    }
//
//    public int getLyricId() { return lyricId; }
//    public String getUsername() { return username; }
//    public String getLyricText() { return lyricText; }
//    public boolean isHidden() { return hidden; }
//    public boolean isOverrideVisible() { return overrideVisible; }
//    public Timestamp getCreatedAt() { return createdAt; }
//}
