package com.Employee.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class TaskDAO {

    // Update the status of a task by its ID
    public boolean updateTaskStatus(String taskId, String status) {
        String query = "UPDATE EmployeeAddTask SET status = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, status);
            ps.setString(2, taskId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Update the details (including status) of a task by its ID
    public boolean updateTaskDetails(String taskId, String taskName, String description, String taskDate, Time startTime, Time endTime, String status) {
        String query = "UPDATE EmployeeAddTask SET task_name = ?, description = ?, task_date = ?, start_time = ?, end_time = ?, status = ? WHERE id = ?";
        try (Connection con = DBConnection.getConnection(); 
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, taskName);
            ps.setString(2, description);
            ps.setString(3, taskDate);
            ps.setTime(4, startTime);
            ps.setTime(5, endTime);
            ps.setString(6, status);
            ps.setString(7, taskId);

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
