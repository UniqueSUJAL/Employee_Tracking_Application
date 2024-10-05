package com.Employee;



import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import com.Employee.model.Task;

@WebServlet("/MangerTaskServlet")
public class MangerTaskServlet extends HttpServlet {

    private TaskDAO taskDAO;

    @Override
    public void init() {
        taskDAO = new TaskDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String employeeId = request.getSession().getAttribute("employeeId").toString();
        String date = request.getParameter("taskDate");
        String time = request.getParameter("taskTime");
        int duration = Integer.parseInt(request.getParameter("taskDuration"));
        String description = request.getParameter("taskDescription");

        response.setContentType("text/plain");

        if (duration > 8) {
            response.getWriter().write("Error: Duration cannot be more than 8 hours for a single task.");
            return;
        }

        try {
            if (taskDAO.isDuplicateEntry(employeeId, date, time)) {
                response.getWriter().write("Error: Duplicate entry for the same date and time.");
            } else {
                int totalDuration = taskDAO.getTotalDurationForDate(employeeId, date);
                if (totalDuration + duration > 8) {
                    response.getWriter().write("Error: Total task duration for the day cannot exceed 8 hours.");
                } else {
                    /*Task task = new Task(employeeId, date, time, duration, description);
                    taskDAO.addTask(task);
                    response.getWriter().write("Task added successfully.");*/
                }
            }
        } catch (SQLException e) {
            throw new ServletException("Database error", e);
        }
    }
}
