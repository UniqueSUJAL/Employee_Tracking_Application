<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.Employee.EmployeeViewListTaskDAO" %>
<%@ page import="com.Employee.model.EmployeeViewTask" %>
<%@ page import="javax.servlet.http.HttpSession" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Employee Tasks Table</title>
    <style>
     canvas {
            max-width: 1000px;
            margin: auto;
        }
        .chart-container {
            position: relative;
            height: 500px;
            width: 100%;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid black;
        }
        th, td {
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
    </style>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
</head>
<body>
    <h1>Employee Tasks Table</h1>

    <%
        // Fetch tasks from the database
        EmployeeViewListTaskDAO taskDAO = new EmployeeViewListTaskDAO();
        HttpSession httpSession = request.getSession();
        String employeeId = (String) httpSession.getAttribute("employeeId");
        List<EmployeeViewTask> tasks = taskDAO.getTasksByEmployee(employeeId);

        // Convert tasks list to JSON manually
        StringBuilder jsonBuilder = new StringBuilder("[");
        for (int i = 0; i < tasks.size(); i++) {
            EmployeeViewTask task = tasks.get(i);
            jsonBuilder.append("{")
                       .append("\"id\":\"").append(task.getId()).append("\",")
                       .append("\"taskId\":\"").append(task.getId()).append("\",")
                       .append("\"taskName\":\"").append(task.getTaskName()).append("\",")
                       .append("\"description\":\"").append(task.getDescription()).append("\",")
                       .append("\"taskDate\":\"").append(task.getTaskDate()).append("\",")
                       .append("\"startTime\":\"").append(task.getStartTime()).append("\",")
                       .append("\"endTime\":\"").append(task.getEndTime()).append("\",")
                       .append("\"status\":\"").append(task.getStatus()).append("\"")
                       .append("}");
            if (i < tasks.size() - 1) {
                jsonBuilder.append(",");
            }
        }
        jsonBuilder.append("]");
        String tasksJson = jsonBuilder.toString();
    %>

    <script>
        // Retrieve the JSON data from the JSP page
        var tasks = <%= tasksJson %>;

        // Check the JSON data in the console
        console.log(tasks);

        // Use Chart.js or any other JavaScript code to process and display the tasks
        // Example: Plotting a pie chart based on task statuses
        var taskStatuses = tasks.reduce(function(acc, task) {
            acc[task.status] = (acc[task.status] || 0) + 1;
            return acc;
        }, {});

        var ctx = document.createElement('canvas').getContext('2d');
        document.body.appendChild(ctx.canvas);

        new Chart(ctx, {
            type: 'pie',
            data: {
                labels: Object.keys(taskStatuses),
                datasets: [{
                    data: Object.values(taskStatuses),
                    backgroundColor: ['#FF6384', '#36A2EB', '#FFCE56']
                }]
            },
            options: {
                responsive: true,
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                return tooltipItem.label + ': ' + tooltipItem.raw;
                            }
                        }
                    }
                }
            }
        });
    </script>
    
    
    
    
    
    
    
    <canvas id="taskChart"></canvas>

    <script>
        // Example JSON data (replace this with your actual data)
        var jsonData = {
            "labels": ["2024-07-20", "2024-07-21", "2024-07-22", "2024-07-23", "2024-07-24", "2024-07-25", "2024-07-26"],
            "pendingCounts": [5, 8, 6, 7, 4, 5, 3],
            "completedCounts": [2, 3, 4, 6, 7, 2, 1]
        };

        var labels = jsonData.labels;
        var pendingCounts = jsonData.pendingCounts;
        var completedCounts = jsonData.completedCounts;

        // Create the stacked bar chart
        var ctx = document.getElementById('taskChart').getContext('2d');
        var taskChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: 'Pending Tasks',
                        data: pendingCounts,
                        backgroundColor: 'rgba(255, 99, 132, 0.5)',
                        stack: 'Stack 0'
                    },
                    {
                        label: 'Completed Tasks',
                        data: completedCounts,
                        backgroundColor: 'rgba(75, 192, 192, 0.5)',
                        stack: 'Stack 1'
                    }
                ]
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        stacked: true
                    },
                    y: {
                        stacked: true
                    }
                },
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                return tooltipItem.dataset.label + ': ' + tooltipItem.raw;
                            }
                        }
                    }
                }
            }
        });
    </script>
    
    
    
    
    <h1>Employee Tasks Gantt Chart</h1>

    <div class="chart-container">
        <canvas id="ganttChart"></canvas>
    </div>

    <script>
        // Example JSON data (replace this with your actual data)
        var jsonData = tasks;
        // Convert JSON data to Chart.js format
        var labels = [];
        var datasets = [];

        jsonData.forEach(task => {
            var taskDate = task.taskDate;
            var startTime = task.startTime.split(':').map(Number);
            var endTime = task.endTime.split(':').map(Number);

            var start = startTime[0] + startTime[1] / 60; // Convert to decimal hours
            var end = endTime[0] + endTime[1] / 60; // Convert to decimal hours

            // Check if date already exists in labels
            if (!labels.includes(taskDate)) {
                labels.push(taskDate);
            }

            var dataset = datasets.find(ds => ds.label === taskDate);
            if (!dataset) {
                dataset = {
                    label: taskDate,
                    data: [],
                    backgroundColor: 'rgba(75, 192, 192, 0.5)',
                    borderColor: 'rgba(75, 192, 192, 1)',
                    borderWidth: 1
                };
                datasets.push(dataset);
            }

            dataset.data.push({
                x: task.taskName,
                y: [start, end]
            });
        });

        var ctx = document.getElementById('ganttChart').getContext('2d');
        var ganttChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: datasets
            },
            options: {
                responsive: true,
                scales: {
                    x: {
                        stacked: true,
                        type: 'category'
                    },
                    y: {
                        stacked: true,
                        min: 0,
                        max: 24, // Assuming a 24-hour format
                        ticks: {
                            stepSize: 1
                        }
                    }
                },
                plugins: {
                    legend: {
                        position: 'top',
                    },
                    tooltip: {
                        callbacks: {
                            label: function(tooltipItem) {
                                var task = tooltipItem.raw;
                                return tooltipItem.dataset.label + ': ' + task.x + ' (' + task.y[0] + ' - ' + task.y[1] + ')';
                            }
                        }
                    }
                }
            }
        });
    </script>
</body>
</html>
    
   
