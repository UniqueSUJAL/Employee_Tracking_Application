package com.Employee;

import com.Employee.model.DBConnection;
import com.Employee.model.TaskDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.Duration;
import java.time.LocalTime;

@WebServlet("/EmployeeUpdateTaskServlet")
public class EmployeeUpdateTaskServlet extends HttpServlet {

    private TaskDAO taskDAO = new TaskDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String taskId = request.getParameter("taskId");

        try {
            if ("updateDetails".equals(action)) {
                String employeeId = request.getParameter("employeeId");
                String taskName = request.getParameter("taskName");
                String description = request.getParameter("taskDescription");
                String taskDate = request.getParameter("taskDate");
                LocalTime startTime = LocalTime.parse(request.getParameter("taskStartTime"));
                LocalTime endTime = LocalTime.parse(request.getParameter("taskEndTime"));
                String status = request.getParameter("status");

                long taskDuration = Duration.between(startTime, endTime).toMinutes();

                if (taskDuration <= 0) {
                    response.sendRedirect("employeeTaskList.jsp?status=invalid_duration");
                    return;
                }

                boolean isDuplicate = isDuplicateTask(taskId, employeeId, taskDate, startTime, endTime);
                boolean isOverlapping = isOverlappingTask(taskId, employeeId, taskDate, startTime, endTime);

                if (isDuplicate) {
                    response.sendRedirect("employeeTaskList.jsp?status=duplicate");
                } else if (isOverlapping) {
                    response.sendRedirect("employeeTaskList.jsp?status=overlapping");
                } else if (!isValidDuration(taskId, startTime, endTime, employeeId, taskDate)) {
                    response.sendRedirect("employeeTaskList.jsp?status=exceed_duration");
                } else {
                    boolean isUpdated = taskDAO.updateTaskDetails(taskId, taskName, description, taskDate, Time.valueOf(startTime), Time.valueOf(endTime), status);
                    if (isUpdated) {
                        response.sendRedirect("employeeTaskList.jsp?message=Task updated successfully");
                    } else {
                        request.setAttribute("errorMessage", "Error updating task details");
                        request.getRequestDispatcher("employeeTaskList.jsp").forward(request, response);
                    }
                }
            } else if ("updateStatus".equals(action)) {
                String status = request.getParameter("status");
                boolean isUpdated = taskDAO.updateTaskStatus(taskId, status);
                if (isUpdated) {
                    response.sendRedirect("employeeTaskList.jsp?message=Task status updated successfully");
                } else {
                    request.setAttribute("errorMessage", "Error updating task status");
                    request.getRequestDispatcher("employeeTaskList.jsp").forward(request, response);
                }
            } else if ("validate".equals(action)) {
                String employeeId = request.getParameter("employeeId");
                String taskDate = request.getParameter("taskDate");
                LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
                LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));

                boolean isDuplicate = isDuplicateTask(taskId, employeeId, taskDate, startTime, endTime);
                boolean isOverlapping = isOverlappingTask(taskId, employeeId, taskDate, startTime, endTime);
                boolean isValid = isValidDuration(taskId, startTime, endTime, employeeId, taskDate);

                if (isDuplicate) {
                    response.getWriter().write("duplicate");
                } else if (isOverlapping) {
                    response.getWriter().write("overlapping");
                } else if (!isValid) {
                    response.getWriter().write("exceed_duration");
                } else {
                    response.getWriter().write("valid");
                }
            }
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Error updating task: " + e.getMessage());
            request.getRequestDispatcher("employeeTaskList.jsp").forward(request, response);
        }
    }

    private boolean isDuplicateTask(String taskId, String employeeId, String taskDate, LocalTime startTime, LocalTime endTime) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT COUNT(*) FROM EmployeeAddTask WHERE employee_id = ? AND task_date = ? AND start_time = ? AND end_time = ? AND id != ?");
            ps.setString(1, employeeId);
            ps.setString(2, taskDate);
            ps.setString(3, startTime.toString());
            ps.setString(4, endTime.toString());
            ps.setString(5, taskId);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isOverlappingTask(String taskId, String employeeId, String taskDate, LocalTime startTime, LocalTime endTime) {
        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT start_time, end_time FROM EmployeeAddTask WHERE employee_id = ? AND task_date = ? AND id != ?");
            ps.setString(1, employeeId);
            ps.setString(2, taskDate);
            ps.setString(3, taskId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LocalTime existingStartTime = rs.getTime("start_time").toLocalTime();
                LocalTime existingEndTime = rs.getTime("end_time").toLocalTime();

                if ((startTime.equals(existingStartTime) || endTime.equals(existingEndTime)) ||
                    (startTime.isBefore(existingEndTime) && endTime.isAfter(existingStartTime))) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean isValidDuration(String taskId, LocalTime startTime, LocalTime endTime, String employeeId, String taskDate) {
        int newTaskDuration = (int) Duration.between(startTime, endTime).toMinutes();

        try (Connection conn = DBConnection.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT start_time, end_time FROM EmployeeAddTask WHERE employee_id = ? AND task_date = ? AND id != ?");
            ps.setString(1, employeeId);
            ps.setString(2, taskDate);
            ps.setString(3, taskId);
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
}
