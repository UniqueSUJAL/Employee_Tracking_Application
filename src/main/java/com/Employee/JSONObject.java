package com.Employee;



public class JSONObject {
	public String employeeId;
    public String currentDate; 
    public String currentTime; 
    public String taskDescription;
    public String completionTime;

    // Getter and Setter for employeeId
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    // Getter and Setter for currentDate
    public String getCurrentDate() {
        return currentDate;
    }

    public void setCurrentDate(String currentDate) {
        this.currentDate = currentDate;
    }

    // Getter and Setter for currentTime
    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    // Getter and Setter for taskDescription
    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    // Getter and Setter for completionTime
    public String getCompletionTime() {
        return completionTime;
    }

    public void setCompletionTime(String completionTime) {
        this.completionTime = completionTime;
    }

    // Method to get a string value from JSON
    public String getString(String key) {
        switch (key) {
            case "employeeId":
                return getEmployeeId();
            case "currentDate":
                return getCurrentDate();
            case "currentTime":
                return getCurrentTime();
            case "taskDescription":
                return getTaskDescription();
            case "completionTime":
                return getCompletionTime();
            default:
                return null;
        }
    }

    // Method to put a value in JSON object
    public void put(String key, String value) {
        switch (key) {
            case "employeeId":
                setEmployeeId(value);
                break;
            case "currentDate":
                setCurrentDate(value);
                break;
            case "currentTime":
                setCurrentTime(value);
                break;
            case "taskDescription":
                setTaskDescription(value);
                break;
            case "completionTime":
                setCompletionTime(value);
                break;
        }
    }

    // Method to put a boolean value in JSON object
    public void put(String key, boolean value) {
        // Since boolean is not used in JsonObject class, this can be left empty or handle accordingly
    }
}
