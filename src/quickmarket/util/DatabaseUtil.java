package quickmarket.util;

import quickmarket.model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseUtil {
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        }
    }

    private static final String DB_URL = "jdbc:mysql://localhost:3306/quickmarket?useSSL=true&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    public static void initializeDatabase() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS users (
                id INT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(50) UNIQUE NOT NULL,
                password VARCHAR(255) NOT NULL,
                role VARCHAR(20) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )
            """;
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully");
        } catch (SQLException e) {
            System.out.println("Database initialization failed:");
            e.printStackTrace();
        }
    }

    public static boolean registerUser(User user) {
        // First check if username exists
        if (usernameExists(user.getUsername())) {
            System.out.println("Username already exists");
            return false;
        }

        String sql = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            System.out.println("Attempting to register user: " + user.getUsername());
            
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getRole());
            
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Registration result: " + (rowsAffected > 0 ? "success" : "failed"));
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Registration failed:");
            e.printStackTrace();
            return false;
        }
    }

    public static User validateLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                User user = new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                );
                System.out.println("Login successful for user: " + username);
                return user;
            } else {
                System.out.println("Login failed: Invalid credentials");
            }
        } catch (SQLException e) {
            System.out.println("Login validation failed:");
            e.printStackTrace();
        }
        return null;
    }

    public static boolean usernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                boolean exists = rs.getInt(1) > 0;
                if (exists) {
                    System.out.println("Username '" + username + "' already exists");
                }
                return exists;
            }
        } catch (SQLException e) {
            System.out.println("Username check failed:");
            e.printStackTrace();
        }
        return false;
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM users";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                users.add(new User(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("role")
                ));
            }
            System.out.println("Retrieved " + users.size() + " users from database");
        } catch (SQLException e) {
            System.out.println("Getting all users failed:");
            e.printStackTrace();
        }
        return users;
    }

    // Utility method to validate role
    public static boolean isValidRole(String role) {
        String[] validRoles = {"Store Owner", "Store Staff", "Delivery Admin", "Delivery Staff"};
        for (String validRole : validRoles) {
            if (validRole.equals(role)) {
                return true;
            }
        }
        return false;
    }

    // Method to clear all users (for testing purposes)
    public static void clearAllUsers() {
        String sql = "DELETE FROM users";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("All users cleared from database");
        } catch (SQLException e) {
            System.out.println("Failed to clear users:");
            e.printStackTrace();
        }
    }
}