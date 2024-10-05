package com.Employee;

import com.Employee.model.EmployeeViewTask;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeViewListTaskDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/employee_db";
    private String jdbcUsername = "root";
    private String jdbcPassword = "root";

    // Method to get a database connection
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }

    // Method to get tasks by employee ID
    public List<EmployeeViewTask> getTasksByEmployee(String employeeId) {
        List<EmployeeViewTask> tasks = new ArrayList<>();
        String query = "SELECT * FROM employeeAddTask WHERE employee_id = ?";

        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EmployeeViewTask task = new EmployeeViewTask();
                    task.setId(rs.getInt("id"));
                    task.setTaskName(rs.getString("task_name"));
                    task.setDescription(rs.getString("description"));
                    task.setTaskDate(rs.getDate("task_date"));
                    task.setStartTime(rs.getTime("start_time"));
                    task.setEndTime(rs.getTime("end_time"));
                    task.setStatus(rs.getString("status")); // Retrieve status
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework
        }

        return tasks;
    }

    // Method to update a task
    public boolean updateTask(EmployeeViewTask task) {
        String query = "UPDATE employeeaddtask SET task_name = ?, description = ?, task_date = ?, start_time = ?, end_time = ?, status = ? WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, task.getTaskName());
            ps.setString(2, task.getDescription());
            ps.setDate(3, task.getTaskDate());
            ps.setTime(4, task.getStartTime());
            ps.setTime(5, task.getEndTime());
            ps.setString(6, task.getStatus()); // Update status
            ps.setInt(7, task.getId());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework
            return false;
        }
    }

    // Method to delete a task
    public boolean deleteTask(int taskId) {
        String query = "DELETE FROM employeeAddTask WHERE id = ?";
        try (Connection con = getConnection(); PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, taskId);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // Consider using a logging framework
            return false;
        }
    }
}
