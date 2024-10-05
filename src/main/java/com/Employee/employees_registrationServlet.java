package com.Employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/employees_registrationServlet")
public class employees_registrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database URL, username, and password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/employee_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        // Get form parameters
        String employeeId = request.getParameter("employeeId");
        String name = request.getParameter("name");
        String role = request.getParameter("role");
        String phoneNumber = request.getParameter("phoneNumber");
        String password = request.getParameter("password");
        String dob = request.getParameter("dob");

        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h2>Employee Registration Details</h2>");
        out.println("<p>Employee ID: " + employeeId + "</p>");
        out.println("<p>Name: " + name + "</p>");
        out.println("<p>Role: " + role + "</p>");
        out.println("<p>Phone Number: " + phoneNumber + "</p>");
        out.println("<p>Password: " + password + "</p>");
        out.println("<p>Date of Birth: " + dob + "</p>");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare an SQL INSERT statement
            String sql = "INSERT INTO registration (employeeId, name, role, phoneNumber, password, dob) VALUES (?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(employeeId));
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, role);
            preparedStatement.setString(4, phoneNumber);
            preparedStatement.setString(5, password);
            preparedStatement.setDate(6, java.sql.Date.valueOf(dob));

            // Execute the statement
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                out.println("<p>Registration successful!</p>");
            } else {
                out.println("<p>Registration failed. Please try again.</p>");
            }

        } catch (ClassNotFoundException e) {
            out.println("<p>Error loading database driver: " + e.getMessage() + "</p>");
        } catch (SQLException e) {
            out.println("<p>Error executing SQL query: " + e.getMessage() + "</p>");
        } finally {
            // Close the resources
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                out.println("<p>Error closing database resources: " + e.getMessage() + "</p>");
            }
        }
        out.println("</body></html>");
    }
}
