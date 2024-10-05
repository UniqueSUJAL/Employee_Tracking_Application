package com.Employee;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/EmployeeTaskDeleteServlet")
public class EmployeeTaskDeleteServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("taskId"));

        EmployeeViewListTaskDAO dao = new EmployeeViewListTaskDAO();
        boolean isDeleted = dao.deleteTask(taskId);

        if (isDeleted) {
            response.sendRedirect("employeeTaskList.jsp?successMessage=Task deleted successfully");
        } else {
            response.sendRedirect("employeeTaskList.jsp?errorMessage=Failed to delete task");
        }
    }
}
