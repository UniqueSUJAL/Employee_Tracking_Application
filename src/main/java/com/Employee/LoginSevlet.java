package com.Employee;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/LoginSevlet")
public class LoginSevlet extends HttpServlet {
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
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish a connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare an SQL query to check user credentials
            String sql = "SELECT * FROM registration WHERE employeeId = ? AND password = ?";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(employeeId));
            preparedStatement.setString(2, password);

            // Execute the query
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                // User exists, retrieve the role
                String role = resultSet.getString("role");
                HttpSession session = request.getSession();
                session.setAttribute("employeeId", employeeId);
                session.setAttribute("role", role);

                // Redirect to role-based dashboard
                if ("admin".equalsIgnoreCase(role)) {
                    response.sendRedirect("admin_dashboard.jsp");
                } else if ("manager".equalsIgnoreCase(role)) {
                    response.sendRedirect("manager_dashboard.jsp");
                } else if ("employee".equalsIgnoreCase(role)) {
                    response.sendRedirect("employee_dashboard.jsp");
                } else {
                    out.println("<h2>Login failed!</h2>");
                    out.println("<p>Unknown role.</p>");
                    out.println("<a href='Loginpage.jsp'>Try Again</a>");
                }
            } else {
                // Invalid credentials
                out.println("<h2>Login failed!</h2>");
                out.println("<p>Invalid Employee ID or Password.</p>");
                out.println("<a href='Loginpage.jsp'>Try Again</a>");
            }

        } catch (ClassNotFoundException e) {
            out.println("<p>Error loading database driver: " + e.getMessage() + "</p>");
        } catch (SQLException e) {
            out.println("<p>Error executing SQL query: " + e.getMessage() + "</p>");
        } finally {
            // Close the resources
            try {
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                out.println("<p>Error closing database resources: " + e.getMessage() + "</p>");
            }
        }
    }
}
