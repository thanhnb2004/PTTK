package com.tkht.dao;

import com.tkht.config.DatabaseConfig;
import com.tkht.model.Appointment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDao {
    
    public List<Appointment> findAll() throws SQLException {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT id, status, scheduled_time, location, appointment_date, customer_id FROM appointment ORDER BY id";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                appointments.add(new Appointment(
                    rs.getString("id"),
                    rs.getInt("status"),
                    rs.getDate("scheduled_time"),
                    rs.getString("location"),
                    rs.getDate("appointment_date"),
                    rs.getString("customer_id")
                ));
            }
        }
        return appointments;
    }

    public Appointment findById(String id) throws SQLException {
        String sql = "SELECT id, status, scheduled_time, location, appointment_date, customer_id FROM appointment WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Appointment(
                        rs.getString("id"),
                        rs.getInt("status"),
                        rs.getDate("scheduled_time"),
                        rs.getString("location"),
                        rs.getDate("appointment_date"),
                        rs.getString("customer_id")
                    );
                }
            }
        }
        return null;
    }

    public void create(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointment(id, status, scheduled_time, location, appointment_date, customer_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, appointment.getId());
            ps.setInt(2, appointment.getStatus());
            ps.setDate(3, appointment.getScheduledTime() != null ? new Date(appointment.getScheduledTime().getTime()) : null);
            ps.setString(4, appointment.getLocation());
            ps.setDate(5, appointment.getAppointmentDate() != null ? new Date(appointment.getAppointmentDate().getTime()) : null);
            ps.setString(6, appointment.getCustomerId());
            ps.executeUpdate();
        }
    }

    public void update(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointment SET status = ?, scheduled_time = ?, location = ?, appointment_date = ?, customer_id = ? WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, appointment.getStatus());
            ps.setDate(2, appointment.getScheduledTime() != null ? new Date(appointment.getScheduledTime().getTime()) : null);
            ps.setString(3, appointment.getLocation());
            ps.setDate(4, appointment.getAppointmentDate() != null ? new Date(appointment.getAppointmentDate().getTime()) : null);
            ps.setString(5, appointment.getCustomerId());
            ps.setString(6, appointment.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM appointment WHERE id = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
