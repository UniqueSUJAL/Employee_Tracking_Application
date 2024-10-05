package com.Employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AssignTaskServlet")
public class AssignTaskServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_db", "root", "root");
            Statement stmt = conn.createStatement();
            String query = "";
            ResultSet rs;

            if ("list".equalsIgnoreCase(action)) {
                String type = request.getParameter("type");
                if ("employee".equalsIgnoreCase(type)) {
                    query = "SELECT employeeId, name FROM registration WHERE role='employee'";
                } else if ("manager".equalsIgnoreCase(type)) {
                    query = "SELECT employeeId, name FROM registration WHERE role='manager'";
                }
                rs = stmt.executeQuery(query);
                out.println("<ul>");
                while (rs.next()) {
                    out.println("<li>Id: " + rs.getString("employeeId") + " - Name: " + rs.getString("name") + "</li>");
                }
                out.println("</ul>");
                rs.close();
            } else if ("verify".equalsIgnoreCase(action)) {
                String employeeId = request.getParameter("employeeId");
                query = "SELECT * FROM registration WHERE employeeId='" + employeeId + "'";
                rs = stmt.executeQuery(query);
                if (rs.next()) {
                    out.println("Employee ID: " + employeeId + " - Name: " + rs.getString("name"));
                } else {
                    out.println("Employee ID: " + employeeId + " is not found.");
                }
                rs.close();
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        } finally {
            out.close();
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");

        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_db", "root", "root");

            if ("submitTaskRecord".equalsIgnoreCase(action)) {
                String employeeId = request.getParameter("employeeId");
                String taskDescription = request.getParameter("taskDescription");
                String startTime = request.getParameter("startTime");
                String endTime = request.getParameter("endTime");

                // Validate end time
                java.time.LocalTime start = java.time.LocalTime.parse(startTime);
                java.time.LocalTime end = java.time.LocalTime.parse(endTime);
                if (end.isAfter(start) && java.time.Duration.between(start, end).toHours() <= 8) {
                    String query = "INSERT INTO Assign_Task_Record (employeeId, date, time, taskDescription, completionTime) " +
                                   "VALUES (?, CURDATE(), ?, ?, ?)";
                    PreparedStatement pstmt = conn.prepareStatement(query);
                    pstmt.setInt(1, Integer.parseInt(employeeId));
                    pstmt.setTime(2, java.sql.Time.valueOf(startTime));
                    pstmt.setString(3, taskDescription);
                    pstmt.setTime(4, java.sql.Time.valueOf(endTime));
                    int result = pstmt.executeUpdate();
                    if (result > 0) {
                        out.println("Task record submitted successfully.");
                    } else {
                        out.println("Failed to submit task record.");
                    }
                    pstmt.close();
                } else {
                    out.println("End time must be within 8 hours of start time.");
                }
            } else {
                out.println("Action parameter is missing or incorrect.");
            }

            conn.close();
        } catch (Exception e) {
            out.println("Error: " + e.getMessage());
        } finally {
            out.close();
        }
    }
}
