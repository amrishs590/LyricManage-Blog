package com.app.dao;

import com.app.model.Participant;
import com.app.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ParticipantDAO {
    public List<Participant> getAllParticipants() {
        List<Participant> participants = new ArrayList<>();
        String sql = "SELECT user_id, username FROM Users where role='Participant'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                participants.add(new Participant(rs.getInt("user_id"), rs.getString("username"), sql));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return participants;
    }
}
