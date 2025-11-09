package com.tkht.dao;

import com.tkht.config.DatabaseConfig;
import com.tkht.model.Staff;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StaffDao {
    
    public List<Staff> findAll() throws SQLException {
        List<Staff> staffs = new ArrayList<>();
        String sql = "SELECT id, position, member_id FROM staff ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                staffs.add(new Staff(
                    rs.getString("id"),
                    rs.getString("position"),
                    rs.getString("member_id")
                ));
            }
        }
        return staffs;
    }

    public Staff findById(String id) throws SQLException {
        String sql = "SELECT id, position, member_id FROM staff WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Staff(
                        rs.getString("id"),
                        rs.getString("position"),
                        rs.getString("member_id")
                    );
                }
            }
        }
        return null;
    }

    public void create(Staff staff) throws SQLException {
        String sql = "INSERT INTO staff(id, position, member_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staff.getId());
            ps.setString(2, staff.getPosition());
            ps.setString(3, staff.getMemberId());
            ps.executeUpdate();
        }
    }

    public void update(Staff staff) throws SQLException {
        String sql = "UPDATE staff SET position = ?, member_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, staff.getPosition());
            ps.setString(2, staff.getMemberId());
            ps.setString(3, staff.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM staff WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
