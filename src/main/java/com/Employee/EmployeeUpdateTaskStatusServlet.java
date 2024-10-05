//package com.Employee;
//
//import com.Employee.model.TaskDAO;
//
//import java.io.IOException;
//import java.sql.Time;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@WebServlet("/EmployeeUpdateTaskStatusServlet")
//public class EmployeeUpdateTaskStatusServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String taskId = request.getParameter("taskId");
//        String status = request.getParameter("status");
//        String startTimeStr = request.getParameter("taskStartTime");
//        String endTimeStr = request.getParameter("taskEndTime");
//
//        // Validate inputs
//        if (taskId == null || status == null || taskId.isEmpty() || status.isEmpty() || startTimeStr == null || endTimeStr == null) {
//            request.setAttribute("errorMessage", "Invalid input data.");
//            request.getRequestDispatcher("employeeTaskList.jsp").forward(request, response);
//            return;
//        }
//
//        try {
//            // Parse start time and end time to java.sql.Time
//            Time startTime = Time.valueOf(startTimeStr + ":00");
//            Time endTime = Time.valueOf(endTimeStr + ":00");
//
//            TaskDAO taskDAO = new TaskDAO();
//            boolean success = taskDAO.updateTaskDetails(taskId, status, startTime, endTime);
//
//            if (success) {
//                response.sendRedirect("employeeTaskList.jsp?successMessage=Task details updated successfully");
//            } else {
//                request.setAttribute("errorMessage", "Failed to update task details.");
//                request.getRequestDispatcher("employeeTaskList.jsp").forward(request, response);
//            }
//        } catch (IllegalArgumentException e) {
//            request.setAttribute("errorMessage", "Invalid time format. Please use HH:MM.");
//            request.getRequestDispatcher("employeeTaskList.jsp").forward(request, response);
//        }
//    }
//}
