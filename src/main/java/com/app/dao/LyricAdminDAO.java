package com.app.dao;

import com.app.model.LyricAdmin;
import com.app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LyricAdminDAO {
    public List<LyricAdmin> getAllLyrics() {
        List<LyricAdmin> lyrics = new ArrayList<>();
        String query = "SELECT l.lyric_id, l.lyric_text, u.username, l.created_at, l.is_hidden, l.is_override_visible " +
                       "FROM Lyrics l JOIN Users u ON l.user_id = u.user_id " +
                       "ORDER BY l.created_at DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lyrics.add(new LyricAdmin(
                        rs.getInt("lyric_id"),
                        rs.getString("lyric_text"),
                        rs.getString("username"),
                        rs.getTimestamp("created_at"),
                        rs.getBoolean("is_hidden"),
                        rs.getBoolean("is_override_visible")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lyrics;
    }
}
