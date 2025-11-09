package com.tkht.dao;

import com.tkht.config.DatabaseConfig;
import com.tkht.model.Supplier_Part;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Supplier_PartDao {
    
    public List<Supplier_Part> findAll() throws SQLException {
        List<Supplier_Part> supplierParts = new ArrayList<>();
        String sql = "SELECT id, supplier_id, part_id FROM supplier_part ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                supplierParts.add(new Supplier_Part(
                    rs.getString("id"),
                    rs.getString("supplier_id"),
                    rs.getString("part_id")
                ));
            }
        }
        return supplierParts;
    }

    public Supplier_Part findById(String id) throws SQLException {
        String sql = "SELECT id, supplier_id, part_id FROM supplier_part WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Supplier_Part(
                        rs.getString("id"),
                        rs.getString("supplier_id"),
                        rs.getString("part_id")
                    );
                }
            }
        }
        return null;
    }

    public void create(Supplier_Part supplierPart) throws SQLException {
        String sql = "INSERT INTO supplier_part(id, supplier_id, part_id) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, supplierPart.getId());
            ps.setString(2, supplierPart.getSupplierId());
            ps.setString(3, supplierPart.getPartId());
            ps.executeUpdate();
        }
    }

    public void update(Supplier_Part supplierPart) throws SQLException {
        String sql = "UPDATE supplier_part SET supplier_id = ?, part_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, supplierPart.getSupplierId());
            ps.setString(2, supplierPart.getPartId());
            ps.setString(6, supplierPart.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM supplier_part WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    public List<Supplier_Part> findBySupplierId(String supplierId) throws SQLException {
        List<Supplier_Part> supplierParts = new ArrayList<>();
        String sql = "SELECT id, supplier_id, part_id FROM supplier_part WHERE supplier_id = ? ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, supplierId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    supplierParts.add(new Supplier_Part(
                        rs.getString("id"),
                        rs.getString("supplier_id"),
                        rs.getString("part_id")
                    ));
                }
            }
        }
        return supplierParts;
    }

    public Supplier_Part findBySupplierIdAndPartId(String supplierId, String partId) throws SQLException {
        String sql = "SELECT id, supplier_id, part_id FROM supplier_part WHERE supplier_id = ? AND part_id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, supplierId);
            ps.setString(2, partId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Supplier_Part(
                        rs.getString("id"),
                        rs.getString("supplier_id"),
                        rs.getString("part_id")
                    );
                }
            }
        }
        return null;
    }
}
