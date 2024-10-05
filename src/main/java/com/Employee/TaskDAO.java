package com.Employee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.Employee.model.Task;

public class TaskDAO {
    private String jdbcURL = "jdbc:mysql://localhost:3306/employee_db";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    public boolean isDuplicateEntry(String employeeId, String date, String time) throws SQLException {
        String query = "SELECT COUNT(*) FROM associate_tasks  WHERE employee_id = ? AND task_date = ? AND task_time = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            stmt.setString(2, date);
            stmt.setString(3, time);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    public int getTotalDurationForDate(String employeeId, String date) throws SQLException {
        String query = "SELECT SUM(duration) FROM associate_tasks   WHERE employee_id = ? AND task_date = ?";
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, employeeId);
            stmt.setString(2, date);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

//    public void addTask(Task task) throws SQLException {
//        String query = "INSERT INTO associate_tasks  (employee_id, task_date, task_time, duration, description) VALUES (?, ?, ?, ?, ?)";
//        try (Connection conn = getConnection(); 
//             PreparedStatement stmt = conn.prepareStatement(query)) {
//            stmt.setString(1, task.getEmployeeId());
//            stmt.setString(2, task.getDate());
//            stmt.setString(3, task.getTime());
//            stmt.setInt(4, task.getDuration());
//            stmt.setString(5, task.getDescription());
//            stmt.executeUpdate();
}
  

