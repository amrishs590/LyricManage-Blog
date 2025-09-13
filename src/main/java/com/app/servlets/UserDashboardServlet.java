package com.app.servlets;

import java.io.IOException;
import java.sql.*;
import java.util.*;
import com.app.util.DBConnection;
import com.app.model.Lyric;
import com.app.model.Comment;

import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class UserDashboardServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("user_id");
        String filter = request.getParameter("filter");
        if (filter == null) filter = "all"; // default

        List<Lyric> lyrics = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
        	String baseSql = 
        		    "SELECT l.lyric_id, l.lyric_text, u.username, l.created_at, " +
        		    "COALESCE(l.genre, 'Not specified') AS genre, " +
        		    "COALESCE(l.mood, 'Not specified') AS mood, " +
        		    "SUM(CASE WHEN r.reaction_type='upvote' THEN 1 ELSE 0 END) AS upvotes, " +
        		    "SUM(CASE WHEN r.reaction_type='downvote' THEN 1 ELSE 0 END) AS downvotes, " +
        		    "(SELECT COUNT(*) FROM Comments c WHERE c.lyric_id=l.lyric_id) AS comment_count " +
        		    "FROM Lyrics l " +
        		    "LEFT JOIN Users u ON l.user_id=u.user_id " +
        		    "LEFT JOIN Reactions r ON l.lyric_id=r.lyric_id " +
        		    "WHERE ((l.is_hidden = false and l.is_override_visible = false) " +
        		    "OR (l.is_hidden = true and l.is_override_visible=true)) ";
            if ("mine".equals(filter)) {
                baseSql += "AND l.user_id = ? ";
            } else if ("others".equals(filter)) {
                baseSql += "AND l.user_id <> ? ";
            }

            baseSql += "GROUP BY l.lyric_id ORDER BY l.created_at DESC";

            PreparedStatement ps;
            if ("mine".equals(filter) || "others".equals(filter)) {
                ps = conn.prepareStatement(baseSql);
                ps.setInt(1, userId);
            } else {
                ps = conn.prepareStatement(baseSql);
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int lyricId = rs.getInt("lyric_id");

                // Fetch comments for this lyric
                String commentSql = 
                    "SELECT c.comment_id, c.comment_text, u.username, c.created_at " +
                    "FROM Comments c JOIN Users u ON c.user_id=u.user_id " +
                    "WHERE c.lyric_id=?";
                PreparedStatement cps = conn.prepareStatement(commentSql);
                cps.setInt(1, lyricId);
                ResultSet crs = cps.executeQuery();

                List<Comment> comments = new ArrayList<>();
                while (crs.next()) {
                    comments.add(new Comment(
                        crs.getInt("comment_id"),
                        lyricId,
                        crs.getString("username"),
                        crs.getString("comment_text"),
                        crs.getTimestamp("created_at")
                    ));
                }

                Lyric lyric = new Lyric(
                    lyricId,
                    rs.getString("lyric_text"),
                    rs.getString("username"),
                    rs.getTimestamp("created_at"),
                    rs.getInt("upvotes"),
                    rs.getInt("downvotes"),
                    rs.getInt("comment_count"),
                    comments,
                    rs.getString("genre"),
                    rs.getString("mood")
                );

                lyrics.add(lyric);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("lyrics", lyrics);
        request.setAttribute("filter", filter);
        RequestDispatcher rd = request.getRequestDispatcher("userDashboard.jsp");
        rd.forward(request, response);
    }
}

