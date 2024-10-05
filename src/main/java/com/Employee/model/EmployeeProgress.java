package com.Employee.model;

import java.sql.Time;

public class EmployeeProgress {
    private String date; // This can be task_date or week/month based on the period
    private Time startTime;
    private Time endTime;
    private String status;
    private String taskName;
    private String description;
    private int totalHoursWorked;
    private int completedTasks;
    private int pendingTasks;

    // Getters and Setters

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public int getTotalHoursWorked() {
        return totalHoursWorked;
    }

    public void setTotalHoursWorked(int totalHoursWorked) {
        this.totalHoursWorked = totalHoursWorked;
    }

    public int getCompletedTasks() {
        return completedTasks;
    }

    public void setCompletedTasks(int completedTasks) {
        this.completedTasks = completedTasks;
    }

    public int getPendingTasks() {
        return pendingTasks;
    }

    public void setPendingTasks(int pendingTasks) {
        this.pendingTasks = pendingTasks;
    }
}
