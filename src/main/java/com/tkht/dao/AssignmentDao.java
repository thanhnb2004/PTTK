package com.tkht.dao;

import com.tkht.config.DatabaseConfig;
import com.tkht.model.Assignment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AssignmentDao {
    
    public List<Assignment> findAll() throws SQLException {
        List<Assignment> assignments = new ArrayList<>();
        String sql = "SELECT id, delivery_date, deadline, sales_invoice_id FROM assignment ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                assignments.add(new Assignment(
                    rs.getString("id"),
                    rs.getDate("delivery_date"),
                    rs.getDate("deadline"),
                    rs.getString("sales_invoice_id")
                ));
            }
        }
        return assignments;
    }

    public Assignment findById(String id) throws SQLException {
        String sql = "SELECT id, delivery_date, deadline, sales_invoice_id FROM assignment WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Assignment(
                        rs.getString("id"),
                        rs.getDate("delivery_date"),
                        rs.getDate("deadline"),
                        rs.getString("sales_invoice_id")
                    );
                }
            }
        }
        return null;
    }

    public void create(Assignment assignment) throws SQLException {
        String sql = "INSERT INTO assignment(id, delivery_date, deadline, sales_invoice_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, assignment.getId());
            ps.setDate(2, assignment.getDeliveryDate() != null ? new Date(assignment.getDeliveryDate().getTime()) : null);
            ps.setDate(3, assignment.getDeadline() != null ? new Date(assignment.getDeadline().getTime()) : null);
            ps.setString(4, assignment.getSalesInvoiceId());
            ps.executeUpdate();
        }
    }

    public void update(Assignment assignment) throws SQLException {
        String sql = "UPDATE assignment SET delivery_date = ?, deadline = ?, sales_invoice_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, assignment.getDeliveryDate() != null ? new Date(assignment.getDeliveryDate().getTime()) : null);
            ps.setDate(2, assignment.getDeadline() != null ? new Date(assignment.getDeadline().getTime()) : null);
            ps.setString(3, assignment.getSalesInvoiceId());
            ps.setString(4, assignment.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM assignment WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
