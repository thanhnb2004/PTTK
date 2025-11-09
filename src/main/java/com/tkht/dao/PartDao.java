package com.tkht.dao;

import com.tkht.config.DatabaseConfig;
import com.tkht.model.Part;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartDao {
    
    public List<Part> findAll() throws SQLException {
        List<Part> parts = new ArrayList<>();
        String sql = "SELECT id, unit, name FROM part ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                parts.add(new Part(
                    rs.getString("id"),
                    rs.getString("unit"),
                    rs.getString("name")
                ));
            }
        }
        return parts;
    }

    public Part findById(String id) throws SQLException {
        String sql = "SELECT id, unit, name FROM part WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Part(
                        rs.getString("id"),
                        rs.getString("unit"),
                        rs.getString("name")
                    );
                }
            }
        }
        return null;
    }

    public void create(Part part) throws SQLException {
        String sql = "INSERT INTO part(id, unit, name) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, part.getId());
            ps.setString(2, part.getUnit());
            ps.setString(3, part.getName());
            ps.executeUpdate();
        }
    }

    public void update(Part part) throws SQLException {
        String sql = "UPDATE part SET unit = ?, name = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, part.getUnit());
            ps.setString(2, part.getName());
            ps.setString(3, part.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM part WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
