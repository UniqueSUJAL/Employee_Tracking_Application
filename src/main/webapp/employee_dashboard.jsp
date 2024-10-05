<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Dashboard</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }

        nav {
            background-color: #333;
            overflow: hidden;
        }

        nav ul {
            list-style-type: none;
            margin: 0;
            padding: 0;
            display: flex;
        }

        nav ul li {
            float: left;
        }

        nav ul li a {
            display: block;
            color: white;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
            font-size: 16px;
        }

        nav ul li a:hover {
            background-color: #575757;
            border-radius: 5px;
        }

        #content {
            padding: 20px;
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

    <div id="content">
        <!-- Content will be loaded here -->
    </div>

 
    </script>
</body>
</html>
