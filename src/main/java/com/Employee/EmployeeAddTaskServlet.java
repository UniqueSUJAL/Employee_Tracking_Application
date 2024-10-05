package com.Employee;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalTime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.Employee.model.DBConnection;

@WebServlet("/EmployeeAddTaskServlet")
public class EmployeeAddTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String employeeId = (String) session.getAttribute("employeeId");

        String taskName = request.getParameter("taskName");
        String taskDescription = request.getParameter("taskDescription");
        String taskDate = request.getParameter("taskDate");
        LocalTime taskStartTime = LocalTime.parse(request.getParameter("taskStartTime"));
        LocalTime taskEndTime = LocalTime.parse(request.getParameter("taskEndTime"));
        String status = "Pending"; // Default status

        long taskDuration = Duration.between(taskStartTime, taskEndTime).toMinutes();

        if (taskDuration <= 0) {
            response.sendRedirect("employee_addTask.jsp?status=invalid_duration");
            return;
        }

        boolean isDuplicate = isDuplicateTask(employeeId, taskDate, taskStartTime, taskEndTime);
        boolean isOverlapping = isOverlappingTask(employeeId, taskDate, taskStartTime, taskEndTime);

        if (isDuplicate) {
            response.sendRedirect("employee_addTask.jsp?status=duplicate");
        } else if (isOverlapping) {
            response.sendRedirect("employee_addTask.jsp?status=overlapping");
        } else if (!isValidDuration(taskStartTime, taskEndTime, employeeId, taskDate)) {
            response.sendRedirect("employee_addTask.jsp?status=exceed_duration");
        } else {
            addTask(employeeId, taskName, taskDescription, taskDate, taskStartTime, taskEndTime, status);
            response.sendRedirect("employee_addTask.jsp?status=success");
        }
    }

    private boolean isDuplicateTask(String employeeId, String taskDate, LocalTime startTime, LocalTime endTime) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM EmployeeAddTask WHERE employee_id = ? AND task_date = ? AND start_time = ? AND end_time = ?");
            ps.setString(1, employeeId);
            ps.setString(2, taskDate);
            ps.setString(3, startTime.toString());
            ps.setString(4, endTime.toString());
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isOverlappingTask(String employeeId, String taskDate, LocalTime startTime, LocalTime endTime) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT start_time, end_time FROM EmployeeAddTask WHERE employee_id = ? AND task_date = ?");
            ps.setString(1, employeeId);
            ps.setString(2, taskDate);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LocalTime existingStartTime = rs.getTime("start_time").toLocalTime();
                LocalTime existingEndTime = rs.getTime("end_time").toLocalTime();
                
                if (startTime.isBefore(existingEndTime) && endTime.isAfter(existingStartTime)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isValidDuration(LocalTime startTime, LocalTime endTime, String employeeId, String taskDate) {
        int newTaskDuration = (int) Duration.between(startTime, endTime).toMinutes();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT start_time, end_time FROM EmployeeAddTask WHERE employee_id = ? AND task_date = ?");
            ps.setString(1, employeeId);
            ps.setString(2, taskDate);
            ResultSet rs = ps.executeQuery();

            int totalDuration = newTaskDuration;
            while (rs.next()) {
                LocalTime existingStartTime = rs.getTime("start_time").toLocalTime();
                LocalTime existingEndTime = rs.getTime("end_time").toLocalTime();
                totalDuration += (int) Duration.between(existingStartTime, existingEndTime).toMinutes();
            }

            return totalDuration <= 480; // 480 minutes = 8 hours
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void addTask(String employeeId, String taskName, String taskDescription, String taskDate, LocalTime startTime, LocalTime endTime, String status) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO EmployeeAddTask (employee_id, task_name, description, task_date, start_time, end_time, status) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, employeeId);
            ps.setString(2, taskName);
            ps.setString(3, taskDescription);
            ps.setString(4, taskDate);
            ps.setString(5, startTime.toString());
            ps.setString(6, endTime.toString());
            ps.setString(7, status);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
