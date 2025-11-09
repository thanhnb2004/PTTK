package com.tkht.dao;

import com.tkht.config.DatabaseConfig;
import com.tkht.model.PurchaseInvoice_SupplierPart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PurchaseInvoice_SupplierPartDao {
    public List<PurchaseInvoice_SupplierPart> findAll() throws SQLException {
        List<PurchaseInvoice_SupplierPart> items = new ArrayList<>();
        String sql = "SELECT id, quantity, price, supplier_part_id, purchase_invoice_id FROM purchaseinvoice_supplierpart ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(new PurchaseInvoice_SupplierPart(
                    rs.getString("id"),
                    rs.getFloat("quantity"),
                    rs.getFloat("price"),
                    rs.getString("supplier_part_id"),
                    rs.getString("purchase_invoice_id")
                ));
            }
        }
        return items;
    }

    public PurchaseInvoice_SupplierPart findById(String id) throws SQLException {
        String sql = "SELECT id, quantity, price, supplier_part_id, purchase_invoice_id FROM purchaseinvoice_supplierpart WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new PurchaseInvoice_SupplierPart(
                        rs.getString("id"),
                        rs.getFloat("quantity"),
                        rs.getFloat("price"),
                        rs.getString("supplier_part_id"),
                        rs.getString("purchase_invoice_id")
                    );
                }
            }
        }
        return null;
    }

    public void create(PurchaseInvoice_SupplierPart item) throws SQLException {
        String sql = "INSERT INTO purchaseinvoice_supplierpart(id, quantity, price, supplier_part_id, purchase_invoice_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getId());
            ps.setFloat(2, item.getQuantity());
            ps.setFloat(3, item.getPrice());
            ps.setString(4, item.getSupplierPartId());
            ps.setString(5, item.getPurchaseInvoiceId());
            ps.executeUpdate();
        }
    }

    public void update(PurchaseInvoice_SupplierPart item) throws SQLException {
        String sql = "UPDATE purchaseinvoice_supplierpart SET quantity = ?, price = ?, supplier_part_id = ?, purchase_invoice_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setFloat(1, item.getQuantity());
            ps.setFloat(2, item.getPrice());
            ps.setString(3, item.getSupplierPartId());
            ps.setString(4, item.getPurchaseInvoiceId());
            ps.setString(5, item.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM purchaseinvoice_supplierpart WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}