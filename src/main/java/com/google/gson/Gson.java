package com.google.gson;
import com.Employee.EmployeeProgressDAO;
import com.Employee.EmployeeProgressServlet;
import com.Employee.model.EmployeeProgress;


import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EmployeeProgressServlet")
public class Gson extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String employeeId = request.getParameter("employeeId");
        String period = request.getParameter("period");
        
        EmployeeProgressDAO dao = new EmployeeProgressDAO();
        List<EmployeeProgress> summaries = dao.getTaskSummary(employeeId, period);
        
        Gson gson = new Gson();
        String json = gson.toJson(summaries);
        
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        out.print(json);
        out.flush();
    }

	public String toJson(List<EmployeeProgress> summaries) {
		
		return null;
	}
}
