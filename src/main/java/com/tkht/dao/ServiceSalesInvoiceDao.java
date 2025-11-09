package com.tkht.dao;

import com.tkht.model.ServiceSalesInvoice;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceSalesInvoiceDao extends DaoBase {

    public List<ServiceSalesInvoice> findByServiceId(String serviceId) throws SQLException {
        String sql = "SELECT id, service_id, sales_invoice_id FROM service_sales_invoice WHERE service_id = ? ORDER BY id";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, serviceId);
            try (ResultSet rs = ps.executeQuery()) {
                List<ServiceSalesInvoice> mappings = new ArrayList<>();
                while (rs.next()) {
                    mappings.add(mapRow(rs));
                }
                return mappings;
            }
        }
    }

    public List<ServiceSalesInvoice> findBySalesInvoiceId(String salesInvoiceId) throws SQLException {
        String sql = "SELECT id, service_id, sales_invoice_id FROM service_sales_invoice WHERE sales_invoice_id = ? ORDER BY id";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, salesInvoiceId);
            try (ResultSet rs = ps.executeQuery()) {
                List<ServiceSalesInvoice> mappings = new ArrayList<>();
                while (rs.next()) {
                    mappings.add(mapRow(rs));
                }
                return mappings;
            }
        }
    }

    public void create(ServiceSalesInvoice mapping) throws SQLException {
        String sql = "INSERT INTO service_sales_invoice(id, service_id, sales_invoice_id) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, mapping.getId());
            ps.setString(2, mapping.getServiceId());
            ps.setString(3, mapping.getSalesInvoiceId());
            ps.executeUpdate();
        }
    }

    public void deleteById(String id) throws SQLException {
        String sql = "DELETE FROM service_sales_invoice WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    public void deleteByServiceAndInvoice(String serviceId, String salesInvoiceId) throws SQLException {
        String sql = "DELETE FROM service_sales_invoice WHERE service_id = ? AND sales_invoice_id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, serviceId);
            ps.setString(2, salesInvoiceId);
            ps.executeUpdate();
        }
    }

    private ServiceSalesInvoice mapRow(ResultSet rs) throws SQLException {
        return new ServiceSalesInvoice(
            rs.getString("id"),
            rs.getString("service_id"),
            rs.getString("sales_invoice_id")
        );
    }
}

