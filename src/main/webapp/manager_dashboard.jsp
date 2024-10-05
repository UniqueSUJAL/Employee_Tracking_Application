<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Work Track System</title>
    <style>
        body {
            font-family: Arial, sans-serif;
        }
        .navbar {
            overflow: hidden;
            background-color: #333;
        }
        .navbar a {
            float: left;
            display: block;
            color: white;
            text-align: center;
            padding: 14px 20px;
            text-decoration: none;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }
        .navbar a.logout {
            float: right;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <a href="Manager_dashboard.jsp">Home</a>
        <a href="Associate_task.jsp">Add Task</a>
        <a href="listOfTasks.jsp">List Of Tasks</a>
        <a href="logout.jsp" class="logout">Logout</a>
    </div>
</body>
</html>
