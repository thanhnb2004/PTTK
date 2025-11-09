package com.tkht.dao;

import com.tkht.config.DatabaseConfig;
import com.tkht.model.SaleInvoice_PurchaseInvoiceSupplierPart;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleInvoice_PurchaseInvoiceSupplierPartDao {
    
    public List<SaleInvoice_PurchaseInvoiceSupplierPart> findAll() throws SQLException {
        List<SaleInvoice_PurchaseInvoiceSupplierPart> items = new ArrayList<>();
        String sql = "SELECT id, quantity, price, purchase_invoice_supplier_part_id FROM saleinvoice_supplierpart ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                items.add(new SaleInvoice_PurchaseInvoiceSupplierPart(
                    rs.getString("id"),
                    rs.getInt("quantity"),
                    rs.getFloat("price"),
                    rs.getString("purchase_invoice_supplier_part_id")
                ));
            }
        }
        return items;
    }

    public SaleInvoice_PurchaseInvoiceSupplierPart findById(String id) throws SQLException {
        String sql = "SELECT id, quantity, price, purchase_invoice_supplier_part_id FROM saleinvoice_supplierpart WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new SaleInvoice_PurchaseInvoiceSupplierPart(
                        rs.getString("id"),
                        rs.getInt("quantity"),
                        rs.getFloat("price"),
                        rs.getString("purchase_invoice_supplier_part_id")
                    );
                }
            }
        }
        return null;
    }

    public void create(SaleInvoice_PurchaseInvoiceSupplierPart item) throws SQLException {
        String sql = "INSERT INTO saleinvoice_supplierpart(id, quantity, price, purchase_invoice_supplier_part_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, item.getId());
            ps.setInt(2, item.getQuantity());
            ps.setFloat(3, item.getPrice());
            ps.setString(4, item.getPurchaseInvoiceSupplierPartId());
            ps.executeUpdate();
        }
    }

    public void update(SaleInvoice_PurchaseInvoiceSupplierPart item) throws SQLException {
        String sql = "UPDATE saleinvoice_supplierpart SET quantity = ?, price = ?, purchase_invoice_supplier_part_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setFloat(1, item.getQuantity());
            ps.setFloat(2, item.getPrice());
            ps.setString(3, item.getPurchaseInvoiceSupplierPartId());
            ps.setString(3, item.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM saleinvoice_supplierpart WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
