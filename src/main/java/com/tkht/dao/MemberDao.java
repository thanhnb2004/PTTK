package com.tkht.dao;

import com.tkht.model.Member;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDao extends DaoBase {
    
    public List<Member> findAll() throws SQLException {
        List<Member> members = new ArrayList<>();
        String sql = "SELECT id, name, username, password, email, staff_id FROM member ORDER BY id";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                members.add(new Member(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("staff_id")
                ));
            }
        }
        return members;
    }

    public Member findById(String id) throws SQLException {
        String sql = "SELECT id, name, username, password, email, staff_id FROM member WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("staff_id")
                    );
                }
            }
        }
        return null;
    }

    public Member findByUsername(String username) throws SQLException {
        String sql = "SELECT id, name, username, password, email, staff_id FROM member WHERE username = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Member(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getString("staff_id")
                    );
                }
            }
        }
        return null;
    }

    public void create(Member member) throws SQLException {
        String sql = "INSERT INTO member(id, name, username, password, email, staff_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getId());
            ps.setString(2, member.getName());
            ps.setString(3, member.getUsername());
            ps.setString(4, member.getPassword());
            ps.setString(5, member.getEmail());
            ps.setString(6, member.getStaffId());
            ps.executeUpdate();
        }
    }

    public void update(Member member) throws SQLException {
        String sql = "UPDATE member SET name = ?, username = ?, password = ?, email = ?, staff_id = ? WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, member.getName());
            ps.setString(2, member.getUsername());
            ps.setString(3, member.getPassword());
            ps.setString(4, member.getEmail());
            ps.setString(5, member.getStaffId());
            ps.setString(6, member.getId());
            ps.executeUpdate();
        }
    }

    public void delete(String id) throws SQLException {
        String sql = "DELETE FROM member WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, id);
            ps.executeUpdate();
        }
    }
}
