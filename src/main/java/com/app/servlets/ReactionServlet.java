package com.app.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.app.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class ReactionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("user_id");
        int lyricId = Integer.parseInt(request.getParameter("lyric_id"));
        String reactionType = request.getParameter("reaction_type"); // "upvote" or "downvote"

        try (Connection conn = DBConnection.getConnection()) {
            // Delete old reaction (if exists) for the same lyric + user
            String deleteSql = "DELETE FROM Reactions WHERE user_id=? AND lyric_id=?";
            PreparedStatement ps1 = conn.prepareStatement(deleteSql);
            ps1.setInt(1, userId);
            ps1.setInt(2, lyricId);
            ps1.executeUpdate();

            // Insert new reaction
            String insertSql = "INSERT INTO Reactions(user_id, lyric_id, reaction_type) VALUES (?, ?, ?)";
            PreparedStatement ps2 = conn.prepareStatement(insertSql);
            ps2.setInt(1, userId);
            ps2.setInt(2, lyricId);
            ps2.setString(3, reactionType);
            ps2.executeUpdate();

            // ✅ After inserting, check if dislikes > 20 → hide lyric
            String checkSql = "SELECT COUNT(*) FROM Reactions WHERE lyric_id=? AND reaction_type='downvote'";
            PreparedStatement cps = conn.prepareStatement(checkSql);
            cps.setInt(1, lyricId);
            ResultSet crs = cps.executeQuery();

            if (crs.next()) {
            	int downvotes = crs.getInt(1);

                // ✅ Get current override status
                String statusSql = "SELECT is_override_visible FROM Lyrics WHERE lyric_id=?";
                PreparedStatement sps = conn.prepareStatement(statusSql);
                sps.setInt(1, lyricId);
                ResultSet srs = sps.executeQuery();

                boolean overrideVisible = false;
                if (srs.next()) {
                    overrideVisible = srs.getBoolean("is_override_visible");
                }

                // ✅ Apply auto-hide only if admin has NOT forced visible
                if (downvotes >= 5 && !overrideVisible) {
                    String hideSql = "UPDATE Lyrics SET is_hidden=TRUE, is_override_visible=FALSE WHERE lyric_id=?";
                    PreparedStatement ups = conn.prepareStatement(hideSql);
                    ups.setInt(1, lyricId);
                    ups.executeUpdate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("UserDashboard");
    }
}
