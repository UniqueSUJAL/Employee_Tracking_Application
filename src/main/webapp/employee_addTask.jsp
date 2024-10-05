<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Task</title>
    <style>
        /* Add some basic styling */
        form {
            margin: 20px;
        }
        label {
            display: block;
            margin: 10px 0 5px;
        }
        input, textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
        }
        input[type="submit"] {
            width: auto;
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
    </style>
    <script>
        // Function to set today's date
        function setTodaysDate() {
            const today = new Date();
            const year = today.getFullYear();
            const month = String(today.getMonth() + 1).padStart(2, '0');
            const day = String(today.getDate()).padStart(2, '0');
            const formattedDate = `${year}-${month}-${day}`;
            document.getElementById('taskDate').value = todayDate();
        }

        // Function to calculate task duration
        function calculateTaskDuration() {
            const startTime = document.getElementById('taskStartTime').value;
            const endTime = document.getElementById('taskEndTime').value;

            if (startTime && endTime) {
                const start = new Date(`1970-01-01T${startTime}:00`);
                const end = new Date(`1970-01-01T${endTime}:00`);
                const duration = (end - start) / (1000 * 60); // duration in minutes

                if (duration > 0) {
                    document.getElementById('taskDuration').innerText = `Task Duration: ${duration} minutes`;
                } else {
                    document.getElementById('taskDuration').innerText = 'End time must be after start time.';
                }
            }
        }
    </script>
</head>
<body onload="setTodaysDate()">
    <h1>Add Task</h1>
    <form action="EmployeeAddTaskServlet" method="post">
        <label for="taskName">Task Name:</label>
        <input type="text" id="taskName" name="taskName" required>

        <label for="taskDescription">Description:</label>
        <textarea id="taskDescription" name="taskDescription" rows="4" required></textarea>

        <label for="taskDate">Date:</label>
        <input type="date" id="taskDate" name="taskDate" required>

        <label for="taskStartTime">Start Time:</label>
        <input type="time" id="taskStartTime" name="taskStartTime" required onchange="calculateTaskDuration()">

        <label for="taskEndTime">End Time:</label>
        <input type="time" id="taskEndTime" name="taskEndTime" required onchange="calculateTaskDuration()">

        <div id="taskDuration"></div>
        <button type="button" onclick="window.location.href='employee_dashboard.jsp'" class="form-back">Back</button>

        <input type="submit" value="Add Task">
        
    </form>

    <%
        String status = request.getParameter("status");
        if (status != null) {
            switch (status) {
                case "duplicate":
                    out.println("<p style='color:red;'>Duplicate task entry for the same time.</p>");
                    break;
                case "invalid_duration":
                    out.println("<p style='color:red;'>End time must be after start time.</p>");
                    break;
                case "exceed_duration":
                    out.println("<p style='color:red;'>Total working hours exceed 8 hours for the day.</p>");
                    break;
                case "success":
                    out.println("<p style='color:green;'>Task added successfully.</p>");
                    break;
                default:
                    out.println("<p style='color:red;'>An error occurred. Please try again.</p>");
                    break;
            }
        }
    %>
</body>
</html>
