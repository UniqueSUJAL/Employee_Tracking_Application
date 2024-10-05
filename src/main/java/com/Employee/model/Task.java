package com.Employee.model;

public class Task {

    private int id; // Assuming ID is needed
    private String employeeId;
    private String taskName;
    private String description;
    private String taskDate;
    private String startTime;
    private String endTime;

    // Constructor for creating a new Task
    public Task(String employeeId, String taskName, String description, String taskDate, String startTime, String endTime) {
        this.employeeId = employeeId;
        this.taskName = taskName;
        this.description = description;
        this.taskDate = taskDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Constructor for retrieving an existing Task (from database)
    public Task(int id, String taskName, String description, String taskDate, String startTime, String endTime) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.taskDate = taskDate;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
