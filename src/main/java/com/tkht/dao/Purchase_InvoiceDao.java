package com.tkht.dao;

import com.tkht.config.DatabaseConfig;
import com.tkht.model.Purchase_Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Purchase_InvoiceDao {
    
    public List<Purchase_Invoice> findAll() throws SQLException {
        List<Purchase_Invoice> invoices = new ArrayList<>();
        String sql = "SELECT id, purchase_date, staff_id, status FROM purchase_invoice ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                invoices.add(new Purchase_Invoice(
                    rs.getString("id"),
                    rs.getDate("purchase_date"),
                    rs.getString("staff_id"),
                    rs.getString("status")
                ));
            }
        }
        return invoices;
    }

    public Purchase_Invoice findById(String id) throws SQLException {
        String sql = "SELECT id, purchase_date, staff_id, status FROM purchase_invoice WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Purchase_Invoice(
                        rs.getString("id"),
                        rs.getDate("purchase_date"),
                        rs.getString("staff_id"),
                        rs.getString("status")
                    );
                }
            }
        }
        return null;
    }

    public void create(Purchase_Invoice invoice) throws SQLException {
        String sql = "INSERT INTO purchase_invoice(id, purchase_date, staff_id, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, invoice.getId());
            ps.setDate(2, invoice.getPurchaseDate() != null ? new Date(invoice.getPurchaseDate().getTime()) : null);
            ps.setString(3, invoice.getStaffId());
            ps.setString(4, invoice.getStatus());
            ps.executeUpdate();
        }
    }

    public void update(Purchase_Invoice invoice) throws SQLException {
        String sql = "UPDATE purchase_invoice SET purchase_date = ?, staff_id = ?, status = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, invoice.getPurchaseDate() != null ? new Date(invoice.getPurchaseDate().getTime()) : null);
            ps.setString(2, invoice.getStaffId());
            ps.setString(3, invoice.getStatus());
            ps.setString(4, invoice.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM purchase_invoice WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
