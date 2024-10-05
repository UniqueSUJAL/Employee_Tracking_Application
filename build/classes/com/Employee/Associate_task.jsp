<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Add Task</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            box-sizing: border-box;
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
        .navbar a.logout {
            float: right;
        }
        .navbar a:hover {
            background-color: #ddd;
            color: black;
        }
        .form-container {
            max-width: 400px;
            margin: 50px auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 10px;
            background-color: #f9f9f9;
        }
        .form-container input[type=text], .form-container input[type=number], .form-container input[type=date], .form-container input[type=time], .form-container textarea {
            width: 100%;
            padding: 10px;
            margin: 5px 0 20px 0;
            display: inline-block;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .form-container .button-container {
            display: flex;
            justify-content: space-between;
        }
        .form-container .button-container input[type=submit], .form-container .button-container a {
            width: 48%;
            background-color: #4CAF50;
            color: white;
            padding: 14px 20px;
            margin: 8px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            text-align: center;
            text-decoration: none;
            display: inline-block;
        }
        .form-container .button-container a {
            background-color: #f44336;
        }
        .form-container .button-container input[type=submit]:hover {
            background-color: #45a049;
        }
        .form-container .button-container a:hover {
            background-color: #d32f2f;
        }
        .alert {
            display: none;
            background-color: #4CAF50;
            color: white;
            padding: 10px;
            margin-top: 20px;
            border-radius: 4px;
            text-align: center;
        }
        .error {
            display: none;
            background-color: #f44336;
            color: white;
            padding: 10px;
            margin-top: 20px;
            border-radius: 4px;
            text-align: center;
        }
        @media (max-width: 600px) {
            .form-container {
                width: 90%;
                margin: 20px auto;
                padding: 10px;
            }
            .navbar a {
                float: none;
                width: 100%;
                text-align: left;
                padding: 10px;
            }
        }
    </style>
    <script>
        function showAlert(message, isError) {
            var alertBox = document.getElementById(isError ? "errorBox" : "alertBox");
            alertBox.innerText = message;
            alertBox.style.display = "block";
            setTimeout(function() {
                alertBox.style.display = "none";
            }, 3000); // Hide the alert after 3 seconds
        }

        function submitForm(event) {
            event.preventDefault(); // Prevent default form submission

            var form = document.querySelector("form");
            var formData = new FormData(form);

            var xhr = new XMLHttpRequest();
            xhr.open("POST", form.action, true);
            xhr.onload = function () {
                if (xhr.status == 200) {
                    var responseText = xhr.responseText;
                    if (responseText.includes("Error")) {
                        showAlert(responseText, true);
                    } else {
                        showAlert(responseText, false);
                        form.reset(); // Clear the form
                    }
                }
            };
            xhr.send(formData);
        }

        document.addEventListener("DOMContentLoaded", function() {
            document.querySelector("form").addEventListener("submit", submitForm);
        });
    </script>
</head>
<body>
    <div class="navbar">
        <a href="Manager_dashboard.jsp">Home</a>
        <a href="Associate_task.jsp">Add Task</a>
        <a href="AssociateTasksList.jsp">Tasks List</a>
        <a href="logout.jsp" class="logout">Logout</a>
    </div>
    <div class="form-container">
        <form action="MangerTaskServlet" method="post">
            <label for="taskDate">Date</label>
            <input type="date" id="taskDate" name="taskDate" required>

            <label for="taskTime">Time</label>
            <input type="time" id="taskTime" name="taskTime" required>

            <label for="taskDuration">Task Duration (hours)</label>
            <input type="number" id="taskDuration" name="taskDuration" min="0.1" max="8" step="0.1" required>

            <label for="taskDescription">Task Description</label>
            <textarea id="taskDescription" name="taskDescription" required></textarea>

            <div class="button-container">
                <a href="javascript:history.back()">Back</a>
                <input type="submit" value="Add Task">
            </div>
        </form>
        <div id="alertBox" class="alert">Task submitted successfully!</div>
        <div id="errorBox" class="error">Error: Task submission failed!</div>
    </div>
</body>
</html>
