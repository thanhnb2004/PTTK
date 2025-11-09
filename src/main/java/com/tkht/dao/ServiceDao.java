package com.tkht.dao;

import com.tkht.model.Service;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceDao extends DaoBase {

    public List<Service> findAll() throws SQLException {
        String sql = "SELECT s.id, s.name, array_remove(array_agg(DISTINCT ssi.sales_invoice_id), NULL) AS sales_invoice_ids " +
                     "FROM service s " +
                     "LEFT JOIN service_sales_invoice ssi ON s.id = ssi.service_id " +
                     "GROUP BY s.id, s.name " +
                     "ORDER BY s.id";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            List<Service> services = new ArrayList<>();
            while (rs.next()) {
                services.add(mapService(rs));
            }
            return services;
        }
    }

    public Service findById(String id) throws SQLException {
        String sql = "SELECT s.id, s.name, array_remove(array_agg(DISTINCT ssi.sales_invoice_id), NULL) AS sales_invoice_ids " +
                     "FROM service s " +
                     "LEFT JOIN service_sales_invoice ssi ON s.id = ssi.service_id " +
                     "WHERE s.id = ? " +
                     "GROUP BY s.id, s.name";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                
                if (rs.next()) {
                    //String id = rs.getString("id");
                    String name = rs.getString("name");
                    System.out.println("id: " + id + ", name: " + name);
                    return mapService(rs);
                }
            }
        }
        return null;
    }

    public List<Service> searchByName(String keyword) throws SQLException {
        String sql = "SELECT s.id, s.name, array_remove(array_agg(DISTINCT ssi.sales_invoice_id), NULL) AS sales_invoice_ids " +
                     "FROM service s " +
                     "LEFT JOIN service_sales_invoice ssi ON s.id = ssi.service_id " +
                     "WHERE s.name ILIKE ? " +
                     "GROUP BY s.id, s.name " +
                     "ORDER BY s.id";
        List<Service> services = new ArrayList<>();
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    services.add(mapService(rs));
                }
            }
        }
        return services;
    }

    public void create(Service service) throws SQLException {
        String sql = "INSERT INTO service(id, name) VALUES (?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, service.getId());
            ps.setString(2, service.getName());
            ps.executeUpdate();
        }
    }

    public void update(Service service) throws SQLException {
        String sql = "UPDATE service SET name = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, service.getName());
            ps.setString(2, service.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM service WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }

    private Service mapService(ResultSet rs) throws SQLException {
        Service service = new Service(
            rs.getString("id"),
            rs.getString("name")
        );
        Array array = rs.getArray("sales_invoice_ids");
        if (array != null) {
            String[] invoiceIds = (String[]) array.getArray();
            service.setSalesInvoiceIds(invoiceIds != null ? new ArrayList<>(Arrays.asList(invoiceIds)) : new ArrayList<>());
        } else {
            service.setSalesInvoiceIds(new ArrayList<>());
        }
        return service;
    }
}
