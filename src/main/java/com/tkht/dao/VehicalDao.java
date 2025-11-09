package com.tkht.dao;

import com.tkht.config.DatabaseConfig;
import com.tkht.model.Vehical;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicalDao {
    
    public List<Vehical> findAll() throws SQLException {
        List<Vehical> vehicals = new ArrayList<>();
        String sql = "SELECT id, model, license_plate, sales_invoice_id FROM vehical ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                vehicals.add(new Vehical(
                    rs.getString("id"),
                    rs.getString("model"),
                    rs.getString("license_plate")
                ));
            }
        }
        return vehicals;
    }

    public Vehical findById(String id) throws SQLException {
        String sql = "SELECT id, model, license_plate, sales_invoice_id FROM vehical WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Vehical(
                        rs.getString("id"),
                        rs.getString("model"),
                        rs.getString("license_plate")
                    );
                }
            }
        }
        return null;
    }

    public void create(Vehical vehical) throws SQLException {
        String sql = "INSERT INTO vehical(id, model, license_plate) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehical.getId());
            ps.setString(2, vehical.getModel());
            ps.setString(3, vehical.getLicensePlate());
            ps.executeUpdate();
        }
    }

    public void update(Vehical vehical) throws SQLException {
        String sql = "UPDATE vehical SET model = ?, license_plate = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, vehical.getModel());
            ps.setString(2, vehical.getLicensePlate());
            ps.setString(3, vehical.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM vehical WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
