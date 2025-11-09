package com.tkht.dao;

import com.tkht.config.DatabaseConfig;
import com.tkht.model.Staff_Assignment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Staff_AssignmentDao {
    
    public List<Staff_Assignment> findAll() throws SQLException {
        List<Staff_Assignment> assignments = new ArrayList<>();
        String sql = "SELECT id, staff_id, assignment_id, sales_invoice_id FROM staff_assignment ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                assignments.add(new Staff_Assignment(
                    rs.getString("id"),
                    rs.getString("staff_id"),
                    rs.getString("assignment_id"),
                    rs.getString("sales_invoice_id")
                ));
            }
        }
        return assignments;
    }

    public Staff_Assignment findById(String id) throws SQLException {
        String sql = "SELECT id, staff_id, assignment_id, sales_invoice_id FROM staff_assignment WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Staff_Assignment(
                        rs.getString("id"),
                        rs.getString("staff_id"),
                        rs.getString("assignment_id"),
                        rs.getString("sales_invoice_id")
                    );
                }
            }
        }
        return null;
    }

    public void create(Staff_Assignment assignment) throws SQLException {
        String sql = "INSERT INTO staff_assignment(id, staff_id, assignment_id, sales_invoice_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, assignment.getId());
            ps.setString(2, assignment.getStaffId());
            ps.setString(3, assignment.getAssignmentId());
            ps.setString(4, assignment.getSalesInvoiceId());
            ps.executeUpdate();
        }
    }

    public void update(Staff_Assignment assignment) throws SQLException {
        String sql = "UPDATE staff_assignment SET staff_id = ?, assignment_id = ?, sales_invoice_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, assignment.getStaffId());
            ps.setString(2, assignment.getAssignmentId());
            ps.setString(3, assignment.getSalesInvoiceId());
            ps.setString(4, assignment.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM staff_assignment WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
