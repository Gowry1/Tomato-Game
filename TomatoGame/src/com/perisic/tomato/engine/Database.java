package com.perisic.tomato.engine;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {

    // JDBC database URL, username, and password
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/gowry";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";
	public static String username;

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);

        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL JDBC Driver not found.", e);
        }
    }

    public static void closeConnection(Connection connection) {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public static boolean insertScoreIntoDatabase(int id, int score) {
        try {
            Connection connection = Database.getConnection();
            String query = "INSERT INTO Score (id, score) VALUES (?, ?)"; // Assuming id is the foreign key for the user
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, score);
            int rowsAffected = preparedStatement.executeUpdate();
            connection.close();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void saveOverallScore(int id, int score) {
        try  {
            Connection connection = Database.getConnection();

            String checkQuery = "SELECT * FROM score WHERE id = ?";
            try (PreparedStatement checkStatement = connection.prepareStatement(checkQuery)) {
                checkStatement.setInt(1, id);
                try (ResultSet resultSet = checkStatement.executeQuery()) {
                    if (resultSet.next()) {
                        String updateQuery = "UPDATE score SET score = ? WHERE id = ?";
                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                            updateStatement.setInt(1, score);
                            updateStatement.setInt(2, id);
                            updateStatement.executeUpdate();
                        }
                    } else {
                        // User doesn't exist, insert a new record
                        String insertQuery = "INSERT INTO score (id, score) VALUES (?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
                            insertStatement.setInt(1, id);
                            insertStatement.setInt(2, score);
                            insertStatement.executeUpdate();
                        }
                    }
                }
            }

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public static int validateLoginAndGetUserID(String username, String password) {
        String query = "SELECT id FROM user WHERE username = ? AND password = ?";
        try (Connection connection = Database.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("id"); 
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return -1; // Return -1 if login fails
    }
    
   
    public static boolean validateLogin1(String username, String email, String password) {
        try {
            String query = "INSERT INTO user (username, email, password) VALUES (?, ?, ?)";
            try (Connection connection = Database.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(query)) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, password);

                int rowsAffected = preparedStatement.executeUpdate();

                return rowsAffected > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        }
    
    public static void main(String[] args) {
        Connection connection = null;

        try {
            connection = getConnection();


            System.out.println("Database connection successful!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection(connection);
        }
    }
}
