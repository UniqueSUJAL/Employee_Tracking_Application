<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Random" %>
<%
    // Generate a random 6-digit Employee ID
    Random random = new Random();
    int employeeId = 100000 + random.nextInt(900000);
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Registration</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .registration-form {
            background-color: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            width: 300px;
        }
        .registration-form h2 {
            margin-bottom: 20px;
            text-align: center;
        }
        .registration-form label {
            display: block;
            margin-bottom: 5px;
        }
        .registration-form input,
        .registration-form select {
            width: 100%;
            padding: 10px;
            margin-bottom: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        .registration-form button {
            width: 100%;
            padding: 10px;
            background-color: #5cb85c;
            border: none;
            border-radius: 5px;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }
        .registration-form button:hover {
            background-color: #4cae4c;
        }
    </style>
</head>
<body>
    <div class="registration-form">
        <h2>Employee Registration</h2>
        <form action="employees_registrationServlet" method="post">
            <label for="employeeId">Employee ID</label>
            <input type="text" id="employeeId" name="employeeId" value="<%= employeeId %>" readonly>

            <label for="name">Name</label>
            <input type="text" id="name" name="name" required>

            <label for="role">Role</label>
            <select id="role" name="role" required>
                <option value="manager">Manager</option>
                <option value="employee">Employee</option>
                <option value="admin">Admin</option>
            </select>

            <label for="phoneNumber">Phone Number</label>
            <input type="text" id="phoneNumber" name="phoneNumber" pattern="\d{10}" required>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" required>

            <label for="dob">Date of Birth</label>
            <input type="date" id="dob" name="dob" required>

            <button type="submit">Register</button>
        </form>
    </div>
</body>
</html>
