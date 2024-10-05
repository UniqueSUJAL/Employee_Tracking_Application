<%@ page import="com.Employee.EmployeeViewListTaskDAO" %>
<%@ page import="com.Employee.model.EmployeeViewTask" %>
<%@ page import="java.util.List" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <meta charset="UTF-8">
    <title>View Tasks</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f0f4f8;
            margin: 0;
            padding: 0;
        }
        nav {
            background-color: #2c3e50;
            color: #ecf0f1;
            padding: 15px;
        }
        nav ul {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        nav ul li {
            display: inline;
            margin-right: 15px;
        }
        nav ul li a {
            color: #ecf0f1;
            text-decoration: none;
            font-weight: bold;
        }
        nav ul li a:hover {
            text-decoration: underline;
        }
        h1 {
            text-align: center;
            margin-top: 20px;
            color: #34495e;
        }
        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
            background-color: #fff;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 12px;
            text-align: left;
        }
        th {
            background-color: #3498db;
            color: #fff;
        }
        .actions {
            text-align: center;
        }
        .actions button {
            background-color: #3498db;
            color: #fff;
            border: none;
            padding: 8px 15px;
            cursor: pointer;
            border-radius: 4px;
            font-size: 14px;
        }
        .actions button:hover {
            background-color: #2980b9;
        }
        .error {
            color: #e74c3c;
            text-align: center;
            margin: 20px 0;
        }
        .update-form {
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 20px;
            max-width: 600px;
            margin: 20px auto;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            display: none;
        }
        .update-form h2 {
            margin-top: 0;
            color: #3498db;
        }
        .update-form label {
            display: block;
            margin: 10px 0 5px;
            color: #2c3e50;
        }
        .update-form input, 
        .update-form textarea {
            width: calc(100% - 22px);
            padding: 12px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .update-form input[type="submit"] {
            background-color: #3498db;
            color: #fff;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            font-size: 16px;
            border-radius: 4px;
        }
        .update-form input[type="submit"]:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
    <nav>
        <ul>
            <li><a href="employee_dashboard.jsp">Home</a></li>
            <li><a href="employee_addTask.jsp">Add Task</a></li>
            <li><a href="employeeTaskList.jsp">View Task</a></li>
            <li><a href="employeeTasksChart.jsp">Progress</a></li>
        </ul>
    </nav>

    <h1>View Tasks</h1>

    <% 
        // Fetch tasks from the database
        EmployeeViewListTaskDAO taskDAO = new EmployeeViewListTaskDAO();
        HttpSession httpSession = request.getSession();
        String employeeId = (String) httpSession.getAttribute("employeeId");
        List<EmployeeViewTask> tasks = taskDAO.getTasksByEmployee(employeeId);

        // Display error messages if any
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
            out.println("<p class='error'>" + errorMessage + "</p>");
        }
    %>

    <table>
        <thead>
            <tr>
                <th>Task Name</th>
                <th>Description</th>
                <th>Date</th>
                <th>Start Time</th>
                <th>End Time</th>
                <th>Status</th> <!-- Added Status Column -->
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <% 
                for (EmployeeViewTask task : tasks) { 
            %>
            <tr>
                <td><%= task.getTaskName() %></td>
                <td><%= task.getDescription() %></td>
                <td><%= task.getTaskDate() %></td>
                <td><%= task.getStartTime().toString().substring(0, 5) %></td>
                <td><%= task.getEndTime().toString().substring(0, 5) %></td>
                <td><%= task.getStatus() %></td> <!-- Display Status -->
                <td class="actions">
                    <button onclick="showUpdateForm('<%= task.getId() %>', '<%= task.getTaskName() %>', '<%= task.getDescription() %>', '<%= task.getTaskDate() %>', '<%= task.getStartTime().toString().substring(0, 5) %>', '<%= task.getEndTime().toString().substring(0, 5) %>', '<%= task.getStatus() %>')">Edit</button>
                    <form action="EmployeeTaskDeleteServlet" method="post" style="display:inline;">
                        <input type="hidden" name="taskId" value="<%= task.getId() %>">
                        <button type="submit">Delete</button>
                    </form>
                    <form action="EmployeeUpdateTaskServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="updateStatus">
                        <input type="hidden" name="taskId" value="<%= task.getId() %>">
                        <input type="hidden" name="taskStartTime" value="<%= task.getStartTime().toString().substring(0, 5) %>">
                        <input type="hidden" name="taskEndTime" value="<%= task.getEndTime().toString().substring(0, 5) %>">
                        <button type="submit" name="status" value="Pending">Set Pending</button>
                        <button type="submit" name="status" value="Completed">Set Completed</button>
                    </form>
                </td>
            </tr>
            <% 
                } 
            %>
        </tbody>
    </table>

    <div class="update-form" id="updateForm">
        <h2>Update Task</h2>
        <form action="EmployeeUpdateTaskServlet" method="post">
            <input type="hidden" name="action" value="updateDetails">
            <input type="hidden" name="taskId" id="taskId">
            <label for="taskName">Task Name:</label>
            <input type="text" id="taskName" name="taskName" placeholder="Enter task name">

            <label for="taskDescription">Description:</label>
            <textarea id="taskDescription" name="taskDescription" rows="4" placeholder="Enter task description"></textarea>

            <label for="taskDate">Date:</label>
            <input type="date" id="taskDate" name="taskDate">

            <label for="taskStartTime">Start Time:</label>
            <input type="time" id="taskStartTime" name="taskStartTime" oninput="calculateTaskDuration()">

            <label for="taskEndTime">End Time:</label>
            <input type="time" id="taskEndTime" name="taskEndTime" oninput="calculateTaskDuration()">
            
            <p id="taskDuration"></p> <!-- Display task duration -->

            <input type="submit" value="Update Task">
        </form>
    </div>

    <script>
        function showUpdateForm(taskId, taskName, taskDescription, taskDate, taskStartTime, taskEndTime, status) {
            document.getElementById('taskId').value = taskId;
            document.getElementById('taskName').value = taskName;
            document.getElementById('taskDescription').value = taskDescription;
            document.getElementById('taskDate').value = taskDate;
            document.getElementById('taskStartTime').value = taskStartTime;
            document.getElementById('taskEndTime').value = taskEndTime;

            document.getElementById('updateForm').style.display = 'block';

            calculateTaskDuration(); // Calculate task duration when form is shown
        }

        function calculateTaskDuration() {
            var start = document.getElementById('taskStartTime').value;
            var end = document.getElementById('taskEndTime').value;

            if (start && end) {
                var startTime = new Date('1970-01-01T' + start + 'Z');
                var endTime = new Date('1970-01-01T' + end + 'Z');
                var diff = (endTime - startTime) / (1000 * 60); // Calculate difference in minutes

                if (diff < 0) {
                    diff = 1440 + diff; // Adjust for tasks ending after midnight
                }

                var hours = Math.floor(diff / 60);
                var minutes = diff % 60;

                document.getElementById('taskDuration').textContent = 'Duration: ' + hours + ' hours and ' + minutes + ' minutes';
            } else {
                document.getElementById('taskDuration').textContent = '';
            }
        }

        function setTodayDate() {
            var today = new Date();
            var day = ("0" + today.getDate()).slice(-2);
            var month = ("0" + (today.getMonth() + 1)).slice(-2);
            var todayDate = today.getFullYear() + "-" + month + "-" + day;

            document.getElementById("taskDate").value = todayDate;
        }

        window.onload = function() {
            setTodayDate();
        };
    </script>
</body>
</html>
