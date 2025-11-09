package com.tkht.dao;

import com.tkht.config.DatabaseConfig;
import com.tkht.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDao {
    
    public List<Supplier> findAll() throws SQLException {
        List<Supplier> suppliers = new ArrayList<>();
        String sql = "SELECT id, address, name FROM supplier ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                suppliers.add(new Supplier(
                    rs.getString("id"),
                    rs.getString("address"),
                    rs.getString("name")
                ));
            }
        }
        return suppliers;
    }

    public Supplier findById(String id) throws SQLException {
        String sql = "SELECT id, address, name FROM supplier WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Supplier(
                        rs.getString("id"),
                        rs.getString("address"),
                        rs.getString("name")
                    );
                }
            }
        }
        return null;
    }

    public void create(Supplier supplier) throws SQLException {
        String sql = "INSERT INTO supplier(id, address, name) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, supplier.getId());
            ps.setString(2, supplier.getAddress());
            ps.setString(3, supplier.getName());
            ps.executeUpdate();
        }
    }

    public void update(Supplier supplier) throws SQLException {
        String sql = "UPDATE supplier SET address = ?, name = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, supplier.getAddress());
            ps.setString(2, supplier.getName());
            ps.setString(3, supplier.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM supplier WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
