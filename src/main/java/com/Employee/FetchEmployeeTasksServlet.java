package com.Employee;

import com.Employee.model.DBConnection;
import com.Employee.model.TaskDAO;
import com.Employee.EmployeeTask;

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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/fetchEmployeeTasks")
public class FetchEmployeeTasksServlet extends HttpServlet {

    private TaskDAO taskDAO = new TaskDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employeeId");
        employeeId = "517302";
        if (employeeId != null && !employeeId.isEmpty()) {
            List<EmployeeTask> tasks = fetchTasksByEmployeeId(employeeId);
            
            request.setAttribute("tasks", tasks);
            request.getRequestDispatcher("employeeTasksChart.jsp").forward(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Employee ID is missing or invalid.");
        }
    }

    private List<EmployeeTask> fetchTasksByEmployeeId(String employeeId) {
        List<EmployeeTask> tasks = new ArrayList<>();
        employeeId = "517302";
        String query = "SELECT id, employee_id, task_name, description, task_date, start_time, end_time, status " +
                       "FROM employeeaddtask WHERE employee_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
        	System.out.println("This is from sevlet");
            ps.setString(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    EmployeeTask task = new EmployeeTask();
                    task.setId(rs.getInt("id"));
                    task.setEmployeeId(rs.getString("employee_id"));
                    task.setTaskName(rs.getString("task_name"));
                    task.setDescription(rs.getString("description"));
                    task.setTaskDate(rs.getDate("task_date"));
                    task.setStartTime(rs.getTime("start_time"));
                    task.setEndTime(rs.getTime("end_time"));
                    task.setStatus(rs.getString("status"));
                    tasks.add(task);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // You might want to handle the exception more gracefully, e.g., logging and sending an error response
        }

        return tasks;
    }
}
