package com.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeAddTaskDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/employee_db";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public boolean isDuplicateTask(String employeeId, String date, String startTime, String endTime) {
        String query = "SELECT COUNT(*) FROM employeeAddTask WHERE employee_id = ? AND task_date = ? AND "
                     + "((start_time <= ? AND end_time > ?) OR (start_time < ? AND end_time >= ?))";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            stmt.setString(2, date);
            stmt.setString(3, startTime);
            stmt.setString(4, startTime);
            stmt.setString(5, endTime);
            stmt.setString(6, endTime);
            
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void addTask(String employeeId, String taskName, String description, String date, String startTime, String endTime, String status) {
        String query = "INSERT INTO employeeAddTask (employee_id, task_name, description, task_date, start_time, end_time, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            stmt.setString(2, taskName);
            stmt.setString(3, description);
            stmt.setString(4, date);
            stmt.setString(5, startTime);
            stmt.setString(6, endTime);
            stmt.setString(7, status);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
