package com.tkht.dao;

import com.tkht.config.DatabaseConfig;
import com.tkht.model.Sales_Invoice;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Sales_InvoiceDao {
    
    public List<Sales_Invoice> findAll() throws SQLException {
        List<Sales_Invoice> invoices = new ArrayList<>();
        String sql = "SELECT id, sale_date, total_amount, payment_status, vehicle_id FROM sales_invoice ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                invoices.add(new Sales_Invoice(
                    rs.getString("id"),
                    rs.getDate("sale_date"),
                    rs.getFloat("total_amount"),
                    rs.getString("payment_status"),
                    rs.getString("vehicle_id")
                ));
            }
        }
        return invoices;
    }

    public Sales_Invoice findById(String id) throws SQLException {
        String sql = "SELECT id, sale_date, total_amount, payment_status, vehicle_id FROM sales_invoice WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Sales_Invoice(
                        rs.getString("id"),
                        rs.getDate("sale_date"),
                        rs.getFloat("total_amount"),
                        rs.getString("payment_status"),
                        rs.getString("vehicle_id")
                    );
                }
            }
        }
        return null;
    }

    public void create(Sales_Invoice invoice) throws SQLException {
        String sql = "INSERT INTO sales_invoice(id, sale_date, total_amount, payment_status, vehicle_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, invoice.getId());
            ps.setDate(2, invoice.getSaleDate() != null ? new Date(invoice.getSaleDate().getTime()) : null);
            ps.setFloat(3, invoice.getTotalAmount());
            ps.setString(4, invoice.getPaymentStatus());
            ps.setString(5, invoice.getVehicleId());
            ps.executeUpdate();
        }
    }

    public void update(Sales_Invoice invoice) throws SQLException {
        String sql = "UPDATE sales_invoice SET sale_date = ?, total_amount = ?, payment_status = ?, vehicle_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, invoice.getSaleDate() != null ? new Date(invoice.getSaleDate().getTime()) : null);
            ps.setFloat(2, invoice.getTotalAmount());
            ps.setString(3, invoice.getPaymentStatus());
            ps.setString(4, invoice.getVehicleId());
            ps.setString(5, invoice.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM sales_invoice WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
