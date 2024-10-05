package com.Employee;

import com.google.gson.Gson;
import com.Employee.model.EmployeeProgress;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/taskSummary")
public class EmployeeProgressServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        try {
            String employeeId = request.getParameter("employeeId");
            String period = request.getParameter("period");
            
            if (employeeId == null || period == null) {
                throw new IllegalArgumentException("Missing required parameters.");
            }

            EmployeeProgressDAO dao = new EmployeeProgressDAO();
            List<EmployeeProgress> summaries = dao.getTaskSummary(employeeId, period);
            
            Gson gson = new Gson();
            String json = gson.toJson(summaries);
            
            out.print(json);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            String errorMessage = "An error occurred while processing your request.";
            out.print("{\"error\": \"" + errorMessage + "\", \"details\": \"" + e.getMessage() + "\"}");
        } finally {
            out.flush();
        }
    }
}
