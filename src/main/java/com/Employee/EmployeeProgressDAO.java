package com.Employee;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.Employee.model.DBConnection;
import com.Employee.model.EmployeeProgress;

public class EmployeeProgressDAO {

    // Fetch task summary based on period (daily, weekly, monthly)
    public List<EmployeeProgress> getTaskSummary(String employeeId, String period) {
        List<EmployeeProgress> summaries = new ArrayList<>();
        String sql = "";

        switch (period) {
            case "daily":
                sql = "SELECT task_date, SUM(TIMESTAMPDIFF(HOUR, start_time, end_time)) AS total_hours_worked, " +
                      "SUM(CASE WHEN status = 'Completed' THEN 1 ELSE 0 END) AS completed_tasks, " +
                      "SUM(CASE WHEN status = 'Pending' THEN 1 ELSE 0 END) AS pending_tasks " +
                      "FROM  employeeAddTask WHERE employee_id = ? AND task_date = CURDATE() GROUP BY task_date";
                break;
            case "weekly":
                sql = "SELECT YEARWEEK(task_date, 1) AS week, SUM(TIMESTAMPDIFF(HOUR, start_time, end_time)) AS total_hours_worked, " +
                      "SUM(CASE WHEN status = 'Completed' THEN 1 ELSE 0 END) AS completed_tasks, " +
                      "SUM(CASE WHEN status = 'Pending' THEN 1 ELSE 0 END) AS pending_tasks " +
                      "FROM  employeeAddTask WHERE employee_id = ? AND task_date BETWEEN CURDATE() - INTERVAL 7 DAY AND CURDATE() GROUP BY YEARWEEK(task_date, 1)";
                break;
            case "monthly":
                sql = "SELECT DATE_FORMAT(task_date, '%Y-%m') AS month, SUM(TIMESTAMPDIFF(HOUR, start_time, end_time)) AS total_hours_worked, " +
                      "SUM(CASE WHEN status = 'Completed' THEN 1 ELSE 0 END) AS completed_tasks, " +
                      "SUM(CASE WHEN status = 'Pending' THEN 1 ELSE 0 END) AS pending_tasks " +
                      "FROM  employeeAddTask WHERE employee_id = ? AND YEAR(task_date) = YEAR(CURDATE()) GROUP BY DATE_FORMAT(task_date, '%Y-%m')";
                break;
        }

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                EmployeeProgress summary = new EmployeeProgress();
                summary.setDate(rs.getString("task_date")); // Adjust based on period (e.g., "week" or "month")
                summary.setTotalHoursWorked(rs.getInt("total_hours_worked"));
                summary.setCompletedTasks(rs.getInt("completed_tasks"));
                summary.setPendingTasks(rs.getInt("pending_tasks"));
                summaries.add(summary);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return summaries;
    }

    // Fetch all tasks for a given employee
    public List<EmployeeProgress> getAllTasks(String employeeId) {
        List<EmployeeProgress> tasks = new ArrayList<>();
        String sql = "SELECT task_date, start_time, end_time, status, task_name, description " +
                     "FROM  employeeAddTask WHERE employee_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, employeeId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                EmployeeProgress task = new EmployeeProgress();
                task.setDate(rs.getString("task_date"));
                task.setStartTime(rs.getTime("start_time"));
                task.setEndTime(rs.getTime("end_time"));
                task.setStatus(rs.getString("status"));
                task.setTaskName(rs.getString("task_name"));
                task.setDescription(rs.getString("description"));
                tasks.add(task);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tasks;
    }

    // Check and populate employee progress, for example, update overdue tasks
    public void checkAndPopulateEmployeeProgress() {
        String selectSql = "SELECT * FROM EmployeeTasks WHERE status = 'Pending'";
        String updateSql = "UPDATE  employeeAddTask SET status = 'Completed' WHERE task_date < CURDATE() AND status = 'Pending'";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement updateStmt = conn.prepareStatement(updateSql);
             PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {

            // Update pending tasks that are overdue
            int rowsUpdated = updateStmt.executeUpdate();
            System.out.println("Updated " + rowsUpdated + " overdue tasks to Completed.");

            // Fetch pending tasks if needed
            ResultSet rs = selectStmt.executeQuery();
            while (rs.next()) {
                // Handle the fetched records as needed
                // For example, you may insert records into another table or process them
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
