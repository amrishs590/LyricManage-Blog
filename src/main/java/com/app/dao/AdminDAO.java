package com.app.dao;

import com.app.model.Admin;
import com.app.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {
    public List<Admin> getAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT user_id, username FROM Users WHERE role='Admin'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                admins.add(new Admin(rs.getInt("user_id"), rs.getString("username")));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return admins;
    }
}
