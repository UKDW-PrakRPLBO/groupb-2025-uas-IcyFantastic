package org.uas.repository;

import org.uas.data.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private final Connection connection;

    public UserRepository(Connection connection) {
        this.connection = connection;
        createTable();
        addDefaultAcc();
    }

    public void createTable() {
        // Create database tables if they don't exist
        // Implement this method to create tables for users, courses, classes, and attendance records
        String userTableSql = "CREATE TABLE IF NOT EXISTS users ("
                + "email TEXT NOT NULL PRIMARY KEY,"
                + "username TEXT NOT NULL UNIQUE,"
                + "password TEXT NOT NULL"
                + ")";
        if (connection != null) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute(userTableSql);
                // Execute more table creation statements as needed
            } catch (SQLException e) {
                System.out.println("Gagal Membuat Tabel: " + e.getMessage());
                // Handle table creation error
            }
        }
    }

    private boolean userExists(String username) throws SQLException {
        String sql = "SELECT COUNT (*) FROM users WHERE username = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return  rs.getInt(1) > 0;
            }
        }
        return false;
    }

    private void addDefaultAcc() {
        try {
            if (!userExists ("admin")) {
                insertUser("admin@gmail.com", "admin", "admin123");
            }
        } catch (SQLException e) {
            System.out.println("Gagal Menambahkan Akun Default: " + e.getMessage());
        }
    }

    public List<User> findAll() {
        ArrayList<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                users.add(new User(
                        rs.getString("email"),
                        rs.getString("username"),
                        rs.getString("password")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Gagal Mengambil Data User: " + e.getMessage());
        }
        return users;
    }

    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT COUNT (*) FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Error Terjadi Saat Proses Autentikasi: " + e.getMessage());
        }
        return false;
    }

    public boolean insertUser(String email, String username, String password) {
        String sql = "INSERT INTO users(email, username, password) VALUES(?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)){
            pstmt.setString(1, email);
            pstmt.setString(2, username);
            pstmt.setString(3, password);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Gagal Menambahkan Akun: " + e.getMessage());
            return false;
        }
    }

    public boolean updateUser(String email, String username, String password) {
        String sql = "UPDATE users SET username = ?, password = ? WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, email);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Gagal Mengupdate Akun: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteUser(String email) {
        String sql = "DELETE FROM users WHERE email = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, email);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Gagal Menghapus Akun: " + e.getMessage());
            return false;
        }
    }
}

