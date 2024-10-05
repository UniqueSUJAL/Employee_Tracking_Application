package com.Employee;

import com.Employee.EmployeeTask;
import com.Employee.model.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeTaskDAO {

    public List<EmployeeTask> getTasksByEmployeeId(String employeeId) {
        List<EmployeeTask> tasks = new ArrayList<>();
        try (Connection connection = DBConnection.getConnection()) {
            String query = "SELECT * FROM employeeaddtask WHERE employee_id = 517302";
            System.out.print("This is after your query has been created");
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
//            	employeeId 
//                preparedStatement.setString(1, employeeId);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                	System.out.println("Here the result is--->" + resultSet);
                    while (resultSet.next()) {
                        EmployeeTask task = new EmployeeTask();
                        task.setId(resultSet.getInt("id"));
                        task.setEmployeeId(resultSet.getString("employee_id"));
                        task.setTaskName(resultSet.getString("task_name"));
                        task.setDescription(resultSet.getString("description"));
                        task.setTaskDate(resultSet.getDate("task_date"));
                        task.setStartTime(resultSet.getTime("start_time"));
                        task.setEndTime(resultSet.getTime("end_time"));
                        System.out.println(task);
                        tasks.add(task);
                    }
                }catch(Exception e){
                	e.printStackTrace();
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tasks;
    }
}
